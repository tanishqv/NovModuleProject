package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.dto.FakeStoreProductDTO;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Product> getSingleProduct(Long id) throws ProductNotFoundException {
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class
        );

        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product not found with id=" + id);
        }

        System.out.println("Object: " + fakeStoreProductDTO);
        return new ResponseEntity<>(fakeStoreProductDTO.getProduct(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        FakeStoreProductDTO[] fakeStoreProductDTOs = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class
        );

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO productDTO: fakeStoreProductDTOs) {
            products.add(productDTO.getProduct());
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
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
    public ResponseEntity<Object> updateProduct(Long id, String title, Double price, String description, String image, String category) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(title);
        fakeStoreProductDTO.setPrice(price);
        fakeStoreProductDTO.setDescription(description);
        fakeStoreProductDTO.setImage(image);
        fakeStoreProductDTO.setCategory(category);

        System.out.println("Object: " + fakeStoreProductDTO);

        restTemplate.put(
                "https://fakestoreapi.com/products/" + id,
                fakeStoreProductDTO
        );
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

    @Override
    public ResponseEntity<Object> deleteProductById(Long id) {
        restTemplate.delete(
                "https://fakestoreapi.com/products/" + id
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
