package com.example.proyectoencuesta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class preguntasVyF extends Activity {

    String nombre,vf;
    TextView nomEncue;
    TextView pregunta;
    RadioGroup radioGroup;
    RadioButton verdadero, falso;
    Button siguiente, guardar, escuchar;
    TextToSpeech tts;
    conexionDB helper;
    int idEncuesta;
    private String textoPregunta;
    int numP, cantidadPreguntas;
    boolean primerIteracion = true;
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
        escuchar = findViewById(R.id.escucharbtn2);
        escuchar.setOnClickListener(onClick);
        tts = new TextToSpeech(this,OnInit);

        nomEncue = findViewById(R.id.nomCuestionario);
        pregunta = findViewById(R.id.preguntaVyF);
        verdadero = findViewById(R.id.rdVerdadero);
        falso = findViewById(R.id.rdFalso);
        siguiente = findViewById(R.id.cuestionarioSiguientebtn);
        guardar = findViewById(R.id.guardarPregunta);
        guardar.setVisibility(View.INVISIBLE);
        siguiente.setOnClickListener(onclick);
        guardar.setOnClickListener(onclick);


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
                if(extra.containsKey("primerIteracion")){
                    primerIteracion = extra.getBoolean("primerIteracion");
                }

                if(extra.containsKey("primerIteracion")){
                    numP = extra.getInt("numP");
                }

                if (extra.containsKey("idEncuesta")) {
                    idEncuesta = extra.getInt("idEncuesta");

                    helper.abrir();

                    if (primerIteracion) {

                        numP = 0;
                        List<pregunta> lista = obtenerPreguntas(idEncuesta);
                        pregunta preg = lista.get(0);
                        preg.getIdEncuesta();
                        cantidadPreguntas = lista.size();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("PRIMER ITERACION");
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP+1) + " - " + preg.getTextoPregunta());

                    } else{
                        List<pregunta> lista = obtenerPreguntas(idEncuesta);
                        pregunta preg = lista.get(numP);
                        cantidadPreguntas = lista.size();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP+1) + " - " +  preg.getTextoPregunta());

                    }

                    if (numP == (cantidadPreguntas-1)) {
                        guardar.setVisibility(View.VISIBLE);
                        siguiente.setVisibility(View.INVISIBLE);
                    }

                   /* helper.abrir();
                    System.out.println(idEncuesta);
                    pregunta preg = helper.consultarPreguntas(idEncuesta);
                    preg.getIdEncuesta();
                    System.out.println(preg.getIdEncuesta());
                    pregunta.setText(preg.getTextoPregunta());
                    System.out.println(preg.getIdEncuesta());
                    System.out.println(preg.getTextoPregunta());
                    helper.cerrar();

                    /*if (preg != null) {
                        pregunta.setText(preg.getTextoPregunta());
                        System.out.println(preg.getIdEncuesta());
                        System.out.println(preg.getTextoPregunta());
                    }*/

                    //************
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

    TextToSpeech.OnInitListener OnInit = new TextToSpeech.OnInitListener() {

        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "TTS no disponible",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener() {
        @SuppressLint("SdCardPath")
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId()==R.id.escucharbtn2){
                tts.speak(pregunta.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()) {
                    case R.id.cuestionarioSiguientebtn:

                        /*String res;
                        respuestaUsuario resp = new respuestaUsuario();
                        resp.setTextoRespuesta(vf);
                        helper.abrir();
                        res = helper.insertar(resp);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();*/
                        String res;
                        respuestaUsuario resp = new respuestaUsuario();
                        if (verdadero.isChecked())
                            vf = "Verdadero";
                        else if (falso.isChecked())
                            vf = "Falso";
                        resp.setTextoRespuesta(vf.getBytes().toString());
                        helper.abrir();
                        res = helper.insertar(resp);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();

                        numP++;


                        Intent i = new Intent(v.getContext(),preguntasVyF.class);
                        i.putExtra("nombreEncuesta", nomEncue.getText().toString());
                        i.putExtra("idEncuesta", idEncuesta);
                        i.putExtra("primerIteracion", false);
                        i.putExtra("numP", numP);
                        //i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i,1234);
                        break;

                    case R.id.guardarPregunta:

                        String res2;
                        respuestaUsuario resp2 = new respuestaUsuario();
                        if (verdadero.isChecked())
                            vf = "Verdadero";
                        else if (falso.isChecked())
                            vf = "Falso";
                        resp2.setTextoRespuesta(vf.getBytes().toString());
                        helper.abrir();
                        res = helper.insertar(resp2);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(v.getContext(),encuestaLista.class);
                        startActivity(in);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private List<pregunta> obtenerPreguntas(int idEncuesta){
        List<pregunta> lis = new ArrayList<>();

        Cursor cursor = helper.obtenerPreguntas(idEncuesta);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    pregunta pre = new pregunta();
                    pre.setIdPregunta(cursor.getInt(0));
                    pre.setIdEncuesta(cursor.getInt(1));
                    pre.setIdTpoPregunta(cursor.getInt(2));
                    pre.setTextoPregunta(cursor.getString(3));
                    //pre.setEsObligatoria(preguntas.(4));
                    pre.setOrdenPregunta(cursor.getInt(5));
                    lis.add(pre);
                }while (cursor.moveToNext());
            }
        }
        return lis;
    }
}
