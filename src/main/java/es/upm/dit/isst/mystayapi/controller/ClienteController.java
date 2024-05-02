package es.upm.dit.isst.mystayapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Reserva;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;
import es.upm.dit.isst.mystayapi.repository.ReservaRepository;

@RestController
@RequestMapping
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private Cliente getCliente() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/clientes")
    List<Cliente> readAll(){
        return (List<Cliente>) clienteRepository.findAll();
    }

    @PostMapping("/clientes")
    ResponseEntity<Cliente> create(@RequestBody Cliente newCliente) throws URISyntaxException{
        if (clienteRepository.findByDNI(newCliente.getDNI()).isPresent()){
            return new ResponseEntity<Cliente>(HttpStatus.CONFLICT);
        }
        Cliente result = clienteRepository.save(newCliente);

        return ResponseEntity.created(new URI("/clientes" + result.getID())).body(result);
    }

    @GetMapping("/cliente")
    ResponseEntity<Cliente> read(){
        Cliente cliente = getCliente();
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/cliente")
    ResponseEntity<Cliente> partialUpdate(@RequestBody Cliente newCliente){
        Cliente cliente = getCliente();

        if (newCliente.getDNI() != null){
            cliente.setDNI(newCliente.getDNI());
        }
        if (newCliente.getNombre() != null){
            cliente.setNombre(newCliente.getNombre());
        }
        if (newCliente.getCorreo() != null){
            cliente.setCorreo(newCliente.getCorreo());
        }
        if (newCliente.getTelefono() != null){
            cliente.setTelefono(newCliente.getTelefono());
        }
        if (newCliente.getPremium() != null){
            cliente.setPremium(newCliente.getPremium());
        }
        if (newCliente.getGasto() != 0.0) {
            cliente.setGasto(newCliente.getGasto());
        }
        if (newCliente.getPagado() != null){
            cliente.setPagado(newCliente.getPagado());
        }
        if (newCliente.getHabitacion() != null){
            cliente.setHabitacion(newCliente.getHabitacion());
        }
        if (newCliente.getPassword() != null){
            cliente.setPassword(newCliente.getPassword());
        }
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @DeleteMapping("/cliente")
    ResponseEntity<Cliente> delete(){
        Cliente cliente = getCliente();

        clienteRepository.delete(cliente);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/cliente/reservas")
    public ResponseEntity<List<Reserva>> reservas() {
        Cliente cliente = getCliente();

        return ResponseEntity.ok(reservaRepository.findByCliente(cliente));
    }
    

    @PutMapping("/clientes/{id}/habitacion/{habitacionid}")
    public ResponseEntity<?> actualizaHabitacion(@PathVariable Integer id, @PathVariable Habitacion habitacion){
        return clienteRepository.findByID(id).map(cliente -> {
            cliente.setHabitacion(habitacion);
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
            
    }
}
