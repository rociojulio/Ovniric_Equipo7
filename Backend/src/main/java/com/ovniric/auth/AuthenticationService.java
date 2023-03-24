package com.ovniric.auth;


import com.ovniric.model.user.Client;
import com.ovniric.model.user.Role;
import com.ovniric.model.user.RoleEnum;
import com.ovniric.model.user.User;
import com.ovniric.repository.ClientRepository;
import com.ovniric.repository.RoleRepository;
import com.ovniric.repository.UserRepository;
import com.ovniric.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;


    public AuthenticationResponse registerUser(RegisterRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User Role not found"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .city(request.getCity())
                .build();
        userRepository.save(user);




        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var userRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("User Role not found"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .city(request.getCity())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void logout(){
        SecurityContextHolder.clearContext();
    }
}

// esta clase de servicio proporciona métodos para registrar y autenticar usuarios en la aplicación utilizando un UserRepository
// para almacenar y recuperar los datos de usuario,
// un PasswordEncoder para cifrar y comparar contraseñas, un JwtService para generar y validar tokens JWT,
// y un AuthenticationManager para autenticar a los usuarios mediante un UsernamePasswordAuthenticationToken.