package com.example.proyectoencuesta;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class AgregarAudioPregunta extends Activity {
    MediaRecorder grabacion;
    String archivoSalida = null;
    Button grabarbtn, playbtn, siguiente, guardar;
    TextView nombre, numeroPreg;
    EditText pregunta;
    conexionDB cn;
    int numP;
    String nom;
    boolean grabando = false; // Variable para controlar el estado de grabación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_audio_preguntas);

        cn = new conexionDB(this);
        grabarbtn = findViewById(R.id.recbtn);
        playbtn = findViewById(R.id.playbtn);
        siguiente = findViewById(R.id.siguibtn);
        guardar = findViewById(R.id.guarbtn);
        guardar.setVisibility(INVISIBLE);
        pregunta = findViewById(R.id.Pregtxt);
        numeroPreg = findViewById(R.id.pregnum);
        grabarbtn.setOnClickListener(onClick);
        playbtn.setOnClickListener(onClick);
        numeroPreg.setText("Pregunta numero " + numP + ":");
        siguiente.setOnClickListener(onClick);
        guardar.setOnClickListener(onClick);
        nombre = findViewById(R.id.encnombre);

        try{
            Bundle extra = getIntent().getExtras();
            if(extra.getString("numP")!=null || extra.getString("nomEn")!=null)
            {
                numP = Integer.valueOf(extra.getString("numP"));
                nom = extra.getString("nomEn");
                nombre.setText(nom);
                siguiente = findViewById(R.id.siguibtn);
            }
            else if(extra.getString("cantP")!=null || extra.getString("nom")!=null){
                numP = Integer.valueOf(extra.getString("cantP"));
                nom = extra.getString("nom");
                nombre.setText(nom);
                siguiente = findViewById(R.id.siguibtn);
            }
            if (numP == 1) {
                guardar.setVisibility(VISIBLE);
                siguiente.setVisibility(INVISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        numeroPreg.setText("Pregunta numero " + numP + ":");
        siguiente.setOnClickListener(onClick);
        guardar.setOnClickListener(onClick);
    }

    public void Recorder() {
        if (!grabando) { // Verificar si no se está grabando actualmente
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            grabacion.setOutputFile(archivoSalida);

            try {
                grabacion.prepare();
                grabacion.start();
                grabando = true; // Actualizar el estado de grabación
                grabarbtn.setBackgroundResource(R.drawable.rec_rojo);
                Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            grabando = false; // Actualizar el estado de grabación
            grabarbtn.setBackgroundResource(R.drawable.rec_blanco);
            Toast.makeText(getApplicationContext(), "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducir() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                String res;
                pregunta p = new pregunta();
                switch (view.getId()){
                    case R.id.siguibtn:
                        cn.abrir();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(4);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_SHORT).show();
                        int c = numP-1;
                        Intent i = new Intent(view.getContext(),AgregarAudioPregunta.class);
                        i.putExtra("cantP", String.valueOf(c));
                        i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i,1234);
                        break;
                    case R.id.guarbtn:
                        cn.abrir();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(4);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_LONG).show();
                        Intent in = new Intent(view.getContext(),vistaDocente.class);
                        startActivity(in);
                        break;
                    case R.id.recbtn:
                        Recorder();
                        break;
                    case R.id.playbtn:
                        reproducir();
                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
