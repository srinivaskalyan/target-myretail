package com.target.redsky.myretail.service;

import java.util.List;

import com.target.redsky.myretail.model.ProductInformation;
import com.target.redsky.myretail.model.ProductPrice;

public interface ProductInformationService {

	List<ProductPrice> getProducts();

	ProductInformation getProductInformation(int id);

	ProductPrice updateProductInformation(int id, ProductInformation productToUpdate);

}
