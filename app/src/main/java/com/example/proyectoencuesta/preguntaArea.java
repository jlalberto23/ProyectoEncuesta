package com.example.proyectoencuesta;

public class preguntaArea {

    private int idPregunta, idAreaEvaluativa;

    public preguntaArea(int idPregunta, int idAreaEvaluativa) {
        this.idPregunta = idPregunta;
        this.idAreaEvaluativa = idAreaEvaluativa;
    }

    public preguntaArea() {
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdAreaEvaluativa() {
        return idAreaEvaluativa;
    }

    public void setIdAreaEvaluativa(int idAreaEvaluativa) {
        this.idAreaEvaluativa = idAreaEvaluativa;
    }
}
