package com.ovniric.controller;

import com.ovniric.model.Feature;
import com.ovniric.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/caracteristicas")
public class FeatureController {

    private FeatureService featureService;


    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;

    }

    @PostMapping
    ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.createFeature(feature));
    }

    @GetMapping
    ResponseEntity<List<Feature>> searchAllFeatures() {
        return ResponseEntity.ok(featureService.searchAllFeatures());
    }

    @GetMapping("/{id}")
    ResponseEntity<Feature> searchFeature(@PathVariable Long id) {
        Optional<Feature> featureToSearch = featureService.searchFeature(id);
        if (featureToSearch.isPresent()) {
            return ResponseEntity.ok(featureToSearch.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateFeature(@RequestBody Feature feature) {
        Optional<Feature> featureToUpdate = featureService.searchFeature(feature.getIdFeature());
        if (featureToUpdate.isPresent()) {
            featureService.updateFeature(feature);
            return ResponseEntity.ok("The feature has been updated");
        } else {
            return ResponseEntity.badRequest().body("The feature cannot be updated because it is not in the list of features");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable Long id) {
        Optional<Feature> featureToDelete = featureService.searchFeature(id);
        if (featureToDelete.isPresent()) {
            featureService.deleteFeature(id);
            return ResponseEntity.ok("The feature has been deleted");
        } else {
            return ResponseEntity.badRequest().body("The feature does not exist");
        }
    }

}


