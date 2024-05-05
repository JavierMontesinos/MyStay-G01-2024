package es.upm.dit.isst.mystayapi.model;

public class Credentials {
    private String dni;
    private String nhab;

    public String getDNI() {
        return this.dni;
    }

    public String getNhab() {
        return this.nhab;
    }

    public boolean isAdmin() {
        return this.dni.equals("admin") && this.nhab.equals("admin");
    }
}
