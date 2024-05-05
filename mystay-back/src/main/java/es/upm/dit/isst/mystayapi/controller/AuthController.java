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
import es.upm.dit.isst.mystayapi.model.Empleado;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;
import es.upm.dit.isst.mystayapi.repository.EmpleadoRepository;

import java.util.Optional;

@RestController
public class AuthController {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Credentials credentials) {
        if (credentials.isAdmin()) {
            return ResponseEntity.ok(userAuthenticationProvider.createToken(credentials.getDNI(), "ROLE_ADMIN"));
        }

        Optional<Empleado> empleado = empleadoRepository.findByDNI(credentials.getDNI());

        if (empleado.isPresent()){
            if (empleado.get().getCorreo().equals(credentials.getNhab())) {
                return ResponseEntity.ok(userAuthenticationProvider.createToken(credentials.getDNI(), "ROLE_EMPLEADO"));
            }
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
        
        Optional<Cliente> cliente = clienteRepository.findByDNI(credentials.getDNI());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(401).body("DNI incorrecto");
        }

        Integer nhab = 0;

        try {
            nhab = Integer.parseInt(credentials.getNhab());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(401).body("Número de habitación no válido");
        }

        if (!cliente.get().getHabitacion().getNumero().equals(nhab)) {
            return ResponseEntity.status(401).body("Número de habitación incorrecto");
        }

        return ResponseEntity.ok(userAuthenticationProvider.createToken(credentials.getDNI(), "ROLE_CLIENTE"));
    }
}
