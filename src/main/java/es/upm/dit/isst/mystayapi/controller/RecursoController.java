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
import es.upm.dit.isst.mystayapi.model.Recurso;
import es.upm.dit.isst.mystayapi.repository.RecursoRepository;


@RestController
@RequestMapping
public class RecursoController {
    @Autowired
    private RecursoRepository recursoRepository;

    @GetMapping("/recursos")
    List<Recurso> readAll(){
        return (List<Recurso>) recursoRepository.findAll();
    }

    @PostMapping("/recursos")
    ResponseEntity<Recurso> create(@RequestBody Recurso newRecurso) throws URISyntaxException{
        if (recursoRepository.findById(newRecurso.getID()).isPresent()){
            return new ResponseEntity<Recurso>(HttpStatus.CONFLICT);
        }
        Recurso result = recursoRepository.save(newRecurso);

        return ResponseEntity.created(new URI("/recursos" + result.getID())).body(result);
    }

    @GetMapping("/recursos/{id}")
    ResponseEntity<Recurso> read(@PathVariable Integer id){
        return recursoRepository.findById(id).map(recurso -> ResponseEntity.ok(recurso))
            .orElse(new ResponseEntity<Recurso>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/recursos/{id}")
    ResponseEntity<Recurso> update(@RequestBody Recurso newRecurso, @PathVariable Integer id) throws URISyntaxException{
        if (recursoRepository.findById(id).isPresent()){
            Recurso recurso = recursoRepository.findById(id).get();
            recurso.setNombre(newRecurso.getNombre());
            recurso.setHotel(newRecurso.getHotel());
            return ResponseEntity.ok(recurso);
        }
        return new ResponseEntity<Recurso>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/recursos/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id){
        if (recursoRepository.findById(id).isPresent()){
            recursoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Recurso>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/recursos/{id}/hotel/{id}")
    ResponseEntity<Recurso> updateHotel(@PathVariable Integer id, @PathVariable Hotel hotel) throws URISyntaxException{
        if (recursoRepository.findById(id).isPresent()){
            Recurso recurso = recursoRepository.findById(id).get();
            recurso.setHotel(hotel);
            recursoRepository.save(recurso);
            return ResponseEntity.ok(recurso);
        }
        return new ResponseEntity<Recurso>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/recursos/{id}")
    ResponseEntity<Recurso> updatePartial(@RequestBody Recurso newRecurso, @PathVariable Integer id) throws URISyntaxException{
        if (recursoRepository.findById(id).isPresent()){
            Recurso recurso = recursoRepository.findById(id).get();
            if (newRecurso.getNombre() != null){
                recurso.setNombre(newRecurso.getNombre());
            }
            if (newRecurso.getHotel() != null){
                recurso.setHotel(newRecurso.getHotel());
            }
            return ResponseEntity.ok(recurso);
        }
        return new ResponseEntity<Recurso>(HttpStatus.NOT_FOUND);
    }

}
