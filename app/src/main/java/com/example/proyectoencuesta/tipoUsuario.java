package com.example.proyectoencuesta;

public class tipoUsuario {

    private int codigoTipoUsuario;
    private String nombreTipoUsuario;

    public tipoUsuario() {
    }

    public tipoUsuario(int codigoTipoUsuario, String nombreTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public int getCodigoTipoUsuario() {
        return codigoTipoUsuario;
    }

    public void setCodigoTipoUsuario(int codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }
}
