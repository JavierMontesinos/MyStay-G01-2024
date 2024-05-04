package es.upm.dit.isst.mystayapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.model.Reserva;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;
import es.upm.dit.isst.mystayapi.repository.HabitacionRepository;
import es.upm.dit.isst.mystayapi.repository.HotelRepository;
import es.upm.dit.isst.mystayapi.repository.ReservaRepository;



@SpringBootTest
class MystayapiApplicationTests {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HabitacionRepository habitacionRepository;
	@Autowired
	private ReservaRepository reservaRepository;

	@Test
	void TestClienteID() {
		Hotel hotel1 = new Hotel();
		hotel1.setID(1);
		hotel1.setNombre("hoteltest");
		hotel1.setDireccion("direcciontest");
		hotelRepository.save(hotel1);

		Habitacion habitacion1 = new Habitacion();
		habitacion1.setID(1);
		habitacion1.setNumero(1);
		habitacion1.setTipo("sencilla");
		habitacion1.setHotel(hotel1);
		habitacion1.setPrecio(100.0);
		habitacionRepository.save(habitacion1);

		Cliente cliente1=new Cliente();
		cliente1.setID(1);
		cliente1.setDNI("1234567A");
		cliente1.setNombre("usuariotest");
		cliente1.setCorreo("usuariotest@gmail.com");
		cliente1.setTelefono("686111111");
		cliente1.setPassword("1234");
		cliente1.setPremium(false);
		cliente1.setGasto(0.0);
		cliente1.setPagado(false);
		cliente1.setHotel(hotel1);
		cliente1.setHabitacion(habitacion1);
		clienteRepository.save(cliente1);

		java.util.Optional<Cliente> clienteTest1 = clienteRepository.findByID(1);

		assertEquals(clienteTest1.get().getID(), 1);
		assertEquals(clienteTest1.get().getDNI(), "1234567A");
		assertEquals(clienteTest1.get().getNombre(), "usuariotest");
		assertEquals(clienteTest1.get().getCorreo(), "usuariotest@gmail.com");
		assertEquals(clienteTest1.get().getTelefono(), "686111111");
		assertEquals(clienteTest1.get().getPassword(), "1234");
		assertFalse(clienteTest1.get().getPremium());
		assertEquals(clienteTest1.get().getGasto(), 0.0);
		assertFalse(clienteTest1.get().getPagado());
		assertEquals(clienteTest1.get().getHotel(), hotel1);
		assertEquals(clienteTest1.get().getHabitacion(), habitacion1);
	}

	@Test
	void TestClienteDNI() {
		Hotel hotel1 = new Hotel();
		hotel1.setID(1);
		hotel1.setNombre("hoteltest");
		hotel1.setDireccion("direcciontest");
		hotelRepository.save(hotel1);

		Habitacion habitacion1 = new Habitacion();
		habitacion1.setID(1);
		habitacion1.setNumero(1);
		habitacion1.setTipo("sencilla");
		habitacion1.setHotel(hotel1);
		habitacion1.setPrecio(100.0);
		habitacionRepository.save(habitacion1);

		Cliente cliente1=new Cliente();
		cliente1.setID(1);
		cliente1.setDNI("1234567A");
		cliente1.setNombre("usuariotest");
		cliente1.setCorreo("usuariotest@gmail.com");
		cliente1.setTelefono("686111111");
		cliente1.setPassword("1234");
		cliente1.setPremium(false);
		cliente1.setGasto(0.0);
		cliente1.setPagado(false);
		cliente1.setHotel(hotel1);
		cliente1.setHabitacion(habitacion1);
		clienteRepository.save(cliente1);

		java.util.Optional<Cliente>  clienteTest2 = clienteRepository.findByDNI("1234567A");

		assertEquals(clienteTest2.get().getDNI(), "1234567A");
		assertEquals(clienteTest2.get().getID(), 1);
		assertEquals(clienteTest2.get().getNombre(), "usuariotest");
		assertEquals(clienteTest2.get().getCorreo(), "usuariotest@gmail.com");
		assertEquals(clienteTest2.get().getTelefono(), "686111111");
		assertEquals(clienteTest2.get().getPassword(), "1234");
		assertFalse(clienteTest2.get().getPremium());
		assertEquals(clienteTest2.get().getGasto(), 0.0);
		assertFalse(clienteTest2.get().getPagado());
		assertEquals(clienteTest2.get().getHotel(), hotel1);
		assertEquals(clienteTest2.get().getHabitacion(), habitacion1);
	}

