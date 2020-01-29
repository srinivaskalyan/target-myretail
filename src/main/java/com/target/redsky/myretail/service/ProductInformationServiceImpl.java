package com.target.redsky.myretail.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.target.redsky.myretail.exception.handler.FailedToUpdateProductException;
import com.target.redsky.myretail.exception.handler.ProductNotFoundException;
import com.target.redsky.myretail.exception.handler.ProductPriceNotFoundInDBException;
import com.target.redsky.myretail.model.CurrentPrice;
import com.target.redsky.myretail.model.ProductInformation;
import com.target.redsky.myretail.model.ProductPrice;
import com.target.redsky.myretail.repository.ProductInformationRepository;

@Component("productServiceImpl")
public class ProductInformationServiceImpl implements ProductInformationService {

	@Autowired
	private ProductInformationRepository productInfoRepo;

	@Autowired
	private Environment configs;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<ProductPrice> getProducts() {
		return productInfoRepo.findAll();
	}

	@Override
	public ProductInformation getProductInformation(int id) {
		String productName = getProductName(id);
		ProductPrice productPrice = productInfoRepo.findByProductId(id);
		if (productPrice == null) {
			throw new ProductPriceNotFoundInDBException(
					"Product price record for given product id not found in database");
		}
		CurrentPrice currentPrice = new CurrentPrice(productPrice.getPrice(), productPrice.getCurrency());
		ProductInformation productInformation = new ProductInformation(id, productName, currentPrice);
		return productInformation;
	}

	private String getProductName(int id) {

		String productNameForId = null;
		String productInfoUrl = new StringBuilder().append(configs.getProperty("baseUrlPrefix")).append(id)
				.append(configs.getProperty("baseUrlSuffix")).toString();
		try {
			String result = restTemplate.getForObject(productInfoUrl, String.class);
			JSONObject jsonObj = new JSONObject(result);
			if (jsonObj.getJSONObject("product").getJSONObject("available_to_promise_network").getString("product_id")
					.equals(String.valueOf(id))) {
				productNameForId = jsonObj.getJSONObject("product").getJSONObject("item")
						.getJSONObject("product_description").getString("title");
			}
		} catch (Exception e) {
			throw new ProductNotFoundException("Product name not found for given product id: " + id);
		}
		return productNameForId;
	}

	@Override
	public ProductPrice updateProductInformation(int id, ProductInformation productToUpdate) {
		ProductPrice recordToUpdate = productInfoRepo.findByProductId(id);
		if (recordToUpdate == null) {
			throw new FailedToUpdateProductException(
					"Record not found in DB. Unable to update the product with product Id:" + id);
		}
		recordToUpdate.setPrice(productToUpdate.getCurrent_price().getValue());
		recordToUpdate.setCurrency(productToUpdate.getCurrent_price().getCurrency_code());
		productInfoRepo.save(recordToUpdate);
		return recordToUpdate;
	}
}
