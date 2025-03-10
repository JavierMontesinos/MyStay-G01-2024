package es.upm.dit.isst.mystayapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

import es.upm.dit.isst.mystayapi.model.Empleado;
import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.repository.EmpleadoRepository;
import es.upm.dit.isst.mystayapi.repository.HotelRepository;



@Secured("ROLE_ADMIN")
@RestController
@RequestMapping
public class EmpleadoController {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/empleados")
    List<Empleado> readAll(){
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @PostMapping("/empleados")
    ResponseEntity<Empleado> create(@RequestBody Empleado newEmpleado) throws URISyntaxException{
        if (empleadoRepository.findById(newEmpleado.getID()).isPresent()){
            return new ResponseEntity<Empleado>(HttpStatus.CONFLICT);
        }
        Empleado result = empleadoRepository.save(newEmpleado);

        return ResponseEntity.created(new URI("/empleados" + result.getID())).body(result);
    }

    @GetMapping("/empleados/{id}")
    ResponseEntity<Empleado> read(@PathVariable Integer id){
        return empleadoRepository.findById(id).map(empleado -> ResponseEntity.ok(empleado))
            .orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/empleados/{id}")
    ResponseEntity<Empleado> update(@RequestBody Empleado newEmpleado, @PathVariable Integer id) throws URISyntaxException{
        if (empleadoRepository.findById(id).isPresent()){
            Empleado empleado = empleadoRepository.findById(id).get();
            empleado.setDNI(newEmpleado.getDNI());
            empleado.setNombre(newEmpleado.getNombre());
            empleado.setCorreo(newEmpleado.getCorreo());
            empleado.setTelefono(newEmpleado.getTelefono());
            empleado.setDisponible(newEmpleado.isDisponible());
            empleado.setHotel(newEmpleado.getHotel());
            empleadoRepository.save(empleado);
            return ResponseEntity.ok(empleado);
        } else {
            return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/empleados/{id}")
    ResponseEntity<Empleado> updateDisponible(@RequestBody Empleado newEmpleado, @PathVariable Integer id) throws URISyntaxException{
        if (empleadoRepository.findById(id).isPresent()){
            Empleado empleado = empleadoRepository.findById(id).get();
            empleado.setDisponible(newEmpleado.isDisponible());
            empleadoRepository.save(empleado);
            return ResponseEntity.ok(empleado);
        } else {
            return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/empleados/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (empleadoRepository.findById(id).isPresent()){
            empleadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/empleados/{id}/hotel/{hotelid}")
    ResponseEntity<Empleado> updateHotel(@PathVariable Integer id, @PathVariable Integer hotelid) throws URISyntaxException{
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()){
            Hotel hotel = hotelRepository.findById(hotelid).get();
            Empleado empleadoUnwrapped = empleado.get();
            empleadoUnwrapped.setHotel(hotel);
            empleadoRepository.save(empleadoUnwrapped);
            return ResponseEntity.ok(empleadoUnwrapped);
        } else {
            return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND);
        }
    }
    


}    
