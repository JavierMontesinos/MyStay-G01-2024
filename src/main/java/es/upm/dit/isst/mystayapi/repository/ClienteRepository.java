package es.upm.dit.isst.mystayapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.upm.dit.isst.mystayapi.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	List<Cliente> findByPagado(boolean pagado);
	Optional<Cliente> findByDNI(String dni);
	Optional <Cliente> findByID(Integer id);
}
