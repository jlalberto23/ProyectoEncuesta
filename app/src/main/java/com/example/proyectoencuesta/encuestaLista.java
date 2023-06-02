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


public class encuestaLista extends Activity {
    Button responder,cancelar;
    Spinner spinnerEncuesta;
    conexionDB helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta_lista);
        helper = new conexionDB(this);
        spinnerEncuesta = findViewById(R.id.spinnerEncuestas);
        //responder = findViewById(R.id.responderbtn);
        //cancelar = findViewById(R.id.cancelarbtn);
        helper.abrir();
        List<encuesta> lista = llenarSp();
        ArrayAdapter<encuesta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        spinnerEncuesta.setAdapter(adapter);
        helper.cerrar();
        //responder.setOnClickListener(onclic);
        //cancelar.setOnClickListener(onclic);
    }
    private List<encuesta> llenarSp(){
        List<encuesta> lis = new ArrayList<>();

        Cursor cursor = helper.mostrarEncuestasSP();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    encuesta en = new encuesta();
                    en.setIdEncuesta(cursor.getInt(0));
                    en.setNombreEncuesta(cursor.getString(1));
                    lis.add(en);
                }while (cursor.moveToNext());
            }
        }
        return lis;
    }

}

