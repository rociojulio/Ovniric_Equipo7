package com.ovniric.controller;

import com.ovniric.dto.ReservationDTO;
import com.ovniric.model.Product;
import com.ovniric.model.Reservation;
import com.ovniric.model.user.Client;
import com.ovniric.model.user.User;
import com.ovniric.repository.ReservationRepository;
import com.ovniric.repository.UserRepository;
import com.ovniric.service.ClientService;
import com.ovniric.service.ProductService;
import com.ovniric.service.ReservationService;
import com.ovniric.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController

@AllArgsConstructor
@RequestMapping("/api/reservaciones")
public class ReservationController {
    private ReservationService reservationService;
    private UserService userService;
    private ProductService productService;
    private ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO){
        try{
            Reservation reservation = new Reservation();

            Optional<User> optionalUser = userService.getUser(reservationDTO.getUserId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                reservation.setUser(user);
                reservationDTO.setUserId(user.getId());
            }

            Optional<Product> optionalProduct = productService.searchProduct(reservationDTO.getProductId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                reservation.setProduct(product);

                int numReservations = reservationRepository.countByProductAndStartDate(product, reservationDTO.getStartDate());
                if (numReservations >= product.getMaxReservations()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(null);
                }
            }


            LocalTime startHour = LocalTime.parse(reservationDTO.getStartHour(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            reservation.setStartHour(startHour);
            reservation.setStartDate(reservationDTO.getStartDate());
            reservation.setEndDate(reservationDTO.getEndDate());

            reservationRepository.save(reservation);

            reservationDTO.setId(reservation.getIdReservation());
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @GetMapping
    public ResponseEntity<List<Reservation>> getALLReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Optional<Reservation> reservationToSearch = reservationService.getReservationById(id);
        if (reservationToSearch.isPresent()) {
            return ResponseEntity.ok(reservationToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Reservation> reservations = reservationService.getReservationsByUser(user);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<Reservation>> getReservationsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reservationService.findReservationsByProductId(productId));
    }

    @PutMapping
    public ResponseEntity<String> updateReservation(@RequestBody Reservation reservation) {
        Optional<Reservation> reservationToSearch = reservationService.getReservationById(reservation.getIdReservation());
        if (reservationToSearch.isPresent()) {
            reservationService.updateReservation(reservation);
            return ResponseEntity.ok("The reservation has been updated");
        }else {
            return ResponseEntity.badRequest().body("The reservation has not been updated because the reservation" +
                    "does not exists on database");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservationToSearch = reservationService.getReservationById(id);
        if(reservationToSearch.isPresent()) {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok("The reservation has been deleted");
        }else {
            return ResponseEntity.badRequest().body("The reservation has not been deleted");
        }
    }
}
