package com.ovniric.service;

import com.ovniric.dto.ProductDTO;
import com.ovniric.model.*;
import com.ovniric.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public Product createProduct(Product product){
//        return productRepository.save(product);
//    }
//
//    public List<Product> searchAllProducts(){
//        return productRepository.findAll();
//    }
//
//    public Optional<Product> searchProduct(Long id) {
//        return productRepository.findById(id);
//    }
//
//
//    public Optional<Product> searchProductByName(String title) {
//        return productRepository.findByTitle(title);
//    }
//
//    public void updateProduct(Product product) {
//        productRepository.save(product);
//    }
//
//    public void deleteProduct(Long id) {
//        Optional<Product> productToDelete = searchProduct(id);
//        if (productToDelete.isPresent()) {
//            productRepository.deleteById(id);
//        }
//    }

    private ProductDTO productToProductDTO (Product product){
        ProductDTO result = new ProductDTO();

        result.setId(product.getIdProduct());
        result.setTitle(product.getTitle());
        result.setCategoryId(product.getCategory().getCategoryId());
        result.setLocationId(product.getLocations().getIdLocation());
        result.setAltitude(product.getAltitude());
        result.setImageUrl(product.getImages().stream().map(Image::getImageUrl).collect(Collectors.toSet()));
        result.setFeatureTitle(product.getFeatures().stream().map(Feature::getTitle).collect(Collectors.toSet()));
        result.setDescription(product.getDescription());
        result.setMaxReservations(product.getMaxReservations());
        result.setPolicy(product.getPolicy());
        return result;

    }

    private Product productDTOToProduct(ProductDTO productDTO) {
        Product result = new Product();

        Location location = new Location();
        location.setIdLocation(productDTO.getLocationId());


        List<Image> images = productDTO.getImageUrl().stream().map(imageUrl -> {
            Image image = new Image();
            image.setImageUrl(imageUrl);
            return image;
        }).collect(Collectors.toList());

        Set<Feature> features = productDTO.getFeatureTitle().stream().map(titleFeature -> {
            Feature feature = new Feature();
            feature.setTitle(titleFeature);
            return feature;
        }).collect(Collectors.toSet());

        Category category = new Category();
        category.setCategoryId((productDTO.getCategoryId()));

        result.setIdProduct(productDTO.getId());
        result.setTitle(productDTO.getTitle());
        result.setCategory(category);
        result.setLocations(location);
        result.setAltitude(productDTO.getAltitude());
        result.setImages(images);
        result.setFeatures(features);
        result.setDescription(productDTO.getDescription());
        result.setMaxReservations(productDTO.getMaxReservations());
        result.setPolicy(productDTO.getPolicy());

        return result;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productToCreate = productRepository.save(productDTOToProduct(productDTO));
        return productToProductDTO(productToCreate);
    }

    public List<Product> searchAllProducts(){
        List<Product> productsToSearch = productRepository.findAll();
        List<Product> result = new ArrayList<>();
        for(Product product : productsToSearch){
            result.add(product);
        }
        return result;

    }

    public Optional<Product> searchProduct(Long id) {
        Optional<Product> productSearched = productRepository.findById(id);
        if(productSearched.isPresent()) {
            return Optional.of(productSearched.get());
        }else {
            return Optional.empty();
        }
    }


    public Optional<ProductDTO> searchProductByName(String title) {
        Optional<Product> productSearched = productRepository.findByTitle(title);
        if(productSearched.isPresent()) {
            return Optional.of(productToProductDTO(productSearched.get()));
        }else {
            return Optional.empty();
        }
    }
        public List<Product> getRandomProducts(int count) {
            List<Product> allProducts = productRepository.findAll();
            Collections.shuffle(allProducts);
            return allProducts.subList(0,count);
        }


    public void updateProduct(ProductDTO productDTO) {
       productRepository.save(productDTOToProduct(productDTO));
    }

    public void deleteProduct(Long id) {
        Optional<Product> productToDelete = searchProduct(id);
        if (productToDelete.isPresent()) {
            productRepository.deleteById(id);
        }
    }
}
