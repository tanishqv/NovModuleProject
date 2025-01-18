package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.dto.FakeStoreProductDTO;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Product redisProduct = (Product) redisTemplate.opsForHash().get("Product", "Product_" + id);
        if (redisProduct != null) {
            // Cache Hit
            return redisProduct;
        }
        // Cache miss
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class
        );

        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product not found with id=" + id);
        }

        System.out.println("Object: " + fakeStoreProductDTO);
        redisTemplate.opsForHash().put("Product", "Product_" + id, fakeStoreProductDTO.getProduct());
        return fakeStoreProductDTO.getProduct();
    }

    @Override
    public Page<Product> getAllProducts(Integer pageNum, Integer pageSize, String fieldName, Boolean sortAscending) throws ProductNotFoundException {
        FakeStoreProductDTO[] fakeStoreProductDTOs = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class
        );

        if (fakeStoreProductDTOs.length == 0) {
            throw new ProductNotFoundException("No products available to list");
        }

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO productDTO: fakeStoreProductDTOs) {
            products.add(productDTO.getProduct());
        }

        // Implementing pagination from list of all fetched products
        int start = Math.min(pageNum * pageSize, products.size());
        int end = Math.min((pageNum + 1) * pageSize, products.size());

        List<Product> paginatedProducts = products.subList(start, end);

        try {
            Field field = Product.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            paginatedProducts.sort((p1, p2) -> {
                try {
                    Object val1 = field.get(p1);
                    Object val2 = field.get(p2);

                    if (val1 instanceof Comparable && val2 instanceof Comparable) {
                        if (sortAscending) {
                            return ((Comparable) val1).compareTo(val2);
                        } else {
                            return ((Comparable) val2).compareTo(val1);
                        }
                    } else {
                        throw new IllegalArgumentException("Field " + fieldName + " not comparable");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field " + fieldName, e);
                }
            });
        } catch (NoSuchFieldException | RuntimeException e) {
            System.out.println("Ignoring sort on the field " + fieldName + "\n" + e.getMessage());
        }
        return new PageImpl<>(paginatedProducts, PageRequest.of(pageNum, pageSize), products.size());
    }

    @Override
    public Product createProduct(Long id, String title, Double price, String description, String image, String category) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(id);
        fakeStoreProductDTO.setTitle(title);
        fakeStoreProductDTO.setPrice(price);
        fakeStoreProductDTO.setDescription(description);
        fakeStoreProductDTO.setImage(image);
        fakeStoreProductDTO.setCategory(category);

        System.out.println("Object: " + fakeStoreProductDTO);

        FakeStoreProductDTO response = restTemplate.postForObject(
                "https://fakestoreapi.com/products/",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class
        );
        return response.getProduct();
    }

    @Override
    public Product updateProduct(Long id, String title, Double price, String description, String image, String category) throws ProductNotFoundException {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(title);
        fakeStoreProductDTO.setPrice(price);
        fakeStoreProductDTO.setDescription(description);
        fakeStoreProductDTO.setImage(image);
        fakeStoreProductDTO.setCategory(category);

        System.out.println("Object: " + fakeStoreProductDTO);

        ResponseEntity<FakeStoreProductDTO> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(fakeStoreProductDTO),
                FakeStoreProductDTO.class
        );
        System.out.println("response:" + response);

        return response.getBody().getProduct();
	}

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.DELETE,
                null,
                FakeStoreProductDTO.class
        );
        System.out.println("response:" + response);

        FakeStoreProductDTO fakeStoreProductDTO = response.getBody();
        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product not found with id=" + id);
        }
        return fakeStoreProductDTO.getProduct();
    }
}
