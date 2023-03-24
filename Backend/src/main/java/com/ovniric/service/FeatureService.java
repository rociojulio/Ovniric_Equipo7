package com.ovniric.service;


import com.ovniric.model.Feature;
import com.ovniric.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FeatureService {

    private FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public Feature createFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    public Optional<Feature> searchFeature(Long id) {
        return featureRepository.findById(id);
    }

    public List<Feature> searchAllFeatures() {
        return featureRepository.findAll();
    }

    public void updateFeature(Feature feature) {
        featureRepository.save(feature);
    }

    public void deleteFeature(Long id) {
        Optional<Feature> featureToDelete = searchFeature(id);
        if(featureToDelete.isPresent()) {
            featureRepository.deleteById(id);
        }
    }

    }

