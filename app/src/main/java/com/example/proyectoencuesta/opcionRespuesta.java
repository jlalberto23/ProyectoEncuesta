package com.example.proyectoencuesta;

public class opcionRespuesta {

    private int idOpcionRespuesta, idPregunta;
    private boolean esLaCorrecta;
    private String textoRespuesta;

    public opcionRespuesta() {
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public boolean isEsLaCorrecta() {
        return esLaCorrecta;
    }

    public void setEsLaCorrecta(boolean esLaCorrecta) {
        this.esLaCorrecta = esLaCorrecta;
    }

    public opcionRespuesta(int idOpcionRespuesta, int idPregunta, boolean esLaCorrecta, String textoRespuesta) {
        this.idOpcionRespuesta = idOpcionRespuesta;
        this.idPregunta = idPregunta;
        this.esLaCorrecta = esLaCorrecta;
        this.textoRespuesta = textoRespuesta;
    }



    public int getIdOpcionRespuesta() {
        return idOpcionRespuesta;
    }

    public void setIdOpcionRespuesta(int idOpcionRespuesta) {
        this.idOpcionRespuesta = idOpcionRespuesta;
    }

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }
}