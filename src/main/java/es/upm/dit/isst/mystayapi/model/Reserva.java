package es.upm.dit.isst.mystayapi.model;

import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;

import java.sql.Date;

@Entity
public class Reserva {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Integer ID;
    @Future private Date FechaInicio;
    @Future private Date FechaFinal;
    private String Llave;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="ClienteID",referencedColumnName="ID")		
	})
	private Cliente cliente;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name="HabitacionID",referencedColumnName="ID")
    })
    private Habitacion habitacion;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name="HotelID",referencedColumnName="ID")
    })
    private Hotel hotel;

    //Getters, Setters, HashCode, Equals, ToString

    // Getters
    public Integer getID() {
        return ID;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public Date getFechaFinal() {
        return FechaFinal;
    }

    public String getLlave() {
        return Llave;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Hotel getHotel() {
        return hotel;
    }

    // Setters
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public void setFechaFinal(Date FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public void setLlave(String Llave) {
        this.Llave = Llave;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // HashCode
    @Override
    public int hashCode() {
        return Objects.hash(ID, FechaInicio, FechaFinal, Llave, cliente, habitacion, hotel);
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Reserva reserva = (Reserva) obj;
        return ID == reserva.ID && cliente == reserva.cliente && habitacion == reserva.habitacion
                && hotel == reserva.hotel && Objects.equals(FechaInicio, reserva.FechaInicio)
                && Objects.equals(FechaFinal, reserva.FechaFinal) && Objects.equals(Llave, reserva.Llave);
    }

    // ToString
    @Override
    public String toString() {
        return "Reserva{" +
                "ID=" + ID +
                ", FechaInicio=" + FechaInicio +
                ", FechaFinal=" + FechaFinal +
                ", Llave='" + Llave + '\'' +
                ", Cliente=" + cliente +
                ", Habitacion=" + habitacion +
                ", Hotel=" + hotel +
                '}';
    }

}