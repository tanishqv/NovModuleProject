package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.dto.FakeStoreProductDTO;
import com.scaler.NovModuleProject.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class
        );
        System.out.println("Object: " + fakeStoreProductDTO);
        return fakeStoreProductDTO.getProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
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
}
