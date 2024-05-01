package es.upm.dit.isst.mystayapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;

@RestController
@RequestMapping
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

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

    @GetMapping("/clientes/{id}")
    ResponseEntity<Cliente> read(@PathVariable Integer id){
        return clienteRepository.findById(id).map(cliente -> ResponseEntity.ok(cliente))
            .orElse(new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/clientes/{id}")
    ResponseEntity<Cliente> partialUpdate(@RequestBody Cliente newCliente, @PathVariable Integer id){
        System.out.println(newCliente.getPremium());
    return clienteRepository.findById(id).map(cliente -> {
        System.out.println(cliente);

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
    }).orElseGet(() -> new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/clientes/{id}")
    ResponseEntity<Cliente> delete(@PathVariable Integer id){
        clienteRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/clientes/{id}/habitacion/{habitacionid}")
    public ResponseEntity<?> actualizaHabitacion(@PathVariable Integer id, @PathVariable Habitacion habitacion){
        return clienteRepository.findByID(id).map(cliente -> {
            cliente.setHabitacion(habitacion);
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
            
    }
}
