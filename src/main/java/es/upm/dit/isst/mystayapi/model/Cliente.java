package es.upm.dit.isst.mystayapi.model;

import jakarta.persistence.*;


@Entity
public class Cliente {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Integer ID;
    private String DNI;
    private String nombre;
    private String correo;
    private String telefono;
    private boolean Premium;
    private double Gasto;
    private boolean Pagado;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="HotelID",referencedColumnName="ID")		
	})
	private Hotel hotel;
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="HabitacionID",referencedColumnName="ID")		
	})
	private Habitacion habitacion;

    //Getters, Setters, HashCode, Equals, ToString
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getPremium() {
        return Premium;
    }

    public void setPremium(boolean premium) {
        Premium = premium;
    }

    public double getGasto() {
        return Gasto;
    }

    public void setGasto(double gasto) {
        Gasto = gasto;
    }

    public boolean getPagado() {
        return Pagado;
    }

    public void setPagado(boolean pagado) {
        Pagado = pagado;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotelID(Hotel hotel) {
        this.hotel = hotel;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((DNI == null) ? 0 : DNI.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
        result = prime * result + hotel.getID();
        result = prime * result + habitacion.getID();
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
        Cliente other = (Cliente) obj;
        if (DNI == null) {
            if (other.DNI != null)
                return false;
        } else if (!DNI.equals(other.DNI))
            return false;
        if (correo == null) {
            if (other.correo != null)
                return false;
        } else if (!correo.equals(other.correo))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (telefono == null) {
            if (other.telefono != null)
                return false;
        } else if (!telefono.equals(other.telefono))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client [ID=" + ID + ", DNI=" + DNI + ", nombre=" + nombre + ", correo=" + correo + ", telefono="
                + telefono + ", Premium=" + Premium + ", Gasto=" + Gasto + ", Pagado=" + Pagado + ", Hotel=" + hotel
                + ", Habitacion=" + habitacion + "]";
    }

    

    
}
