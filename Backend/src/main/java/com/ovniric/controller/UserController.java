package com.ovniric.controller;

import com.ovniric.model.user.User;
import com.ovniric.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/usuarios")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userToSearch = userService.getUser(id);
        if (userToSearch.isPresent()) {
            return ResponseEntity.ok(userToSearch.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user){
        Optional<User> userToUpdate = userService.getUser(user.getId());
        if(userToUpdate.isPresent()) {
            userService.updateUser(user);
            return ResponseEntity.ok("The user has been updated");
        }else {
            return ResponseEntity.badRequest().body("The user has not been updated because the user does not exist" +
                    " in the database");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<User> userToDelete = userService.getUser(id);
        if(userToDelete.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("The user has been deleted");
        }else {
            return ResponseEntity.badRequest().body("The user does not exist");
        }
        }
}
