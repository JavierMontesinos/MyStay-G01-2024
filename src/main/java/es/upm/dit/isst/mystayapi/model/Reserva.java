package es.upm.dit.isst.mystayapi.model;

import java.util.Objects;
import jakarta.persistence.*;
import java.sql.Time;


@Entity
public class Reserva {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private int ID;
    @ManyToOne
    @JoinColumn(name = "ClienteID")
    private Client client;
    private Client Client;
    private Time FechaInicio;
    private Time FechaFinal;
    private String Llave;
    @ManyToOne
    @JoinColumn(name = "HotelID")

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

    public Time getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Time FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Time getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(Time FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public String getLlave() {
        return Llave;
    }

    public void setLlave(String Llave) {
        this.Llave = Llave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return ID == reserva.ID &&
                Objects.equals(Client, reserva.Client) &&
                Objects.equals(FechaInicio, reserva.FechaInicio) &&
                Objects.equals(FechaFinal, reserva.FechaFinal) &&
                Objects.equals(Llave, reserva.Llave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Client, FechaInicio, FechaFinal, Llave);
    }
}
