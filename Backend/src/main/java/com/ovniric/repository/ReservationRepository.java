package com.ovniric.repository;

import com.ovniric.model.Product;
import com.ovniric.model.Reservation;
import com.ovniric.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findByUser(User user);

    @Query("SELECT r FROM Reservation r WHERE r.product.idProduct = :productId")
    List<Reservation> findAllByProductId(@Param("productId") Long productId);

    Integer countByProductAndStartDate(Product product, LocalDate startDate);

    

}
