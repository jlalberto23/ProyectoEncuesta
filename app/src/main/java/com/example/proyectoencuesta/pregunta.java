package com.example.proyectoencuesta;

public class pregunta {

    private int idPregunta, idEncuesta, idTpoPregunta, ordenPregunta;
    private String textoPregunta;
    private boolean esObligatoria;

    public pregunta(int idPregunta, int idEncuesta, int idTpoPregunta, int ordenPregunta, String textoPregunta, boolean esObligatoria) {
        this.idPregunta = idPregunta;
        this.idEncuesta = idEncuesta;
        this.idTpoPregunta = idTpoPregunta;
        this.ordenPregunta = ordenPregunta;
        this.textoPregunta = textoPregunta;
        this.esObligatoria = esObligatoria;
    }

    public pregunta() {

    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdTpoPregunta() {
        return idTpoPregunta;
    }

    public void setIdTpoPregunta(int idTpoPregunta) {
        this.idTpoPregunta = idTpoPregunta;
    }

    public int getOrdenPregunta() {
        return ordenPregunta;
    }

    public void setOrdenPregunta(int ordenPregunta) {
        this.ordenPregunta = ordenPregunta;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public boolean isEsObligatoria() {
        return esObligatoria;
    }

    public void setEsObligatoria(boolean esObligatoria) {
        this.esObligatoria = esObligatoria;
    }
}