	@Test
	void TestClientePagado() {
		Hotel hotel1 = new Hotel();
		hotel1.setID(1);
		hotel1.setNombre("hoteltest");
		hotel1.setDireccion("direcciontest");
		hotelRepository.save(hotel1);

		Habitacion habitacion1 = new Habitacion();
		habitacion1.setID(1);
		habitacion1.setNumero(1);
		habitacion1.setTipo("sencilla");
		habitacion1.setHotel(hotel1);
		habitacion1.setPrecio(100.0);
		habitacionRepository.save(habitacion1);

		Cliente cliente1=new Cliente();
		cliente1.setID(1);
		cliente1.setDNI("1234567A");
		cliente1.setNombre("usuariotest");
		cliente1.setCorreo("usuariotest@gmail.com");
		cliente1.setTelefono("686111111");
		cliente1.setPassword("1234");
		cliente1.setPremium(false);
		cliente1.setGasto(0.0);
		cliente1.setPagado(true);
		cliente1.setHotel(hotel1);
		cliente1.setHabitacion(habitacion1);
		clienteRepository.save(cliente1);

		List<Cliente> usuariosNoPagados = clienteRepository.findByPagado(true);
		
		// Verificar que la lista de clientes no pagados no esté vacía
		assertFalse(usuariosNoPagados.isEmpty());

		// Verificar que cada cliente en la lista tenga el estado de pago correcto
		for (Cliente cliente : usuariosNoPagados) {
			assertFalse(!cliente.getPagado());
		}
	}

	@Test
	void TestReserva() {
		Hotel hotel1 = new Hotel();
		hotel1.setID(1);
		hotel1.setNombre("hoteltest");
		hotel1.setDireccion("direcciontest");
		hotelRepository.save(hotel1);

		Habitacion habitacion1 = new Habitacion();
		habitacion1.setID(1);
		habitacion1.setNumero(1);
		habitacion1.setTipo("sencilla");
		habitacion1.setHotel(hotel1);
		habitacion1.setPrecio(100.0);
		habitacionRepository.save(habitacion1);

		Cliente cliente1 = new Cliente();
		cliente1.setID(1);
		cliente1.setDNI("1234567A");
		cliente1.setNombre("usuariotest");
		cliente1.setCorreo("usuariotest@gmail.com");
		cliente1.setTelefono("686111111");
		cliente1.setPremium(false);
		cliente1.setGasto(0.0);
		cliente1.setPagado(false);
		cliente1.setHotel(hotel1);
		cliente1.setHabitacion(habitacion1);
		clienteRepository.save(cliente1);

		Reserva reserva1 = new Reserva();
		reserva1.setID(null);
		reserva1.setFechaInicio(null);
		reserva1.setFechaFinal(null);
		reserva1.setLlave(null);
		reserva1.setCliente(cliente1);
		reserva1.setHotel(hotel1);
		reserva1.setHabitacion(habitacion1);
		reservaRepository.save(reserva1);
		
		List<Reserva> reservasCliente = reservaRepository.findByCliente(cliente1);
		
		assertFalse(reservasCliente.isEmpty());

		for (Reserva reserva : reservasCliente) {
			assertEquals(reserva.getCliente().getID(), 1);
			assertEquals(reserva.getCliente().getDNI(), "1234567A");
			assertEquals(reserva.getCliente().getNombre(), "usuariotest");
			assertEquals(reserva.getCliente().getCorreo(), "usuariotest@gmail.com");
			assertEquals(reserva.getCliente().getTelefono(), "686111111");
			assertFalse(reserva.getCliente().getPremium());
			assertEquals(reserva.getCliente().getGasto(), 0.0);
			assertFalse(reserva.getCliente().getPagado());
			assertEquals(reserva.getCliente().getHotel(), hotel1);
			assertEquals(reserva.getCliente().getHabitacion(), habitacion1);
		}
	}
}
