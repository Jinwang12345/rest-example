package edu.upc.dsa.services;

public class MaletaRequest {
    private String idVuelo;
    private String idUsuario;

    public MaletaRequest() { }

    public String getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(String idVuelo) {
        this.idVuelo = idVuelo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
