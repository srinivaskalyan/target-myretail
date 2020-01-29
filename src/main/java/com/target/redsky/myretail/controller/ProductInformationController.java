package com.target.redsky.myretail.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.target.redsky.myretail.model.ProductInformation;
import com.target.redsky.myretail.model.ProductPrice;
import com.target.redsky.myretail.service.ProductInformationService;

@RestController
@RequestMapping("/")
public class ProductInformationController {

	private final Logger logger = LoggerFactory.getLogger(ProductInformationController.class);

	@Autowired
	private ProductInformationService productInfoService;

	/*
	 * HTTP GET request to get a product information for the given product {id} with
	 * id field as input to the service
	 */
	@GetMapping(value = "/products/{id}")
	@ResponseBody
	public ResponseEntity<ProductInformation> getProductById(@PathVariable("id") int id) {
		ProductInformation productInformationForGivenId;
		logger.info("requesting product information for product id: {}", id);
		productInformationForGivenId = productInfoService.getProductInformation(id);
		logger.info("product information for product id: {}, {}", id, productInformationForGivenId.toString());
		return new ResponseEntity<ProductInformation>(productInformationForGivenId, HttpStatus.OK);
	}

	/*
	 * HTTP PUT request to update a product information for the given product {id}
	 * with
	 * 
	 * @RequestBody ProductInformation and id as inputs to the service
	 */
	@PutMapping(value = "/products/{id}")
	@ResponseBody
	public ResponseEntity<ProductPrice> updatetProductPrice(@PathVariable("id") int id,
			@RequestBody ProductInformation productToUpdate) {
		logger.info("requesting to update product information for product id: {}", id);
		ProductPrice updatedProductInformation = productInfoService.updateProductInformation(id, productToUpdate);
		logger.info("updated product information for product id: {}, {}", id, updatedProductInformation.toString());
		return new ResponseEntity<ProductPrice>(updatedProductInformation, HttpStatus.OK);
	}

	/*
	 * HTTP GET request to fetch all products information 
	 * from the database
	 */
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public List<ProductPrice> getProducts() {
		return productInfoService.getProducts();
	}
}
