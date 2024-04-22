package es.upm.dit.isst.mystayapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.mystayapi.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	List<Cliente> findByPagado(boolean pagado);
	Optional <Cliente> findByID(Integer id);
}
