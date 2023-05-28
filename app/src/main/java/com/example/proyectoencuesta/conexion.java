package com.example.proyectoencuesta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;


public class conexion {

    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public conexion(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "encuesta.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null , VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("create table tipo_usuario (id_tipo_usuario integer primary key autoincrement,nombre_tipo_usuario char(256) not null);");
                db.execSQL("create table usuario (id_usuario integer primary key AUTOINCREMENT,id_tipo_usuario integer not null,nombre_usuario char(256),username char(256) not null,password char(256) not null, fecha_registro date, foreign key (id_tipo_usuario) references tipo_usuario (id_tipo_usuario));");
                db.execSQL("create table tipo_encuesta ( id_tipo_encuesta integer primary key AUTOINCREMENT, nombre_tipo_encuesta char(100) not null);");
                db.execSQL("create table encuesta ( id_encuesta integer not null primary key AUTOINCREMENT, id_usuario integer not null, id_tipo_encuesta integer not null, nombre_encuesta char(256), fecha_creacion date, id_estado_encuesta integer, numero_preguntas integer, limite_intentos integer, fecha_inicio date, fecha_fin date, foreign key (id_usuario) references usuario (id_usuario), foreign key (id_tipo_encuesta) references tipo_encuesta (id_tipo_encuesta));");

            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }


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

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }

    public void llenarBDCarnet(){
        try {cerrar();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}