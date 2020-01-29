package com.target.redsky.myretail.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.target.redsky.myretail.model.CurrentPrice;
import com.target.redsky.myretail.model.ProductInformation;
import com.target.redsky.myretail.model.ProductPrice;
import com.target.redsky.myretail.service.ProductInformationService;

@RunWith(MockitoJUnitRunner.class)
public class ProductInformationControllerTest {

	@InjectMocks
	private ProductInformationController mockProductInfoController;

	@Mock
	private ProductInformationService mockProductInfoService;

	private int dummyProductId;
	private String dummyProductName;
	private ProductInformation dummyProductInfo;
	private ProductPrice dummyUpdatedProductPrice;
	
	@Before
	public void setUp() {
		dummyProductId = 12345678;
		dummyProductName = "Market Pantry";
		
		CurrentPrice dummyCurrentPrice = new CurrentPrice();
		dummyCurrentPrice.setCurrency_code("USD");
		dummyCurrentPrice.setValue(59.00);
		dummyProductInfo = new ProductInformation();
		dummyProductInfo.setId(dummyProductId);
		dummyProductInfo.setName(dummyProductName);
		dummyProductInfo.setCurrent_price(dummyCurrentPrice);
	
		dummyUpdatedProductPrice = new ProductPrice();
		dummyUpdatedProductPrice.setPrice(60.00);
		dummyUpdatedProductPrice.setProductId(dummyProductId);
	}
	
	@Test
	public void getProductInformationForGivenProductIdTest() {

		when(mockProductInfoService.getProductInformation(dummyProductId)).thenReturn(dummyProductInfo);
		ResponseEntity<ProductInformation> response = mockProductInfoController.getProductById(dummyProductId);
		assertEquals(dummyProductInfo.getId(), response.getBody().getId());
		assertEquals(dummyProductInfo.getName(), response.getBody().getName());
	}
	
	@Test
	public void updateProductPriceForGivenProductInformationTest() {
		
		when(mockProductInfoService.updateProductInformation(dummyProductId, dummyProductInfo)).thenReturn(dummyUpdatedProductPrice);
		ResponseEntity<ProductPrice> response = mockProductInfoController.updatetProductPrice(dummyProductId, dummyProductInfo);
		assertEquals(dummyUpdatedProductPrice.getPrice(), response.getBody().getPrice());
		assertEquals(dummyUpdatedProductPrice.getCurrency(), response.getBody().getCurrency());
	}
	
	@Test
	public void getAllProductsFromDBTest() {
		
		List<ProductPrice> listOfProductsFromDB = new ArrayList<>();
		listOfProductsFromDB.add(dummyUpdatedProductPrice);
		listOfProductsFromDB.add(dummyUpdatedProductPrice);
		listOfProductsFromDB.add(dummyUpdatedProductPrice);
		
		when(mockProductInfoService.getProducts()).thenReturn(listOfProductsFromDB);
		List<ProductPrice> result = mockProductInfoController.getProducts();
		assertEquals(listOfProductsFromDB.size(), result.size());
	}

}
