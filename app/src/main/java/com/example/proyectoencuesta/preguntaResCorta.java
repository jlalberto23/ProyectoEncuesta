package com.example.proyectoencuesta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.content.SharedPreferences;

public class preguntaResCorta extends Activity {
    protected static final  int RESULT_SPEECH = 1;
    static final int check=1111;
    String nombre;
    Button siguiente, guardar, escucharbtn, resAudiobtn, resSpeechbtn, resImagenbtn;
    TextToSpeech tts;
    TextView pregunta, nomEncue;
    EditText respuesta;
    conexionDB helper;
    int numP, cantidadPreguntas;
    boolean primerIteracion = true;
    int idEncuesta, idPregunta;
    String nom,preg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new conexionDB(this);
        helper.abrir();
        setContentView(R.layout.preguntas_res_corta);
        helper.cerrar();

        escucharbtn = findViewById(R.id.escucharbtn);

        resSpeechbtn = findViewById(R.id.resSpeechbtn);

        escucharbtn.setOnClickListener(onClick);

        resSpeechbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es-ESP");
                try{
                    startActivityForResult(intent, RESULT_SPEECH);
                    respuesta.setText("");
                } catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Tu dispositivo no soporta speech to text",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        tts = new TextToSpeech(this,OnInit);

        guardar = findViewById(R.id.guardarPreguntas);
        siguiente = findViewById(R.id.sigbtn);
        guardar.setVisibility(View.INVISIBLE);
        pregunta = findViewById(R.id.preguntaCorta);
        nomEncue = findViewById(R.id.nomCuestilbl);
        respuesta = findViewById(R.id.resCortatxt);
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
            else if( extra.getString("nombreEncuesta")!=null){
                nombre = extra.getString("nombreEncuesta");
                nomEncue.setText(nombre);
                //next = findViewById(R.id.sigbtn);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //**************************************************
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
                        idPregunta = preg.getIdPregunta();
                        cantidadPreguntas = lista.size();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("PRIMER ITERACION");
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP+1) + " - " + preg.getTextoPregunta());

                    } else{
                        List<pregunta> lista = obtenerPreguntas(idEncuesta);
                        pregunta preg = lista.get(numP);
                        cantidadPreguntas = lista.size();
                        idPregunta = preg.getIdPregunta();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP+1) + " - " +  preg.getTextoPregunta());

                    }

                    if (numP == (cantidadPreguntas-1)) {
                        guardar.setVisibility(View.VISIBLE);
                        siguiente.setVisibility(View.INVISIBLE);
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //*************************************************


    }
    //Implementacion de voz a texto

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode==RESULT_OK && data != null){
                    ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    respuesta.setText(text.get(0));
                }
                break;
        }
    }

    //Implementacion de librerias de texto a voz
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
            if (v.getId()==R.id.escucharbtn){
                tts.speak(pregunta.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Fetching the stored data from the SharedPreference
            SharedPreferences sh = getSharedPreferences("SharedPreferenceUsuario", MODE_PRIVATE);
            String id_usuario = sh.getString("id_usuario", "");

            try{
                switch (v.getId()) {
                    case R.id.sigbtn:

                        String res;
                        respuestaUsuario resp = new respuestaUsuario();
                        resp.setTextoRespuesta(respuesta.getText().toString());
                        resp.setIdEncuesta(idEncuesta);
                        resp.setIdPregunta(idPregunta);
                        resp.setIdUsuario(Integer.valueOf(id_usuario));
                        resp.setNumeroIntento(1);
                        resp.setFechaRespondido(helper.getDatePhone());

                        helper.abrir();
                        res = helper.insertar(resp);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();

                        numP++;


                        Intent i = new Intent(v.getContext(),preguntaResCorta.class);
                        i.putExtra("nombreEncuesta", nomEncue.getText().toString());
                        i.putExtra("idEncuesta", idEncuesta);
                        i.putExtra("primerIteracion", false);
                        i.putExtra("numP", numP);
                        //i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i,1234);
                        break;

                    case R.id.guardarPreguntas:

                        String res2;
                        respuestaUsuario resp2 = new respuestaUsuario();
                        resp2.setTextoRespuesta(respuesta.getText().toString());
                        resp2.setIdEncuesta(idEncuesta);
                        resp2.setIdPregunta(idPregunta);
                        resp2.setIdUsuario(Integer.valueOf(id_usuario));
                        resp2.setNumeroIntento(1);
                        resp2.setFechaRespondido(helper.getDatePhone());

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
