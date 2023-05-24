package com.example.proyectoencuesta;

public class tipoRespuesta {

    private int idTipoRespuesta;
    private String nombreTipoRespuesta;

    public tipoRespuesta(int idTipoRespuesta, String nombreTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
        this.nombreTipoRespuesta = nombreTipoRespuesta;
    }

    public tipoRespuesta() {
    }

    public int getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    public void setIdTipoRespuesta(int idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
    }

    public String getNombreTipoRespuesta() {
        return nombreTipoRespuesta;
    }

    public void setNombreTipoRespuesta(String nombreTipoRespuesta) {
        this.nombreTipoRespuesta = nombreTipoRespuesta;
    }
}