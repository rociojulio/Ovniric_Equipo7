package com.ovniric.repository;


import com.ovniric.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String title);
//    List<Product> findProductsByCategory(String category);
//
//    List<Product> findProductsByLocation(String location);
    @Query("SELECT p FROM Product p WHERE p.locations.place = :place")
    List<Product> findByLocationPlace(@Param("place") String place);

    @Query("SELECT p FROM Product p WHERE p.locations.place= :place AND NOT EXISTS (SELECT r FROM p.reservations r WHERE r.endDate >= :startDate AND r.startDate <= :endDate)")
    List<Product> findAvailableProductosByLocalizacion(@Param("place") String place, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Product p WHERE NOT EXISTS (SELECT r FROM p.reservations r WHERE r.endDate >= :startDate AND r.startDate <= :endDate)")
    List<Product> findAvailableProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long id);

}
