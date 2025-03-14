package es.upm.dit.isst.mystayapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.repository.HabitacionRepository;
import es.upm.dit.isst.mystayapi.repository.HotelRepository;
import es.upm.dit.isst.mystayapi.model.Hotel;


@Secured( {"ROLE_ADMIN", "ROLE_EMPLEADO"})
@RestController
@RequestMapping
public class HabitacionController {
    @Autowired
    private HabitacionRepository habitacionRepository;
    
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/habitaciones")
    List<Habitacion> readAll(){
        return (List<Habitacion>) habitacionRepository.findAll();
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping("/habitaciones")
    ResponseEntity<Habitacion> create(@RequestBody Habitacion newHabitacion) throws URISyntaxException{
        if (habitacionRepository.findById(newHabitacion.getID()).isPresent()){
            return new ResponseEntity<Habitacion>(HttpStatus.CONFLICT);
        }
        Habitacion result = habitacionRepository.save(newHabitacion);

        return ResponseEntity.created(new URI("/habitaciones" + result.getID())).body(result);
    }

    @GetMapping("/habitaciones/{id}")
    ResponseEntity<Habitacion> read(@PathVariable Integer id){
        return habitacionRepository.findById(id).map(habitacion -> ResponseEntity.ok(habitacion))
            .orElse(new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/habitaciones/{id}")
    ResponseEntity<Habitacion> update(@RequestBody Habitacion newHabitacion, @PathVariable Integer id) throws URISyntaxException{
        if (habitacionRepository.findById(id).isPresent()){
            Habitacion habitacion = habitacionRepository.findById(id).get();
            habitacion.setNumero(newHabitacion.getNumero());
            habitacion.setTipo(newHabitacion.getTipo());
            habitacion.setPrecio(newHabitacion.getPrecio());
            habitacion.setHotel(newHabitacion.getHotel());
            habitacionRepository.save(habitacion);
            return ResponseEntity.ok(habitacion);
        } else {
            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Secured("ROLE_ADMIN")
    @PatchMapping("/habitaciones/{id}")
    ResponseEntity<Habitacion> updatePrecio(@RequestBody Habitacion newHabitacion, @PathVariable Integer id) throws URISyntaxException{
        if (habitacionRepository.findById(id).isPresent()){
            Habitacion habitacion = habitacionRepository.findById(id).get();
            habitacion.setPrecio(newHabitacion.getPrecio());
            habitacionRepository.save(habitacion);
            return ResponseEntity.ok(habitacion);
        } else {
            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/habitaciones/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (habitacionRepository.findById(id).isPresent()){
            habitacionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/habitaciones/{id}/hotel/{hotelid}")
    ResponseEntity<Habitacion> actualizaHotel(@PathVariable Integer id, @PathVariable Integer hotelid) throws URISyntaxException{
        if (habitacionRepository.findById(id).isPresent()){
            Habitacion habitacion = habitacionRepository.findById(id).get();
            Hotel hotel = hotelRepository.findById(hotelid).get();

            habitacion.setHotel(hotel);
            habitacionRepository.save(habitacion);
            return ResponseEntity.ok(habitacion);
        } else {
            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
        }
    }

}
