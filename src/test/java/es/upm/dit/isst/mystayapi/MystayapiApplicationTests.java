package es.upm.dit.isst.mystayapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;
import es.upm.dit.isst.mystayapi.repository.HabitacionRepository;
import es.upm.dit.isst.mystayapi.repository.HotelRepository;



@SpringBootTest
class MystayapiApplicationTests {

	@Autowired
	private ClienteRepository usuarioRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HabitacionRepository habitacionRepository;

	@Test
	void TestUsuarios() {

		Cliente cliente1=new Cliente();
		cliente1.setID(1);
		cliente1.setDNI("1234567A");
		cliente1.setNombre("usuariotest");
		cliente1.setCorreo("usuariotest@gmail.com");
		cliente1.setTelefono("686111111");
		cliente1.setPremium(false);
		cliente1.setGasto(0.0);
		cliente1.setPagado(false);

		Hotel hotel1 = new Hotel();
		hotel1.setID(1);
		hotel1.setNombre("hoteltest");
		hotel1.setDireccion("direcciontest");
		hotelRepository.save(hotel1);
		
		cliente1.setHotelID(hotel1);

		Habitacion habitacion1 = new Habitacion();
		habitacion1.setID(1);
		habitacion1.setNumero(1);
		habitacion1.setTipo("sencilla");
		habitacion1.setHotel(hotel1);
		habitacion1.setPrecio(100.0);
		habitacionRepository.save(habitacion1);
		
		cliente1.setHabitacion(habitacion1);

		usuarioRepository.save(cliente1);

		java.util.Optional<Cliente> usuarioTest = usuarioRepository.findByID(1);

		assertEquals(usuarioTest.get().getID(), 1);
	
	}

}
