package com.example.proyectoencuesta;

public class opcionRespuesta {

    private int idOpcionRespuesta, idTipoRespuesta;
    private boolean esCorrecta;
    private String textoRespuesta;

    public opcionRespuesta(int idOpcionRespuesta, int idTipoRespuesta, boolean esCorrecta, String textoRespuesta) {
        this.idOpcionRespuesta = idOpcionRespuesta;
        this.idTipoRespuesta = idTipoRespuesta;
        this.esCorrecta = esCorrecta;
        this.textoRespuesta = textoRespuesta;
    }

    public opcionRespuesta() {
    }

    public int getIdOpcionRespuesta() {
        return idOpcionRespuesta;
    }

    public void setIdOpcionRespuesta(int idOpcionRespuesta) {
        this.idOpcionRespuesta = idOpcionRespuesta;
    }

    public int getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    public void setIdTipoRespuesta(int idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }
}