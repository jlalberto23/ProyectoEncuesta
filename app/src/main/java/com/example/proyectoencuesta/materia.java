package com.example.proyectoencuesta;

public class materia {

    private int idMateria;
    private String nombreMateria, codigoMateria, ciclo, anio;

    public materia() {
    }

    public materia(int idMateria, String nombreMateria, String codigoMateria, String ciclo, String anio) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.codigoMateria = codigoMateria;
        this.ciclo = ciclo;
        this.anio = anio;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

}
