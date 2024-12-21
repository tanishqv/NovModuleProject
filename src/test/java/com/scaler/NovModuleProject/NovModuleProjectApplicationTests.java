package com.scaler.NovModuleProject;

import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.projections.ProductProjection;
import com.scaler.NovModuleProject.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NovModuleProjectApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testFindProductByCategoryId() {
		List<Product> products = productRepository.findProductByCategoryId(1L);
		for(Product p: products) {
			System.out.println(p);
		}
	}

	@Test
	void testFindProductByCategoryIdUsingNativeQuery() {
		List<Product> products = productRepository.findProductByCategoryIdUsingNativeQuery(1L);
		for(Product p: products) {
			System.out.println(p);
		}
	}

	@Test
	void testFindProductTitleAndIdByCategoryIdUsingProjections() {
		List<ProductProjection> products = productRepository.findProductTitleAndIdByCategoryIdUsingProjections(1L);
		for(ProductProjection p: products) {
			System.out.println("(In query) ID: " + p.getId());
			System.out.println("(In query) Title: " + p.getTitle());
			System.out.println("(Not in query) Description: " + p.getDescription());
			System.out.println("(Not in query) Price: " + p.getPrice());
			System.out.println("(Not in query) Image URL: " + p.getImageUrl());
			System.out.println("(Not in query) Category: " + p.getCategory());
			System.out.println("---------------------------------------------");
		}
	}

}
