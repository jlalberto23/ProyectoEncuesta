package com.example.proyectoencuesta;

import android.widget.Spinner;

public class usuario {

    private int codigUsuario, codigoTipoUsuario;
    private String nombreUsuario,contrasenia, usuario, fecha_registro, carnet;

    public usuario(int codigUsuario, int codigoTipoUsuario, String nombreUsuario, String contrasenia, String usuario, String fecha_registro, String carnet) {
        this.codigUsuario = codigUsuario;
        this.codigoTipoUsuario = codigoTipoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.usuario = usuario;
        this.fecha_registro = fecha_registro;
        this.carnet = carnet;
    }

    public usuario() {
    }

    public int getCodigUsuario() {
        return codigUsuario;
    }

    public void setCodigUsuario(int codigUsuario) {
        this.codigUsuario = codigUsuario;
    }

    public int getCodigoTipoUsuario() {
        return codigoTipoUsuario;
    }

    public void setCodigoTipoUsuario(int codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;

    }
    public String toString() {
        return nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}