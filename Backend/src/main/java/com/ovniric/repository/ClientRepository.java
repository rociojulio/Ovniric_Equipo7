package com.ovniric.repository;

import com.ovniric.model.Reservation;
import com.ovniric.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
    Client findByEmail(String email);

    @Override
    Optional<Client> findById(Long clientId);
}
