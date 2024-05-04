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
public class Recurso {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer ID;
    private String nombre;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="HotelID",referencedColumnName="ID")		
	})
	private Hotel hotel;

    //Getters, Setters, HashCode, Equals, ToString
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + hotel.getID();
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
        Recurso other = (Recurso) obj;
        if (ID != other.ID)
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (hotel != other.hotel)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Recurso [ID=" + ID + ", nombre=" + nombre + ", Hotel=" + hotel + "]";
    }
    

}
