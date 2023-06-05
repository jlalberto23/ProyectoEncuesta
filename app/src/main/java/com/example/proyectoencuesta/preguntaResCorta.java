package com.example.proyectoencuesta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class preguntaResCorta extends Activity {
    String nombre;
    Button siguiente, guardar, escucharbtn, resAudiobtn, resSpeechbtn, resImagenbtn;
    TextToSpeech tts;
    TextView pregunta, nomEncue;
    EditText respuesta;
    conexionDB helper;
    int numP;
    String nom,preg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new conexionDB(this);
        helper.llenarDatos();
        setContentView(R.layout.preguntas_res_corta);
        helper.cerrar();

        escucharbtn = findViewById(R.id.escucharbtn);
        resAudiobtn = findViewById(R.id.resAudiobtn);
        resSpeechbtn = findViewById(R.id.resSpeechbtn);
        resImagenbtn = findViewById(R.id.resImagenbtn);
        escucharbtn.setOnClickListener(onClick);
        resAudiobtn.setOnClickListener(onclick);
        resSpeechbtn.setOnClickListener(onclick);
        resImagenbtn.setOnClickListener(onclick);

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
            /*if (numP == 1) {
                guardar.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }*/
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
                if (extra.containsKey("idEncuesta")) {
                    int idEncuesta = extra.getInt("idEncuesta");

                    helper.abrir();
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

        //*************************************************


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
            if (v.getId()==R.id.escucharbtn){
                tts.speak(pregunta.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        }
    };

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()) {
                    case R.id.sigbtn:

                        String res;
                        respuestaUsuario resp = new respuestaUsuario();
                        resp.setTextoRespuesta(respuesta.getText().toString());
                        helper.abrir();
                        res = helper.insertar(resp);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
