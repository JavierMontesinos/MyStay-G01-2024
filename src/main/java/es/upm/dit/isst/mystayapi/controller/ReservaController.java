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

import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.model.Reserva;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.repository.ReservaRepository;

@RestController
@RequestMapping
public class ReservaController {
    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/reservas")
    List<Reserva> readAll(){
        return (List<Reserva>) reservaRepository.findAll();
    }

    @PostMapping("/reservas")
    ResponseEntity<Reserva> create(@RequestBody Reserva newReserva) throws URISyntaxException{
        if (reservaRepository.findById(newReserva.getID()).isPresent()){
            return new ResponseEntity<Reserva>(HttpStatus.CONFLICT);
        }
        Reserva result = reservaRepository.save(newReserva);

        return ResponseEntity.created(new URI("/reservas" + result.getID())).body(result);
    }

    @GetMapping("/reservas/{id}")
    ResponseEntity<Reserva> read(@PathVariable Integer id){
        return reservaRepository.findById(id).map(reserva -> ResponseEntity.ok(reserva))
            .orElse(new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND));

    }
    
    @PutMapping("/reservas/{id}")
    ResponseEntity<Reserva> update(@RequestBody Reserva newReserva, @PathVariable Integer id) throws URISyntaxException{
        if (reservaRepository.findById(id).isPresent()){
            Reserva reserva = reservaRepository.findById(id).get();
            reserva.setFechaInicio(newReserva.getFechaInicio());
            reserva.setFechaFinal(newReserva.getFechaFinal());
            reserva.setLlave(newReserva.getLlave());
            reserva.setCliente(newReserva.getCliente());
            reserva.setHabitacion(newReserva.getHabitacion());
            reserva.setHotel(newReserva.getHotel());
            return ResponseEntity.ok(reserva);
        }
        return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/reservas/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (reservaRepository.findById(id).isPresent()){
            reservaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/reservas/{id}")
    ResponseEntity<Reserva> updatePartial(@RequestBody Reserva newReserva, @PathVariable Integer id){
        return reservaRepository.findById(id).map(reserva -> {
            if (newReserva.getFechaInicio() != null){
                reserva.setFechaInicio(newReserva.getFechaInicio());
            }
            if (newReserva.getFechaFinal() != null){
                reserva.setFechaFinal(newReserva.getFechaFinal());
            }
            if (newReserva.getLlave() != null){
                reserva.setLlave(newReserva.getLlave());
            }
            if (newReserva.getCliente() != null){
                reserva.setCliente(newReserva.getCliente());
            }
            if (newReserva.getHabitacion() != null){
                reserva.setHabitacion(newReserva.getHabitacion());
            }
            if (newReserva.getHotel() != null){
                reserva.setHotel(newReserva.getHotel());
            }
            return ResponseEntity.ok(reservaRepository.save(reserva));
        }).orElse(new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/reservas/{id}/cliente/{id}")
    ResponseEntity<Reserva> updateCliente(@PathVariable Integer id, @PathVariable Cliente cliente) throws URISyntaxException{
        if (reservaRepository.findById(id).isPresent()){
            Reserva reserva = reservaRepository.findById(id).get();
            reserva.setCliente(cliente);
            reservaRepository.save(reserva);
            return ResponseEntity.ok(reserva);
        }
        return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reservas/{id}/habitacion/{id}")
    ResponseEntity<Reserva> updateHabitacion(@PathVariable Integer id, @PathVariable Habitacion habitacion) throws URISyntaxException{
        if (reservaRepository.findById(id).isPresent()){
            Reserva reserva = reservaRepository.findById(id).get();
            reserva.setHabitacion(habitacion);
            reservaRepository.save(reserva);
            return ResponseEntity.ok(reserva);
        }
        return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reservas/{id}/hotel/{ID}")
    ResponseEntity<Reserva> updateHotel(@PathVariable Integer ID, @PathVariable Hotel hotel) throws URISyntaxException{
        if (reservaRepository.findById(ID).isPresent()){
            Reserva reserva = reservaRepository.findById(ID).get();
            reserva.setHotel(hotel);
            reservaRepository.save(reserva);
            return ResponseEntity.ok(reserva);
        }
        return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
    }


}
