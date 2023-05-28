package com.example.proyectoencuesta;

public class materiaUsuario {
    private int idMateriaUsuario, idMateria, idUsuario;

    public materiaUsuario(int idMateriaUsuario, int idMateria, int idUsuario) {
        this.idMateriaUsuario = idMateriaUsuario;
        this.idMateria = idMateria;
        this.idUsuario = idUsuario;
    }

    public materiaUsuario() {
    }

    public int getIdMateriaUsuario() {
        return idMateriaUsuario;
    }

    public void setIdMateriaUsuario(int idMateriaUsuario) {
        this.idMateriaUsuario = idMateriaUsuario;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
