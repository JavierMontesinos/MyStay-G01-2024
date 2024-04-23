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
import es.upm.dit.isst.mystayapi.repository.HotelRepository;

@RestController
@RequestMapping
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/hoteles")
    List<Hotel> readAll(){
        return (List<Hotel>) hotelRepository.findAll();
    }

    @PostMapping("/hoteles")
    ResponseEntity<Hotel> create(@RequestBody Hotel newHotel) throws URISyntaxException{
        if (hotelRepository.findById(newHotel.getID()).isPresent()){
            return new ResponseEntity<Hotel>(HttpStatus.CONFLICT);
        }
        Hotel result = hotelRepository.save(newHotel);

        return ResponseEntity.created(new URI("/hoteles" + result.getID())).body(result);
    }

    @GetMapping("/hoteles/{id}")
    ResponseEntity<Hotel> read(@PathVariable Integer id){
        return hotelRepository.findById(id).map(hotel -> ResponseEntity.ok(hotel))
            .orElse(new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/hoteles/{id}")
    ResponseEntity<Hotel> update(@RequestBody Hotel newHotel, @PathVariable Integer id) throws URISyntaxException{
        if (hotelRepository.findById(id).isPresent()){
            Hotel hotel = hotelRepository.findById(id).get();
            hotel.setNombre(newHotel.getNombre());
            hotel.setDireccion(newHotel.getDireccion());
            return ResponseEntity.ok(hotel);
        }
        return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/hoteles/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (hotelRepository.findById(id).isPresent()){
            hotelRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/hoteles/{id}")
    ResponseEntity<Hotel> updatePartial(@RequestBody Hotel newHotel, @PathVariable Integer id) throws URISyntaxException{
        if (hotelRepository.findById(id).isPresent()){
            Hotel hotel = hotelRepository.findById(id).get();
            if (newHotel.getNombre() != null){
                hotel.setNombre(newHotel.getNombre());
            }
            if (newHotel.getDireccion() != null){
                hotel.setDireccion(newHotel.getDireccion());
            }
            return ResponseEntity.ok(hotel);
        }
        return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
    }
    


}
