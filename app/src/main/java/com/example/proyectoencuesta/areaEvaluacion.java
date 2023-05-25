package com.example.proyectoencuesta;

public class areaEvaluacion {

    private int idAreaEvaluativa, ordenNumerico;

    private String nombreArea;

    public areaEvaluacion(int idAreaEvaluativa,int ordenNumerico, String nombreArea){

        this.idAreaEvaluativa = idAreaEvaluativa;
        this.ordenNumerico = ordenNumerico;
        this.nombreArea = nombreArea;

    }

    public int getIdAreaEvaluativa() {
        return idAreaEvaluativa;
    }

    public int getOrdenNumerico() {
        return ordenNumerico;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setIdAreaEvaluativa(int idAreaEvaluativa) {
        this.idAreaEvaluativa = idAreaEvaluativa;
    }

    public void setOrdenNumerico(int ordenNumerico) {
        this.ordenNumerico = ordenNumerico;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}
