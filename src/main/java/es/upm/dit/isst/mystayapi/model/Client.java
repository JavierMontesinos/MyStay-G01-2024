package es.upm.dit.isst.mystayapi.model;
import java.util.List;

import jakarta.persistence.*;


@Entity
public class Client {
    @Id 
    @GeneratedValue( strategy=GenerationType.AUTO )
    private int ID;
    private String DNI;
    private int Nhab;
    private boolean Premium;
    private double Gasto;
    private boolean Pagado;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Servicio> servicios;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getNhab() {
        return Nhab;
    }

    public void setNhab(int Nhab) {
        this.Nhab = Nhab;
    }

    public boolean isPremium() {
        return Premium;
    }

    public void setPremium(boolean Premium) {
        this.Premium = Premium;
    }

    public double getGasto() {
        return Gasto;
    }

    public void setGasto(double Gasto) {
        this.Gasto = Gasto;
    }

    public boolean isPagado() {
        return Pagado;
    }

    public void setPagado(boolean Pagado) {
        this.Pagado = Pagado;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> Reservas) {
        this.reservas = Reservas;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> Servicios) {
        this.servicios = Servicios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((DNI == null) ? 0 : DNI.hashCode());
        result = prime * result + Nhab;
        result = prime * result + (Premium ? 1231 : 1237);
        long temp;
        temp = Double.doubleToLongBits(Gasto);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (Pagado ? 1231 : 1237);
        result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
        result = prime * result + ((servicios == null) ? 0 : servicios.hashCode());
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
        Client other = (Client) obj;
        if (ID != other.ID)
            return false;
        if (DNI == null) {
            if (other.DNI != null)
                return false;
        } else if (!DNI.equals(other.DNI))
            return false;
        if (Nhab != other.Nhab)
            return false;
        if (Premium != other.Premium)
            return false;
        if (Double.doubleToLongBits(Gasto) != Double.doubleToLongBits(other.Gasto))
            return false;
        if (Pagado != other.Pagado)
            return false;
        if (reservas == null) {
            if (other.reservas != null)
                return false;
        } else if (!reservas.equals(other.reservas))
            return false;
        if (servicios == null) {
            if (other.servicios != null)
                return false;
        } else if (!servicios.equals(other.servicios))
            return false;
        return true;
    }
}
