package com.scaler.NovModuleProject;

import com.scaler.NovModuleProject.models.Product;
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

}
