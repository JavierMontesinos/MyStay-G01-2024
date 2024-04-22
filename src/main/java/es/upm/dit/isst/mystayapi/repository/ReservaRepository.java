package es.upm.dit.isst.mystayapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Reserva;

public interface ReservaRepository extends CrudRepository<Reserva, Integer>{
    List<Reserva> findByCliente(Cliente cliente);
}
