package com.ovniric.service;

import com.ovniric.model.Image;
import com.ovniric.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById((id));
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    public void updateImage(Image image) {
        imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        Optional<Image> imageToDelete= getImage(id);
        if(imageToDelete.isPresent()) {
            imageRepository.deleteById(id);
        }
    }


}
