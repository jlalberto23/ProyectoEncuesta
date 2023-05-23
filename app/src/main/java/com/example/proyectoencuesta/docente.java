package com.example.proyectoencuesta;

public class docente {

    private int codigoDocente, codigoUsuario;
    private String nombreDocente;

    public docente(int codigoDocente, int codigoUsuario, String nombreDocente) {
        this.codigoDocente = codigoDocente;
        this.codigoUsuario = codigoUsuario;
        this.nombreDocente = nombreDocente;
    }

    public docente() {
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }
}
