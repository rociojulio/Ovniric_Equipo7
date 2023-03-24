package com.ovniric.controller;

import com.ovniric.dto.ClientDTO;
import com.ovniric.model.user.Client;
import com.ovniric.repository.ClientRepository;
import com.ovniric.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;

//    @GetMapping
//    public List<ClientDTO> getAllClients() {
//        return clientService.getAllClients();
//    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> clienteToSearch = clientService.getClientById(id);
        if (clienteToSearch.isPresent()) {
            return ResponseEntity.ok(clienteToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
//        Optional<ClientDTO> client = clientService.getClientByid(id).map(clientService::toUserDTO);
//        if (client.isPresent()) {
//            return ResponseEntity.ok(client.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
    }

    @PutMapping
    public ResponseEntity<String> updateClient(@RequestBody Client client){
        Optional<Client> clientToUpdate = clientService.getClientById(client.getId());
        if(clientToUpdate.isPresent()) {
            clientService.updateClient(client);
            return ResponseEntity.ok("The client has been updated");
        }else{
            return ResponseEntity.badRequest().body("The client has not been updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        Optional<Client> clienteToSearch = clientService.getClientById(id);
        if(clienteToSearch.isPresent()) {
            clientService.deleteClient(id);
            return ResponseEntity.ok("The client was successfully deleted");
        }else {
            return ResponseEntity.badRequest().body("The client was not found in the list of clients");
        }
    }
}