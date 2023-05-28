package com.example.proyectoencuesta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class conexion {

    private Context context;
    private SQLiteDatabase db;


   /* public usuario consultarUsuario(String carnet) {


        String[] id = {carnet};
        Cursor cursor = db.query("usuario", camposUsuario, "carnet = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            usuario user = new usuario();
            user.setCarnet(cursor.getString(0));
            user.setNombreUsuario(cursor.getString(1));
            user.setUsuario(cursor.getString(2));
            user.setContrasenia(cursor.getString(3));
            user.setFecha_registro(cursor.getString(4));

            return user;
        }else{
            return null;
        }


    public materia consultarMateria(String codigoMateria) {
        String[] id = {codigoMateria};
        Cursor cursor = db.query("alumno", camposMateria, "codito_materia = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            materia mate = new materia();
            mate.setCodigoMateria(cursor.getString(0));
            mate.setNombreMateria(cursor.getString(1));
            mate.setCiclo(cursor.getString(2));
            mate.setAnio(cursor.getString(3));

            return mate;
        }else{
            return null;
        }
    }

    */


}