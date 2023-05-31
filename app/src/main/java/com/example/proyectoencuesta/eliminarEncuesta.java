package com.example.proyectoencuesta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class eliminarEncuesta extends Activity {
    Button eliminar, limpiar;
    Spinner spinnerElimEncuesta;
    conexionDB helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_encuesta);
        helper = new conexionDB(this);
        spinnerElimEncuesta = findViewById(R.id.spinnerElimEncuesta);
        List<encuesta> lista = llenarSp();
        ArrayAdapter<encuesta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        spinnerElimEncuesta.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private List<encuesta> llenarSp(){
        List<encuesta> lis = new ArrayList<>();
        helper.abrir();
        Cursor cursor = helper.mostrarEncuestasSP();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    encuesta en = new encuesta();
                    en.setIdEncuesta(cursor.getInt(cursor.getColumnIndex("id_encuesta")));
                    en.setNombreEncuesta(cursor.getString(cursor.getColumnIndex("nombre_encuesta")));
                    lis.add(en);
                }while (cursor.moveToNext());
            }
        }
        helper.cerrar();
        return lis;
    }
    public void eliminarUsuario(View v){
        String regEliminadas;
        encuesta enc=new encuesta();
        String selectedItem = spinnerElimEncuesta.getSelectedItem().toString();
        enc.setNombreEncuesta(selectedItem);

        helper.abrir();
        regEliminadas=helper.eliminar(enc);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        //spinnerElimEncuesta.setAdapter("");

    }
}