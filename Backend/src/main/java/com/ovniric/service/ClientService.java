package com.ovniric.service;

import com.ovniric.dto.ClientDTO;
import com.ovniric.model.user.Client;
import com.ovniric.model.user.Role;
import com.ovniric.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ClientService {

    ClientRepository clientRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

//    public List<ClientDTO> getAllClients() {
//        List<Client> clients = clientRepository.findAll();
//        return clients.stream().map(this::toUserDTO).collect(Collectors.toList());
//    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    public List<Client> getAllClients() {
        return  clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id){
       return clientRepository.findById(id);
    }

    public void updateClient(Client client){
        clientRepository.save(client);
    }
    public void deleteClient(Long id) {
        Optional<Client> clientToDelete = getClientById(id);
        if (clientToDelete.isPresent()) {
            clientRepository.deleteById(id);
        }
    }


//    public ClientDTO toUserDTO(Client user) {
//        ClientDTO dto = new ClientDTO();
//        dto.setId(user.getId());
//        dto.setFirstName(user.getFirstname());
//        dto.setLastName(user.getLastname());
//        dto.setEmail(user.getEmail());
//        dto.setCity(user.getCity());
//        dto.setRoleName(user.getRole().getRoleEnum().name());
//        return dto;
//    }
//
//    public Client toUser(ClientDTO dto) {
//        Client user = new Client();
//        user.setFirstname(dto.getFirstName());
//        user.setLastname(dto.getLastName());
//        user.setEmail(dto.getEmail());
//        user.setCity(dto.getCity());
//        Role role = new Role();
//        return user;
//    }
}
