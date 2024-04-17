package es.upm.dit.isst.mystayapi.model;

import java.util.Objects;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class Servicio {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private int ID;
    @ManyToOne
    @JoinColumn(name = "ClienteID")
    private Client Client;
    private String Tipo;
    private String Descripcion;
    private Time Fecha;



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Client getClient() {
        return Client;
    }

    public void setClient(Client Client) {
        this.Client = Client;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Time getFecha() {
        return Fecha;
    }

    public void setFecha(Time Fecha) {
        this.Fecha = Fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return ID == servicio.ID &&
                Objects.equals(Client, servicio.Client) &&
                Objects.equals(Tipo, servicio.Tipo) &&
                Objects.equals(Descripcion, servicio.Descripcion) &&
                Objects.equals(Fecha, servicio.Fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Client, Tipo, Descripcion, Fecha);
    }
}