package com.ovniric.config;

import com.ovniric.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component //componente de Spring que se puede inyectar en otras clases como dependencia
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}

//La clase JwtAuthenticationFilter extiende la clase OncePerRequestFilter, lo que significa que se ejecutará una vez por cada solicitud HTTP.
//
//El método doFilterInternal() es el método principal del filtro.
// Este método se ejecutará para cada solicitud HTTP.
// El método verifica si la solicitud HTTP contiene un encabezado de autorización JWT válido y, si es así,
// autentica al usuario en la aplicación.
//
//Primero, el método verifica si la solicitud HTTP contiene un encabezado de autorización válido.
// Si no es así, el filtro llama al siguiente filtro en la cadena utilizando
// filterChain.doFilter(request, response) y se devuelve inmediatamente.
//
//Si la solicitud HTTP contiene un encabezado de autorización válido,
// el método extrae el JWT del encabezado y llama al método extractUsername() del objeto jwtService
// para extraer el nombre de usuario del JWT.
//
//A continuación, el método carga los detalles del usuario utilizando
// el objeto UserDetailsService y el nombre de usuario extraído del JWT.
// Si los detalles del usuario se cargan correctamente y el token JWT es válido,
// se crea un objeto UsernamePasswordAuthenticationToken y se establece en el contexto de seguridad de Spring.
//
//Finalmente, el filtro llama al siguiente filtro en la cadena utilizando filterChain.doFilter(request, response)
// para continuar con el procesamiento de la solicitud HTTP. En resumen, este filtro se utiliza para autenticar
// a los usuarios en la aplicación mediante un token JWT.
