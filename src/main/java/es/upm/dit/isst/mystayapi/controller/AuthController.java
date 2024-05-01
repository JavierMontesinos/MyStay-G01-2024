package es.upm.dit.isst.mystayapi.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.mystayapi.config.UserAuthenticationProvider;
import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Credentials;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Credentials credentials) {
        Optional<Cliente> cliente = clienteRepository.findByDNI(credentials.getDNI());
        
        if (cliente.isEmpty()) {
            return ResponseEntity.status(401).body("DNI incorrecto");
        }

        if (cliente.get().getHabitacion().getNumero() != credentials.getNhab()) {
            return ResponseEntity.status(401).body("Número de habitación incorrecto");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", userAuthenticationProvider.createToken(credentials.getDNI()));
        response.put("id", cliente.get().getID());
        
        return ResponseEntity.ok(response);
    }
}
