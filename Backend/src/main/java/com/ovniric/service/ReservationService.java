package com.ovniric.service;

import com.ovniric.dto.ReservationDTO;
import com.ovniric.model.user.Client;
import com.ovniric.model.Product;
import com.ovniric.model.Reservation;
import com.ovniric.model.user.User;
import com.ovniric.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    private ProductService productService;
    private ClientService clientService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


//    public List<Reservation> getReservationsByUser(User user) {
//        List<Reservation> allReservations = reservationRepository.findByUser(user);
//        List<Reservation> clientReservations = new ArrayList<>();
//        for (Reservation reservation : allReservations) {
//            if (reservation.getClient().getId().equals(user.getId())) {
//                clientReservations.add(reservation);
//            }
//        }
//        return clientReservations;
//    }

    public List<Reservation> getReservationsByUser(User user) {
        List<Reservation> allReservations = reservationRepository.findByUser(user);
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : allReservations) {
            if (reservation.getUser().equals(user)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }



    public List<Reservation> findReservationsByProductId(Long productId) {
        return reservationRepository.findAllByProductId(productId);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservationToCreate = reservationRepository.save(toReservation(reservationDTO));
        return toReservationDTO(reservationToCreate);
    }

    public Reservation updateReservation(Reservation updatedReservation) {
         return reservationRepository.save(updatedReservation);
    }
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }


    public Reservation toReservation(ReservationDTO reservationDTO) {

        User user = new User();
        user.setId(reservationDTO.getUserId());
        Product product= new Product();
        product.setIdProduct(reservationDTO.getProductId());

        Reservation result = new Reservation();
        result.setIdReservation(reservationDTO.getId());
        LocalTime startHour =  LocalTime.parse(reservationDTO.getStartHour(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        result.setStartHour(startHour);

        result.setEndDate(reservationDTO.getEndDate());
        result.setStartDate(reservationDTO.getStartDate());
        result.setProduct(product);
        result.setUser(user);
        return result;
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {

        ReservationDTO result = new ReservationDTO();

        result.setId(reservation.getIdReservation());

        String startHour = reservation.getStartHour().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        result.setStartHour(startHour);

        result.setEndDate(reservation.getEndDate());
        result.setStartDate(reservation.getStartDate());
        result.setUserId(reservation.getUser().getId());
        result.setProductId(reservation.getProduct().getIdProduct());

        return result;

    }

}

