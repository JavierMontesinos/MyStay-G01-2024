package es.upm.dit.isst.mystayapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;

import java.sql.Date;

@Entity
public class Servicio {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Integer ID;
    private String nombre;
    private String descripcion;
    @Future private Date fecha;
    private boolean esPremium;
    private double precio;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="HotelID",referencedColumnName="ID")		
	})
	private Hotel hotel;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name="EmpleadoID",referencedColumnName="ID")
    })
    private Empleado empleado;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name="ReservaID",referencedColumnName="ID")
    })
    private Reserva reserva;

    //Getters, Setters, HashCode, Equals, ToString
    // Getters
    public Integer getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isEsPremium() {
        return esPremium;
    }

    public double getPrecio() {
        return precio;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    // Setters
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEsPremium(boolean esPremium) {
        this.esPremium = esPremium;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    // HashCode and Equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Servicio other = (Servicio) obj;
        if (ID != other.ID)
            return false;
        return true;
    }

    // ToString
    @Override
    public String toString() {
        return "Servicio [ID=" + ID + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
                + ", esPremium=" + esPremium + ", precio=" + precio + ", Hotel=" + hotel + ", EmpleadoID="
                + empleado + ", Reserva=" + reserva + "]";
    }




   
}