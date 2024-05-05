package es.upm.dit.isst.mystayapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.model.Habitacion;
import es.upm.dit.isst.mystayapi.model.Hotel;
import es.upm.dit.isst.mystayapi.model.Pago;
import es.upm.dit.isst.mystayapi.model.Reserva;
import es.upm.dit.isst.mystayapi.model.Servicio;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;
import es.upm.dit.isst.mystayapi.repository.HabitacionRepository;
import es.upm.dit.isst.mystayapi.repository.HotelRepository;
import es.upm.dit.isst.mystayapi.repository.ReservaRepository;

@RestController
@RequestMapping
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private HabitacionRepository habitacionRepository;

    private Cliente getCliente() {
        return (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean pasarelaPago(Pago infoPago) {
        // Mandar a una pasarale
        if (infoPago.getBank() == null) {
            return false;
        }
        if (infoPago.getCvv() == null) {
            return false;
        }
        return true;
    }

    private boolean mandarServicioTPM(Servicio servicio, Cliente cliente) {

        // Send the service for the client cliente
        if (servicio.getNombre() == null) {
            return false;
        }

        if (servicio.getDescripcion() == null) {
            return false;
        }

        if (servicio.getFecha() == null) {
            return false;
        }

        return true;
    }

    private Optional<Habitacion> reservaHabitacion(Hotel hotel) {
        return habitacionRepository.findRandomByHotel(hotel.getID());
    }

    @Secured({ "ROLE_ADMIN", "ROLE_EMPLEADO" })
    @GetMapping("/clientes")
    List<Cliente> readAll(){
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Secured({ "ROLE_ADMIN", "ROLE_EMPLEADO" })
    @PostMapping("/clientes")
    ResponseEntity<Cliente> create(@RequestBody Cliente newCliente) throws URISyntaxException{
        if (clienteRepository.findByDNI(newCliente.getDNI()).isPresent()){
            return new ResponseEntity<Cliente>(HttpStatus.CONFLICT);
        }
        Cliente result = clienteRepository.save(newCliente);

        return ResponseEntity.created(new URI("/clientes" + result.getID())).body(result);
    }

    @Secured({ "ROLE_CLIENTE"})
    @GetMapping("/cliente")
    ResponseEntity<Cliente> read(){
        Cliente cliente = getCliente();
        return ResponseEntity.ok(cliente);
    }

    @Secured({ "ROLE_CLIENTE"})
    @PutMapping("/cliente")
    ResponseEntity<Cliente> partialUpdate(@RequestBody Cliente newCliente){
        Cliente cliente = getCliente();

        if (newCliente.getDNI() != null){
            cliente.setDNI(newCliente.getDNI());
        }
        if (newCliente.getNombre() != null){
            cliente.setNombre(newCliente.getNombre());
        }
        if (newCliente.getCorreo() != null){
            cliente.setCorreo(newCliente.getCorreo());
        }
        if (newCliente.getTelefono() != null){
            cliente.setTelefono(newCliente.getTelefono());
        }
        if (newCliente.getPremium() != null){
            cliente.setPremium(newCliente.getPremium());
        }
        if (newCliente.getGasto() != 0.0) {
            cliente.setGasto(newCliente.getGasto());
        }
        if (newCliente.getPagado() != null){
            cliente.setPagado(newCliente.getPagado());
        }
        if (newCliente.getHabitacion() != null){
            Habitacion habitacion = habitacionRepository.findById(newCliente.getHabitacion().getID()).get();
            cliente.setHabitacion(habitacion);
        }
        if (newCliente.getPassword() != null){
            cliente.setPassword(newCliente.getPassword());
        }
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clientes/{id}")
    ResponseEntity<Cliente> delete(@PathVariable Integer id){
        clienteRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @Secured("ROLE_CLIENTE")
    @GetMapping("/cliente/reservas")
    public ResponseEntity<List<Reserva>> reservas() {
        Cliente cliente = getCliente();

        return ResponseEntity.ok(reservaRepository.findByCliente(cliente));
    }

    @Secured("ROLE_CLIENTE")
    @PostMapping("/cliente/reservas")
    public ResponseEntity<String> newReserva(@RequestBody Reserva reserva) {
        Cliente cliente = getCliente();
        
        if (reserva.getFechaInicio() == null) {
            return ResponseEntity.status(400).body("Escoge una fecha de entrada");
        }

        if (reserva.getFechaFinal() == null) {
            return ResponseEntity.status(400).body("Escoge una fecha de salida");
        }

        if (reserva.getHotel() == null || reserva.getHotel().getID() == null ) {
            return ResponseEntity.status(400).body("Necesitas escoger un hotel");
        }

        Optional<Hotel> hotel = hotelRepository.findById(reserva.getHotel().getID());
        if (hotel.isEmpty()) {
            return ResponseEntity.status(400).body("No existe ese hotel");
        }
        
        reserva.setHotel(hotel.get());

        // Hablamos con el TPM para que nos de un numero de habitación    
        Optional<Habitacion> habitacion = reservaHabitacion(hotel.get());
        if (habitacion.isEmpty()) {
            return ResponseEntity.status(400).body("No hay habitaciones disponibles para ese hotel");
        }
        reserva.setHabitacion(habitacion.get());
        
        reserva.setCliente(cliente);
        reservaRepository.save(reserva);

        return ResponseEntity.ok("Se ha creado la reserva correctamente");
    }

    @Secured("ROLE_CLIENTE")
    @PostMapping("/cliente/pagar")
    public ResponseEntity<String> pagar(@RequestBody Pago infoPago) {
        Cliente cliente = getCliente();

        if (cliente.getPagado()) {
            return ResponseEntity.status(400).body("No hay ningún pago a procesar");
        }

        // Pasarela de pago de pago
        if (!pasarelaPago(infoPago)) {
            return ResponseEntity.status(400).body("No se ha podido procesar el pago");
        }

        cliente.setPagado(true);
        clienteRepository.save(cliente);

        return ResponseEntity.ok("Cuenta pagada correctamente");
    }

    @Secured("ROLE_CLIENTE")
    @PostMapping("/cliente/premium")
    public ResponseEntity<String> premium(@RequestBody Pago infoPago) {
        Cliente cliente = getCliente();

        if (cliente.getPremium()) {
            return ResponseEntity.status(400).body("Ya eres premium");
        }

        // Pasarela de pago de pago
        if (!pasarelaPago(infoPago)) {
            return ResponseEntity.status(400).body("No se ha podido procesar el pago");
        }

        cliente.setPremium(true);
        clienteRepository.save(cliente);

        return ResponseEntity.ok("Pago tramitado, eres premium!");
    }

    @Secured("ROLE_CLIENTE")
    @GetMapping("/cliente/factura")
    public ResponseEntity<String> factura() {
        Cliente cliente = getCliente();

        if (!cliente.getPagado()) {
            return ResponseEntity.status(400).body("No hay ninguna factura, paga primero para verla");
        }

        return ResponseEntity.ok(cliente.getGasto()+"");
    }

    @Secured("ROLE_CLIENTE")
    @PostMapping("/cliente/servicio")
    public ResponseEntity<?> newServicio(@RequestBody Servicio servicio) {
        Cliente cliente = getCliente();

        if (!mandarServicioTPM(servicio, cliente)) {
            return ResponseEntity.status(400).body("No se ha podido pedir el servicio");
        }
        
        return ResponseEntity.ok().body(servicio);
    }
    

    @Secured({ "ROLE_ADMIN", "ROLE_EMPLEADO" })
    @PutMapping("/clientes/{id}/habitacion/{habitacionid}")
    public ResponseEntity<?> actualizaHabitacion(@PathVariable Integer id, @PathVariable Integer habitacionid){
        return clienteRepository.findByID(id).map(cliente -> {
            Habitacion habitacion = habitacionRepository.findById(habitacionid).get();

            cliente.setHabitacion(habitacion);

            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());   
    }
    
    @Secured({ "ROLE_ADMIN", "ROLE_EMPLEADO" })
    @PutMapping("/clientes/{id}/hotel/{hotelid}")
    public ResponseEntity<?> actualizaHotel(@PathVariable Integer id, @PathVariable Integer hotelid){
        return clienteRepository.findByID(id).map(cliente -> {
            
            Hotel hotel = hotelRepository.findById(hotelid).get();
            cliente.setHotel(hotel);

            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());   
    }
}
