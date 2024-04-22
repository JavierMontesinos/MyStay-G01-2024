package es.upm.dit.isst.mystayapi.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.mystayapi.model.Empleado;
import es.upm.dit.isst.mystayapi.model.Hotel;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer>{
    // List<Empleado> findByHotel(Hotel hotel);
}