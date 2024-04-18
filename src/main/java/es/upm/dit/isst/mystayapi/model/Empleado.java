package es.upm.dit.isst.mystayapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Entity
public class Empleado {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int ID;
    private String DNI;
    private String nombre;
    private String correo;
    private String telefono;
    private boolean disponible;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="HotelID",referencedColumnName="ID")		
	})
	private Hotel hotel;
    

    //Getters, Setters, HashCode, Equals, ToString

    // Getters
    public int getID() {
        return ID;
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setHotelID(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // HashCode and Equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((DNI == null) ? 0 : DNI.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
        result = prime * result + hotel.getID();
        result = prime * result + (disponible ? 1231 : 1237);
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
        Empleado other = (Empleado) obj;
        if (ID != other.ID)
            return false;
        if (DNI == null) {
            if (other.DNI != null)
                return false;
        } else if (!DNI.equals(other.DNI))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (correo == null) {
            if (other.correo != null)
                return false;
        } else if (!correo.equals(other.correo))
            return false;
        if (telefono == null) {
            if (other.telefono != null)
                return false;
        } else if (!telefono.equals(other.telefono))
            return false;
        if (hotel != other.hotel)
            return false;
        if (disponible != other.disponible)
            return false;
        return true;
    }

    // ToString
    @Override
    public String toString() {
        return "Empleado [ID=" + ID + ", DNI=" + DNI + ", nombre=" + nombre + ", correo=" + correo + ", telefono="
                + telefono + ", Hotel=" + hotel + ", disponible=" + disponible + "]";
    }

}
