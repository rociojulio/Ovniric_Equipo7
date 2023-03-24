package com.ovniric.auth;

import com.ovniric.model.user.Client;
import com.ovniric.model.user.Role;
import com.ovniric.repository.ClientRepository;
import com.ovniric.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;



    //Conectar con cliente
    @PostMapping("/userRegister")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        AuthenticationResponse response = authenticationService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/adminRegister")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ){

        AuthenticationResponse response = authenticationService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        authenticationService.logout();
        return ResponseEntity.noContent().build();
    }
}
