package com.ovniric.repository;

import com.ovniric.model.Location;
import com.ovniric.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {

    Optional<Location> findByPlace(String place);

}
