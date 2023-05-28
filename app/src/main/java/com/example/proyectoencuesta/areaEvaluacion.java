package com.example.proyectoencuesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class  areaEvaluacion {
    private int idAreaEvaluativa, idEncuesta, ordenNumerico;
    private String nombreArea;

   /* public areaEvaluacion(int idAreaEvaluativa, int idEncuesta, int ordenNumerico, String nombreArea) {
        this.idAreaEvaluativa = idAreaEvaluativa;
        this.idEncuesta = idEncuesta;
        this.ordenNumerico = ordenNumerico;
        this.nombreArea = nombreArea;
    }
    */
   public areaEvaluacion() {
   }

    public int getIdAreaEvaluativa() {
        return idAreaEvaluativa;
    }

    public void setIdAreaEvaluativa(int idAreaEvaluativa) {
        this.idAreaEvaluativa = idAreaEvaluativa;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getOrdenNumerico() {
        return ordenNumerico;
    }

    public void setOrdenNumerico(int ordenNumerico) {
        this.ordenNumerico = ordenNumerico;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SurveyUesDB";

    private static final String area_evaluativa = "areaEvaluacion";
    private static final String id_area_evaluativa = "idAreaEvaluativa";
    private static final String id_encuesta = "idEncuesta";
    private static final String orden_numerico = "ordenNumerico";
    private static final String nombre_area = "nombreArea";

    public  areaEvaluacion(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //@Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AREA_EVALUACION_TABLE = "CREATE TABLE " + area_evaluativa +
                "(" +
                id_area_evaluativa + " INTEGER PRIMARY KEY," +
                id_encuesta + " INTEGER," +
                orden_numerico + " INTEGER," +
                nombre_area + " TEXT" +
                ")";
        db.execSQL(CREATE_AREA_EVALUACION_TABLE);
    }

    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + area_evaluativa);
        onCreate(db);
    }

    // Método para agregar una nueva área evaluativa
    public void agregarAreaEvaluacion(areaEvaluacion area) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id_area_evaluativa, area.getIdAreaEvaluativa());
        values.put(id_encuesta, area.getIdEncuesta());
        values.put(orden_numerico, area.getOrdenNumerico());
        values.put(nombre_area, area.getNombreArea());

        db.insert(area_evaluativa, null, values);
        db.close();
    }

    private SQLiteDatabase getWritableDatabase() {

        return null;
    }

    // Método para obtener todas las áreas evaluativas
    public List<areaEvaluacion> obtenerTodasLasAreasEvaluativas() {
        List<areaEvaluacion> areas = new ArrayList<>();

        String query = "SELECT * FROM " + area_evaluativa;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                areaEvaluacion area = new areaEvaluacion();
                area.setIdAreaEvaluativa(cursor.getInt(0));
                area.setIdEncuesta(cursor.getInt(1));
                area.setOrdenNumerico(cursor.getInt(2));
                area.setNombreArea(cursor.getString(3));

                areas.add(area);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return areas;
    }

    // Método para actualizar una área evaluativa existente
    public void actualizarAreaEvaluacion(areaEvaluacion area) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id_encuesta, area.getIdEncuesta());
        values.put(orden_numerico, area.getOrdenNumerico());
        values.put(nombre_area, area.getNombreArea());


        db.update(area_evaluativa, values, id_area_evaluativa + " = ?",
                new String[]{String.valueOf(area.getIdAreaEvaluativa())});
        db.close();
    }

    // Método para eliminar una área evaluativa
    public void eliminarAreaEvaluacion(int idAreaEvaluativa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(area_evaluativa, id_area_evaluativa + " = ?",
                new String[]{String.valueOf(idAreaEvaluativa)});
        db.close();
    }
}
