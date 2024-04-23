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
public class Habitacion {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer ID;
    private Integer numero;
    private String tipo;
    private double precio;
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

    public Integer getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public Hotel getHotel() {
        return hotel;
    }

    // Setters
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // HashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + hotel.getID();
        result = prime * result + numero;
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        long temp;
        temp = Double.doubleToLongBits(precio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Habitacion other = (Habitacion) obj;
        if (ID != other.ID)
            return false;
        if (hotel != other.hotel)
            return false;
        if (numero != other.numero)
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
            return false;
        return true;
    }

    // ToString
    @Override
    public String toString() {
        return "Habitacion [ID=" + ID + ", numero=" + numero + ", tipo=" + tipo + ", precio=" + precio + ", Hotel="
                + hotel + "]";
    }

}
