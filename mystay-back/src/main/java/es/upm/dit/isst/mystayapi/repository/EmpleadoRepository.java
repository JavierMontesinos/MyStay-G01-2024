package es.upm.dit.isst.mystayapi.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.mystayapi.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer>{
    // List<Empleado> findByHotel(Hotel hotel);
    Optional<Empleado> findByDNI(String dni);
}