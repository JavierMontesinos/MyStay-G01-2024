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

import es.upm.dit.isst.mystayapi.model.Servicio;
import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.model.Empleado;
import es.upm.dit.isst.mystayapi.model.Reserva;
import es.upm.dit.isst.mystayapi.repository.ServicioRepository;



@RestController
@RequestMapping
public class ServicioController {
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/servicios")
    List<Servicio> readAll(){
        return (List<Servicio>) servicioRepository.findAll();
    }

    @PostMapping("/servicios")
    ResponseEntity<Servicio> create(@RequestBody Servicio newServicio) throws URISyntaxException{
        if (servicioRepository.findById(newServicio.getID()).isPresent()){
            return new ResponseEntity<Servicio>(HttpStatus.CONFLICT);
        }
        Servicio result = servicioRepository.save(newServicio);

        return ResponseEntity.created(new URI("/servicios" + result.getID())).body(result);
    }

    @GetMapping("/servicios/{id}")
    ResponseEntity<Servicio> read(@PathVariable Integer id){
        return servicioRepository.findById(id).map(servicio -> ResponseEntity.ok(servicio))
            .orElse(new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/servicios/{id}")
    ResponseEntity<Servicio> update(@RequestBody Servicio newServicio, @PathVariable Integer id) throws URISyntaxException{
        if (servicioRepository.findById(id).isPresent()){
            Servicio servicio = servicioRepository.findById(id).get();
            servicio.setNombre(newServicio.getNombre());
            servicio.setPrecio(newServicio.getPrecio());
            servicio.setDescripcion(newServicio.getDescripcion());
            servicio.setFecha(newServicio.getFecha());
            servicio.setEsPremium(newServicio.isEsPremium());
            servicio.setHotel(newServicio.getHotel());
            servicio.setEmpleado(newServicio.getEmpleado());
            servicio.setReserva(newServicio.getReserva());

            return ResponseEntity.ok(servicio);
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/servicios/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (servicioRepository.findById(id).isPresent()){
            servicioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/servicios/{id}")
    ResponseEntity<Servicio> updatePartial(@RequestBody Servicio newServicio, @PathVariable Integer id) throws URISyntaxException{
        if (servicioRepository.findById(id).isPresent()){
            Servicio servicio = servicioRepository.findById(id).get();
            if (newServicio.getNombre() != null){
                servicio.setNombre(newServicio.getNombre());
            }
            if (newServicio.getPrecio() != 0){
                servicio.setPrecio(newServicio.getPrecio());
            }
            if (newServicio.getDescripcion() != null){
                servicio.setDescripcion(newServicio.getDescripcion());
            }
            if (newServicio.getFecha() != null){
                servicio.setFecha(newServicio.getFecha());
            }
            if (newServicio.isEsPremium() != false){
                servicio.setEsPremium(newServicio.isEsPremium());
            }
            if (newServicio.getHotel() != null){
                servicio.setHotel(newServicio.getHotel());
            }
            if (newServicio.getEmpleado() != null){
                servicio.setEmpleado(newServicio.getEmpleado());
            }
            if (newServicio.getReserva() != null){
                servicio.setReserva(newServicio.getReserva());
            }
            return ResponseEntity.ok(servicio);
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/servicios/{id}/hotel/{Hotel.id}")
    ResponseEntity<Servicio> updateHotel(@RequestBody Hotel newHotel, @PathVariable Integer id) throws URISyntaxException{
        if (servicioRepository.findById(id).isPresent()){
            Servicio servicio = servicioRepository.findById(id).get();
            servicio.setHotel(newHotel);
            return ResponseEntity.ok(servicio);
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/servicios/{id}/empleado/{Empleado.id}")
    ResponseEntity<Servicio> updateEmpleado(@RequestBody Empleado newEmpleado, @PathVariable Integer id) throws URISyntaxException{
        if (servicioRepository.findById(id).isPresent()){
            Servicio servicio = servicioRepository.findById(id).get();
            servicio.setEmpleado(newEmpleado);
            return ResponseEntity.ok(servicio);
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/servicios/{id}/reserva/{Reserva.id}")
    ResponseEntity<Servicio> updateReserva(@RequestBody Reserva newReserva, @PathVariable Integer id) throws URISyntaxException{
        if (servicioRepository.findById(id).isPresent()){
            Servicio servicio = servicioRepository.findById(id).get();
            servicio.setReserva(newReserva);
            return ResponseEntity.ok(servicio);
        }
        return new ResponseEntity<Servicio>(HttpStatus.NOT_FOUND);
    }

}
