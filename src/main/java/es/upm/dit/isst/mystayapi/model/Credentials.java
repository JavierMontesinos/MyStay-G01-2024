package es.upm.dit.isst.mystayapi.model;

public class Credentials {
    private String dni;
    private Integer nhab;

    public String getDNI() {
        return this.dni;
    }

    public Integer getNhab() {
        return this.nhab;
    }

    public boolean isAdmin() {
        return this.dni.equals("admin") && this.nhab.equals(12345);
    }
}
