package com.example.proyectoencuesta;

public class tipoEncuesta {

    private int idTipoEncuesta;
    private String nombreTipoEncuesta;

    public tipoEncuesta(int idTipoEncuesta, String nombreTipoEncuesta) {
        this.idTipoEncuesta = idTipoEncuesta;
        this.nombreTipoEncuesta = nombreTipoEncuesta;
    }

    public tipoEncuesta() {
    }

    public int getIdTipoEncuesta() {
        return idTipoEncuesta;
    }

    public void setIdTipoEncuesta(int idTipoEncuesta) {
        this.idTipoEncuesta = idTipoEncuesta;
    }

    public String getNombreTipoEncuesta() {
        return nombreTipoEncuesta;
    }

    public void setNombreTipoEncuesta(String nombreTipoEncuesta) {
        this.nombreTipoEncuesta = nombreTipoEncuesta;
    }
}
