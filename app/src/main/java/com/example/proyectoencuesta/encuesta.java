package com.example.proyectoencuesta;

public class encuesta {

    private int idEncuesta, idUsuario, idTipoEncuesta, numeroPreguntas,limiteIntentos;
    private boolean estadoEncuesta;
    private String nombreEncuesta, fechaCreacion, fechaInicio, fechaFin;

    public encuesta(int idEncuesta, int idUsuario, int idTipoEncuesta, int numeroPreguntas, int limiteIntentos, boolean estadoEncuesta, String nombreEncuesta, String fechaCreacion, String fechaInicio, String fechaFin) {
        this.idEncuesta = idEncuesta;
        this.idUsuario = idUsuario;
        this.idTipoEncuesta = idTipoEncuesta;
        this.numeroPreguntas = numeroPreguntas;
        this.limiteIntentos = limiteIntentos;
        this.estadoEncuesta = estadoEncuesta;
        this.nombreEncuesta = nombreEncuesta;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public encuesta() {
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipoEncuesta() {
        return idTipoEncuesta;
    }

    public void setIdTipoEncuesta(int idTipoEncuesta) {
        this.idTipoEncuesta = idTipoEncuesta;
    }

    public int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(int numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }

    public int getLimiteIntentos() {
        return limiteIntentos;
    }

    public void setLimiteIntentos(int limiteIntentos) {
        this.limiteIntentos = limiteIntentos;
    }

    public boolean isEstadoEncuesta() {
        return estadoEncuesta;
    }

    public void setEstadoEncuesta(boolean estadoEncuesta) {
        this.estadoEncuesta = estadoEncuesta;
    }

    public String getNombreEncuesta() {
        return nombreEncuesta;
    }

    public void setNombreEncuesta(String nombreEncuesta) {
        this.nombreEncuesta = nombreEncuesta;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}