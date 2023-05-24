package com.example.proyectoencuesta;

public class estudiante {

    private int idEstudiante, idUsuario;
    private String nombreEstudiante, carnet;

    public estudiante(int idEstudiante, int idUsuario, String nombreEstudiante, String carnet) {
        this.idEstudiante = idEstudiante;
        this.idUsuario = idUsuario;
        this.nombreEstudiante = nombreEstudiante;
        this.carnet = carnet;
    }

    public estudiante() {
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}
