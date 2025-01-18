package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.repository.CategoryRepository;
import com.scaler.NovModuleProject.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service("dbProductService")
public class DbProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Product redisProduct = (Product) redisTemplate.opsForHash().get("Product", "Product_" + id);
        if (redisProduct != null) {
            // Cache Hit
            return redisProduct;
        }
        // Cache miss
        Optional<Product> p = productRepository.findById(id);

        if (p.isPresent()) {
            // Storing in cache
            redisTemplate.opsForHash().put("Product", "Product_" + id, p);
            return p.get();
        }
        throw new ProductNotFoundException("Product not found in DB with id=" + id);
    }

    @Override
    public Page<Product> getAllProducts(Integer pageNum, Integer pageSize, String fieldName, Boolean sortAscending) throws ProductNotFoundException {
        Page<Product> products;
        if (sortAscending) {
            products = productRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(fieldName).ascending()));
        } else {
            products = productRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(fieldName).descending()));
        }

        if (!products.isEmpty()) {
            return products;
        }
        throw new ProductNotFoundException("No products available in DB to list");
    }

    @Override
    public Product createProduct(Long id, String title, Double price, String description, String image, String categoryTitle) {
        Product product = new Product();

        // Only findByPK methods in JPA have Optional return type by default, rest have to be made to return Optional type through the Repository
        Optional<Category> currCategory = categoryRepository.findByTitle(categoryTitle);
        if (currCategory.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            Category newRow = categoryRepository.save(newCategory);
            product.setCategory(newRow);
        } else {
            product.setCategory(currCategory.get());
        }

        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, String title, Double price, String description, String image, String categoryTitle) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            if (title != null) {
                product.get().setTitle(title);
            }
            if (price != null) {
                product.get().setPrice(price);
            }
            if (description != null) {
                product.get().setDescription(description);
            }
            if (image != null) {
                product.get().setImageUrl(image);
            }
            if (categoryTitle != null) {
                System.out.println("Got categoryTitle=" + categoryTitle);
                Optional<Category> currCategory = categoryRepository.findByTitle(categoryTitle);
                if (currCategory.isEmpty()) {
                    System.out.println("Creating new category with " + categoryTitle);
                    Category newCategory = new Category();
                    newCategory.setTitle(categoryTitle);
                    Category newRow = categoryRepository.save(newCategory);
                    product.get().setCategory(newRow);
                }
            }
            return productRepository.save(product.get());
        }
        throw new ProductNotFoundException("Product not found with id=" + id);
    }

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return product.get();
        }
        throw new ProductNotFoundException("Product not found with id=" + id);
    }
}
