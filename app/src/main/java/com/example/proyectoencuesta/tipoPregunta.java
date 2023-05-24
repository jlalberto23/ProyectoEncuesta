package com.example.proyectoencuesta;

public class tipoPregunta {

    private int idTipoPregunta;
    private String nombreTipoPregunta;

    public tipoPregunta(int idTipoPregunta, String nombreTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
        this.nombreTipoPregunta = nombreTipoPregunta;
    }

    public tipoPregunta() {
    }

    public int getIdTipoPregunta() {
        return idTipoPregunta;
    }

    public void setIdTipoPregunta(int idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }

    public String getNombreTipoPregunta() {
        return nombreTipoPregunta;
    }

    public void setNombreTipoPregunta(String nombreTipoPregunta) {
        this.nombreTipoPregunta = nombreTipoPregunta;
    }
}
