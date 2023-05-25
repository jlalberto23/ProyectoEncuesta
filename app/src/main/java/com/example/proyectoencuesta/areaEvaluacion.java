package com.example.proyectoencuesta;

public class areaEvaluacion {

    private int idAreaEvaluativa, idEncuesta, ordenNumerico;
    private String nombreArea;

    public areaEvaluacion(int idAreaEvaluativa, int idEncuesta, int ordenNumerico, String nombreArea) {
        this.idAreaEvaluativa = idAreaEvaluativa;
        this.idEncuesta = idEncuesta;
        this.ordenNumerico = ordenNumerico;
        this.nombreArea = nombreArea;
    }

    public areaEvaluacion() {
    }

    public int getIdAreaEvaluativa() {
        return idAreaEvaluativa;
    }

    public void setIdAreaEvaluativa(int idAreaEvaluativa) {
        this.idAreaEvaluativa = idAreaEvaluativa;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getOrdenNumerico() {
        return ordenNumerico;
    }

    public void setOrdenNumerico(int ordenNumerico) {
        this.ordenNumerico = ordenNumerico;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}
