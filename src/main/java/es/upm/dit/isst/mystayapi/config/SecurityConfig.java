package es.upm.dit.isst.mystayapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.upm.dit.isst.mystayapi.repository.ClienteRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(clienteRepository);

        http
            .csrf(csrf -> {csrf.disable();})
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/login").permitAll();
                auth.requestMatchers("/h2-console/**").permitAll();
                // auth.requestMatchers("/clientes").hasAnyRole("ADMIN");
                // auth.requestMatchers("/clientes/**").permitAll();
                // auth.requestMatchers("/empleados").permitAll();
                // auth.requestMatchers("/empleados/**").permitAll();
                // auth.requestMatchers("/hoteles").permitAll();
                // auth.requestMatchers("/hoteles/**").permitAll();
                // auth.requestMatchers("/habitaciones").permitAll();
                // auth.requestMatchers("/habitaciones/**").permitAll();
                // auth.requestMatchers("/recursos").permitAll();
                // auth.requestMatchers("/recursos/**").permitAll();
                // auth.requestMatchers("/reservas").permitAll();
                // auth.requestMatchers("/reservas/**").permitAll();
                // auth.requestMatchers("/servicios").permitAll();
                // auth.requestMatchers("/servicios/**").permitAll();
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            
            http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));
            


        return http.build();
     }
}