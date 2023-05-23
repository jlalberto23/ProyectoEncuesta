package com.example.proyectoencuesta;

public class usuario {

    private int codigUsuario, codigoTipoUsuario;
    private int nombreTipoUsuario, nombreUsuario,contrasenia, usuario, fecha_registro;

    public usuario(int codigUsuario, int codigoTipoUsuario, int nombreUsuario, int contrasenia, int usuario, int fecha_registro) {
        this.codigUsuario = codigUsuario;
        this.codigoTipoUsuario = codigoTipoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.usuario = usuario;
        this.fecha_registro = fecha_registro;
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

    public int getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(int nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public int getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(int nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(int contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(int fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
