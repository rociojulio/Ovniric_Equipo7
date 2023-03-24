package com.ovniric.service;

import com.ovniric.model.Location;
import com.ovniric.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> searchAllLocations(){
        return locationRepository.findAll();
    }

    public Optional<Location> searchLocation(Long id) {
        return locationRepository.findById(id);
    }


    public Optional<Location> searchLocationByPlace(String place) {
        return locationRepository.findByPlace(place);
    }

    public void updateLocation(Location location) {
        locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        Optional<Location> locationToDelete = searchLocation(id);
        if (locationToDelete.isPresent()) {
            locationRepository.deleteById(id);
        }
    }
}
