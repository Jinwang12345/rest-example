package edu.upc.dsa.models;

public class Maleta {
    private String idMaleta;
    private String idUsuario;

    // Constructor vacío
    public Maleta() { }

    // Constructor completo
    public Maleta(String idMaleta, String idUsuario) {
        this.idMaleta = idMaleta;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public String getIdMaleta() {
        return idMaleta;
    }

    public void setIdMaleta(String idMaleta) {
        this.idMaleta = idMaleta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Maleta{idMaleta='" + idMaleta + "', idUsuario='" + idUsuario + "'}";
    }
}
