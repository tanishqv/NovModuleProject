package com.scaler.NovModuleProject;

import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.projections.ProductProjection;
import com.scaler.NovModuleProject.repository.CategoryRepository;
import com.scaler.NovModuleProject.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class NovModuleProjectApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

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

	@Test
	void testFetchType() {
		// Hibernate executes following query for Fetch Type "EAGER"
		// Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.title,c1_0.updated_at,p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from category c1_0 left join product p1_0 on c1_0.id=p1_0.category_id where c1_0.id=?
		// Products list is also populated

		// Added hibernate.enable_lazy_load_no_trans
		// Hibernate executes following queries for Fetch Type "LAZY"
		// Fetching category only
		// Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from product p1_0 where p1_0.category_id=?
		// Populating the list of products later (when getProducts() is called)
		// Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.title,c1_0.updated_at from category c1_0 where c1_0.id=?

		Category category = categoryRepository.findById(1L).get();
		System.out.println(category.getId());

		List<Product> currentProducts = category.getProducts();
		System.out.println("#products in category: " + currentProducts.size());
	}

	@Test
	void testNplusOneProblem() {
		// This executes 1 query to get all the categories
		// Then N queries to get the list of products for each of the N categories
		// Queries executed by Hibernate:
		// Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.title,c1_0.updated_at from category c1_0 where c1_0.id in (?,?)
		// Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from product p1_0 where p1_0.category_id=?
		// Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.title,c1_0.updated_at from category c1_0 where c1_0.id=?
		// Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from product p1_0 where p1_0.category_id=?
		// Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.title,c1_0.updated_at from category c1_0 where c1_0.id=?

		// This problem is easily resolved with the use of HQL or native SQL queries.
		List<Category> categories = categoryRepository.findAllById(Arrays.asList(1L, 2L));
		for (Category c: categories) {
			System.out.println("Products for category: " + c.getTitle());
			System.out.println(c.getProducts());
		}
	}
}
