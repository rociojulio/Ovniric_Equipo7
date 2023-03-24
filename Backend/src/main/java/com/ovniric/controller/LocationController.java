package com.ovniric.controller;

import com.ovniric.model.Feature;
import com.ovniric.model.Location;
import com.ovniric.model.Product;
import com.ovniric.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/localizaciones")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    ResponseEntity<List<Location>> searchAllLocations() {
        return ResponseEntity.ok(locationService.searchAllLocations());
    }

    @GetMapping("/{id}")
    ResponseEntity<Location> searchLocationById(@PathVariable Long id) {
        Optional<Location> locationToSearch = locationService.searchLocation(id);
        if (locationToSearch.isPresent()) {
            return ResponseEntity.ok(locationToSearch.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{place}")
    ResponseEntity<Location> searchLocationByPlace(@PathVariable String place) {
        Optional<Location> locationToSearch = locationService.searchLocationByPlace(place);
        if (locationToSearch.isPresent()) {
            return ResponseEntity.ok(locationToSearch.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateLocation(@RequestBody Location location){
        Optional<Location> locationToUpdate = locationService.searchLocation(location.getIdLocation());
        if(locationToUpdate.isPresent()) {
            locationService.updateLocation(location);
            return ResponseEntity.ok("The location has been updated");
        }else {
            return ResponseEntity.badRequest().body("The location has not been updated because it is not in " +
                    "the list of locations");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        Optional<Location> locationToDelete = locationService.searchLocation(id);
        if(locationToDelete.isPresent()) {
           locationService.deleteLocation(id);
            return ResponseEntity.ok("The location has been deleted");
        }else {
            return ResponseEntity.badRequest().body("The location with" + id + "does not exist in the database");
        }
    }

























}
