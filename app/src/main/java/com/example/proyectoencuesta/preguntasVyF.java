package com.example.proyectoencuesta;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class preguntasVyF extends Activity {
    String nombre;
    TextView nomEncue;
    TextView pregunta;
    RadioGroup radioGroup;
    RadioButton verdadero, falso;
    Button siguiente, guardar;
    conexionDB helper;
    private int idEncuesta;
    private String textoPregunta;
    private SQLiteDatabase getReadableDatabase() {
        return helper.getReadableDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new conexionDB(this);
        helper.abrir();

        setContentView(R.layout.preguntas_vyf);
        helper.cerrar();

        nomEncue = findViewById(R.id.nomCuestionario);
        pregunta = findViewById(R.id.preguntaVyF);
        verdadero = findViewById(R.id.rdVerdadero);
        falso = findViewById(R.id.rdFalso);
        siguiente = findViewById(R.id.cuestionarioSiguientebtn);
        guardar = findViewById(R.id.guardarPregunta);
        guardar.setVisibility(View.INVISIBLE);
        //siguiente.setOnClickListener(onclick);
        //guardar.setOnClickListener(onclick);
        try{
            Bundle extra = getIntent().getExtras();
            if(extra.getString("nombreEncuesta")!=null)
            {
                nombre = extra.getString("nombreEncuesta");
                nomEncue.setText(nombre);
                //next = findViewById(R.id.sigbtn);
            }
            //else
            /*if (numP == 1) {
                guardar.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                if (extra.containsKey("nombreEncuesta")) {
                    String nombreEncuesta = extra.getString("nombreEncuesta");
                    nomEncue.setText(nombreEncuesta);
                }
                if (extra.containsKey("idEncuesta")) {
                    int idEncuesta = extra.getInt("idEncuesta");
                    //System.out.println(idEncuesta);
                    helper.abrir();
                    pregunta preg = helper.consultarPreguntas(idEncuesta);

                    //System.out.println(idEncuesta);
                    pregunta.setText(preg.getTextoPregunta());
                    System.out.println(preg.getIdEncuesta());
                    System.out.println(preg.getTextoPregunta());
                    helper.cerrar();

                    /*if (preg != null) {
                        pregunta.setText(preg.getTextoPregunta());
                        System.out.println(preg.getIdEncuesta());
                        System.out.println(preg.getTextoPregunta());
                    }*/
                }
            }


            //else
            /*if (numP == 1) {
                guardar.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }





    }


}
