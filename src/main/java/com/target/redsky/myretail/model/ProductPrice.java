package com.target.redsky.myretail.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
public class ProductPrice {
	
	@Id
	private BigInteger _id;
	private int productId;
	private Double price;
	private String currency;
	
	public ProductPrice() {	}
	
	public ProductPrice(BigInteger id, int productId, Double price, String currency) {
		this._id = id;
		this.productId = productId;
		this.price = price;
		this.currency = currency;
	}

	public BigInteger get_id() {
		return _id;
	}

	public void set_id(BigInteger _id) {
		this._id = _id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "ProductPrice [_id=" + _id + ", productId=" + productId + ", price=" + price + ", currency=" + currency
				+ "]";
	}
}
