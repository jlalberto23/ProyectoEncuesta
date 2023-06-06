package com.example.proyectoencuesta;

public class respuestaUsuario {

    private int idRespuestaUsuario, idEncuesta, idPregunta, idUsuario, numeroIntento;
    private String fechaRespondido, dispositivo, textoRespuesta;
    private boolean esAnonima;

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public respuestaUsuario(int idRespuestaUsuario, int idEncuesta, int idPregunta, int idUsuario, int numeroIntento, String fechaRespondido, String dispositivo, boolean esAnonima, String textoRespuesta) {
        this.idRespuestaUsuario = idRespuestaUsuario;
        this.idEncuesta = idEncuesta;
        this.idPregunta = idPregunta;
        this.idUsuario = idUsuario;
        this.numeroIntento = numeroIntento;
        this.fechaRespondido = fechaRespondido;
        this.dispositivo = dispositivo;
        this.esAnonima = esAnonima;
        this.textoRespuesta = textoRespuesta;
    }

    public respuestaUsuario() {
    }

    public int getIdRespuestaUsuario() {
        return idRespuestaUsuario;
    }

    public void setIdRespuestaUsuario(int idRespuestaUsuario) {
        this.idRespuestaUsuario = idRespuestaUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumeroIntento() {
        return numeroIntento;
    }

    public void setNumeroIntento(int numeroIntento) {
        this.numeroIntento = numeroIntento;
    }

    public String getFechaRespondido() {
        return fechaRespondido;
    }

    public void setFechaRespondido(String fechaRespondido) {
        this.fechaRespondido = fechaRespondido;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public boolean isEsAnonima() {
        return esAnonima;
    }

    public void setEsAnonima(boolean esAnonima) {
        this.esAnonima = esAnonima;
    }
}