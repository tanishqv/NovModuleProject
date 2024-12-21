package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.dto.FakeStoreProductDTO;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class
        );

        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product not found with id=" + id);
        }

        System.out.println("Object: " + fakeStoreProductDTO);
        return fakeStoreProductDTO.getProduct();
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
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

        return products;
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
    public Product updateProduct(Long id, String title, Double price, String description, String image, String category) {
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
