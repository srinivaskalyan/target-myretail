package com.target.redsky.myretail.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.target.redsky.myretail.model.ProductPrice;

public interface ProductInformationRepository extends MongoRepository<ProductPrice, Integer> {
	
	ProductPrice findByProductId(int id);
	List<ProductPrice> findAll();

}
