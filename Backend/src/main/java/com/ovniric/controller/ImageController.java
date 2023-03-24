package com.ovniric.controller;

import com.ovniric.model.Image;
import com.ovniric.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/imagenes")
public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImages(){
        return ResponseEntity.ok(imageService.getImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        Optional<Image> imageToSearch = imageService.getImage(id);
        if (imageToSearch.isPresent()) {
            return ResponseEntity.ok(imageToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        return ResponseEntity.ok(imageService.createImage(image));
    }

    @PutMapping
    public ResponseEntity<String> updateImage(@RequestBody Image image) {
        Optional<Image> imageToSearch = imageService.getImage(image.getIdImage());
        if (imageToSearch.isPresent()) {
            imageService.updateImage(image);
            return ResponseEntity.ok("The image with id " + image.getIdImage() + "has been updated");
        }else {
            return ResponseEntity.badRequest().body("The image with id " + image.getIdImage() + "has not been updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        Optional<Image> imageToDelete = imageService.getImage(id);
        if (imageToDelete.isPresent()) {
            imageService.deleteImage(id);
            return ResponseEntity.ok("The image was successfully deleted");
        }else {
            return ResponseEntity.badRequest().body("The image was not found");
        }
    }



}
