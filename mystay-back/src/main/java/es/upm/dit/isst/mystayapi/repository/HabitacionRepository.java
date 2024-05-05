package es.upm.dit.isst.mystayapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Hotel;

public interface HabitacionRepository extends CrudRepository<Habitacion, Integer>{
    
    @Query(value = "SELECT * FROM HABITACION WHERE HOTELID = :hotelId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Habitacion> findRandomByHotel(@Param("hotelId") Integer hotelId);

    Optional<List<Habitacion>> findAllByHotel(Hotel hotel);
}
