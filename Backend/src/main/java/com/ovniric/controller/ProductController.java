package com.ovniric.controller;
import com.ovniric.dto.ProductDTO;
import com.ovniric.model.*;
import com.ovniric.repository.ProductRepository;
import com.ovniric.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController

@RequestMapping("/api/productos")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private FeatureService featureService;
    private ImageService imageService;
    private LocationService locationService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, FeatureService featureService, ImageService imageService, LocationService locationService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.featureService = featureService;
        this.imageService = imageService;
        this.locationService = locationService;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("id/{id}")
    public ResponseEntity<Product> searchProductById(@PathVariable Long id) {
        Optional<Product> productToSearch = productService.searchProduct(id);
        if(productToSearch.isPresent()) {
            return ResponseEntity.ok(productToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();

        Optional<Category> optionalCategory = categoryService.getCategory(productDTO.getCategoryId());
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            product.setCategory(category);
        }

        Optional<Location> locationOptional = locationService.searchLocation(productDTO.getLocationId());
        if(locationOptional.isPresent()) {
            Location location = locationOptional.get();
            product.setLocations(location);
        }


        Set<Feature> existingFeatures = new HashSet<>(featureService.searchAllFeatures());
        Set<Feature> features = new HashSet<>();
        for(String featureTitle : productDTO.getFeatureTitle()){
            Optional<Feature> optionalFeature = existingFeatures.stream()
                    .filter(feat -> feat.getTitle().equalsIgnoreCase(featureTitle))
                    .findFirst();

            if(optionalFeature.isPresent()){
                features.add(optionalFeature.get());
            }else {
                Feature feature = new Feature();
                feature.setTitle(featureTitle);
                featureService.createFeature(feature);
                features.add(feature);
            }
        }

        product.setFeatures(features);

        List<Image> images = new ArrayList<>();
        for(String imageUrl : productDTO.getImageUrl()){
            Image image = new Image();
            image.setImageUrl(imageUrl);
            image.setProduct(product);
            image.setImageTitle(product.getTitle());
            images.add(image);
        }
        product.setImages(images);

        product.setTitle(productDTO.getTitle());
        product.setAltitude(productDTO.getAltitude());
        product.setDescription(productDTO.getDescription());
        product.setMaxReservations(productDTO.getMaxReservations());
        product.setPolicy(productDTO.getPolicy());

        productRepository.save(product);

        productDTO.setId(product.getIdProduct());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);


//        ProductDTO product = productService.createProduct(productDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> searchAllProducts(){
        return ResponseEntity.ok(productService.searchAllProducts());
    }


    @GetMapping("nombre/{name}")
    public ResponseEntity<ProductDTO> searchProductById(@PathVariable String name) {
        Optional<ProductDTO> productToSearch = productService.searchProductByName(name);
        if(productToSearch.isPresent()) {
            return ResponseEntity.ok(productToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping
//    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO){
//        Optional<Product> productToUpdate = productService.searchProduct(productDTO.getId());
//        if(productToUpdate.isPresent()) {
//            productService.updateProduct(productDTO);
//            return ResponseEntity.ok("The product has been updated");
//        }else {
//            return ResponseEntity.badRequest().body("The product has not been updated because it is not in " +
//                    "the list of products");
//        }
//    }

//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        Optional<Product> optionalProduct = productRepository.findById(id);
//
//        if(optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//
//            Optional<Category> optionalCategory = categoryService.getCategory(productDTO.getCategoryId());
//            if(optionalCategory.isPresent()) {
//                Category category = optionalCategory.get();
//                product.setCategory(category);
//            }
//
//            Optional<Location> locationOptional = locationService.searchLocation(productDTO.getLocationId());
//            if(locationOptional.isPresent()) {
//                Location location = locationOptional.get();
//                product.setLocations(location);
//            }
//
//            Set<Feature> existingFeatures = new HashSet<>(featureService.searchAllFeatures());
//            Set<Feature> features = new HashSet<>();
//            for(String featureTitle : productDTO.getFeatureTitle()){
//                Optional<Feature> optionalFeature = existingFeatures.stream()
//                        .filter(feat -> feat.getTitle().equalsIgnoreCase(featureTitle))
//                        .findFirst();
//
//                if(optionalFeature.isPresent()){
//                    features.add(optionalFeature.get());
//                }else {
//                    Feature feature = new Feature();
//                    feature.setTitle(featureTitle);
//                    featureService.createFeature(feature);
//                    features.add(feature);
//                }
//            }
//
//            product.setFeatures(features);
//
//            List<Image> images = new ArrayList<>();
//            for(String imageUrl : productDTO.getImageUrl()){
//                Image image = new Image();
//                image.setImageUrl(imageUrl);
//                image.setProduct(product);
//                image.setImageTitle(product.getTitle());
//                images.add(image);
//            }
//            product.setImages(images);
//
//            product.setTitle(productDTO.getTitle());
//            product.setAltitude(productDTO.getAltitude());
//            product.setDescription(productDTO.getDescription());
//            product.setMaxReservations(productDTO.getMaxReservations());
//            product.setPolicy(productDTO.getPolicy());
//
//            productRepository.save(product);
//
//            productDTO.setId(product.getIdProduct());
//            return ResponseEntity.ok(productDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Optional<Product> productToDelete = productService.searchProduct(id);
        if(productToDelete.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("The product has been deleted");
        }else {
            return ResponseEntity.badRequest().body("The product does not exist in the database");
        }
    }

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/localizacion/{place}")
    public ResponseEntity<List<Product>> getProductosByLocalizacionPlace(@PathVariable String place) {
        List<Product> productos = productRepository.findByLocationPlace(place);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoryId}")
    public ResponseEntity<List<Product>> getProductosByLocalizacionPlace(@PathVariable Long categoryId) {
        List<Product> productos = productRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/random")
    public List<Product> getRandomProducts() {
        return productService.getRandomProducts(8);
    }

    @GetMapping ("/disponibles/{location}/{startDate}/{endDate}")
    public List<Product> getProductosDisponibles(@PathVariable("location") String location,
                                                 @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                 @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Product> productAvalibles= productRepository.findAvailableProductosByLocalizacion(location, startDate, endDate);
        return productAvalibles;
    }

    @GetMapping("/disponible/{startDate}/{endDate}")
    public List<Product> getAvailableProducts(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return productRepository.findAvailableProducts(startDate, endDate);
    }


//    @GetMapping("/nombre/{name}")
//    public ResponseEntity<Product> searchProductById(@PathVariable String name) {
//        Optional<Product> productToSearch = productService.searchProductByName(name);
//        if(productToSearch.isPresent()) {
//            return ResponseEntity.ok(productToSearch.get());
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping
//    public ResponseEntity<String> updateProduct(@RequestBody Product product){
//        Optional<Product> productToUpdate = productService.searchProduct(product.getIdProduct());
//        if(productToUpdate.isPresent()) {
//            productService.updateProduct(product);
//            return ResponseEntity.ok("The product has been updated");
//        }else {
//            return ResponseEntity.badRequest().body("The product has not been updated because it is not in " +
//                    "the list of products");
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//        Optional<Product> productToDelete = productService.searchProduct(id);
//        if(productToDelete.isPresent()) {
//            productService.deleteProduct(id);
//            return ResponseEntity.ok("The product has been deleted");
//        }else {
//            return ResponseEntity.badRequest().body("The product does not exist in the database");
//        }
//    }


    // @GetMapping("id/{id}")
    //public ResponseEntity<ProductDTO> searchProductById(@PathVariable Long id) {
    //  Optional<ProductDTO> productToSearch = productService.searchProduct(id);
    //if(productToSearch.isPresent()) {
    //  return ResponseEntity.ok(productToSearch.get());
    //}else {
    //  return ResponseEntity.notFound().build();
    //}
    //}

    //
//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        return ResponseEntity.ok(productService.createProduct(product));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Product>> searchAllProducts(){
//        return ResponseEntity.ok(productService.searchAllProducts());
//    }

}
