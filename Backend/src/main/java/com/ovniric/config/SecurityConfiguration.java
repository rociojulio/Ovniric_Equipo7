package com.ovniric.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    //Http security: permite configurar la seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()//solicitudes necesitan enviar un token csrf
                .disable()//desabilito la proteccion csrf
                .authorizeHttpRequests()//reglas de autorización
                .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**",
                        "/swagger-ui.html")//todas las solicitudes que empiezan con este endpoint no necesitan ser autenticadas
                .permitAll()//todas las permitidas
                .requestMatchers(HttpMethod.GET, "/api/productos/**","/api/localizaciones/**", "/api/imagenes/**",
                        "/api/categorias/**", "/api/caracteristicas/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios","/api/reservaciones")
                .hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**","/api/reservaciones/**")
                .hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/api/**")
                .hasAuthority("ADMIN")
                .anyRequest() //request que necesitan ser autenticadas
                .authenticated()//indica que necesitan ser autenticadas
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//no se crearán ni utilizarán sesiones http
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
