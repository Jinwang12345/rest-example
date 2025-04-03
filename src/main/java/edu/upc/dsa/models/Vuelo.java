package edu.upc.dsa.models;

public class Vuelo {
    private String idVuelo;
    private String horaSalida;
    private String horaLlegada;
    private String idAvion;
    private String origen;
    private String destino;

    // Constructor vacío
    public Vuelo() { }

    // Constructor completo
    public Vuelo(String idVuelo, String horaSalida, String horaLlegada, String idAvion, String origen, String destino) {
        this.idVuelo = idVuelo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.idAvion = idAvion;
        this.origen = origen;
        this.destino = destino;
    }

    // Getters y Setters
    public String getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(String idVuelo) {
        this.idVuelo = idVuelo;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "Vuelo{idVuelo='" + idVuelo + "', horaSalida='" + horaSalida + "', horaLlegada='" + horaLlegada +
                "', idAvion='" + idAvion + "', origen='" + origen + "', destino='" + destino + "'}";
    }
}
