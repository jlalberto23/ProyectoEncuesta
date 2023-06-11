package com.example.proyectoencuesta;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class preguntaAudio extends Activity {
    String nombre;
    TextView nomEncue;
    TextView pregunta;
    Button siguiente, guardar, play;
    conexionDB helper;
    int idEncuesta, idPregunta;
    byte [] audio;
    private String textoPregunta;
    int numP, cantidadPreguntas;
    boolean primerIteracion = true;
    EditText respuesta;
    Cursor preguntasCursor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new conexionDB(this);
        helper.abrir();

        setContentView(R.layout.pregunta_audio);
        helper.cerrar();
        play = findViewById(R.id.reprobtn);
        play.setOnClickListener(onclick);

        nomEncue = findViewById(R.id.encTitle);
        pregunta = findViewById(R.id.preglbl);
        siguiente = findViewById(R.id.nextbtn);
        guardar = findViewById(R.id.guardbtn);
        guardar.setVisibility(View.INVISIBLE);
        siguiente.setOnClickListener(onclick);
        guardar.setOnClickListener(onclick);
        respuesta = findViewById(R.id.resptxt);


        try {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                if (extra.containsKey("nombreEncuesta")) {
                    nombre = extra.getString("nombreEncuesta");
                    nomEncue.setText(nombre);
                }
                if (extra.containsKey("primerIteracion")) {
                    primerIteracion = extra.getBoolean("primerIteracion");
                }

                if (extra.containsKey("primerIteracion")) {
                    numP = extra.getInt("numP");
                }

                if (extra.containsKey("idEncuesta")) {
                    idEncuesta = extra.getInt("idEncuesta");

                    helper.abrir();

                    if (primerIteracion) {
                        numP = 0;
                        List<pregunta> lista = obtenerPreguntas(idEncuesta);
                        pregunta preg = lista.get(0);
                        idPregunta = preg.getIdPregunta();
                        audio = preg.getArchivoMultimedia();
                        System.out.println(audio);
                        cantidadPreguntas = lista.size();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("PRIMER ITERACION");
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP + 1) + " - " + preg.getTextoPregunta());

                    } else {
                        List<pregunta> lista = obtenerPreguntas(idEncuesta);
                        pregunta preg = lista.get(numP);
                        idPregunta = preg.getIdPregunta();
                        audio = preg.getArchivoMultimedia();
                        cantidadPreguntas = lista.size();
                        System.out.println("Cantidad de preguntas " + cantidadPreguntas);
                        System.out.println("Numero de Pregunta " + numP);

                        pregunta.setText((numP + 1) + " - " + preg.getTextoPregunta());

                    }

                    if (numP == (cantidadPreguntas - 1)) {
                        guardar.setVisibility(View.VISIBLE);
                        siguiente.setVisibility(View.INVISIBLE);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Fetching the stored data from the SharedPreference
            SharedPreferences sh = getSharedPreferences("SharedPreferenceUsuario", MODE_PRIVATE);
            String id_usuario = sh.getString("id_usuario", "");

            try {
                switch (v.getId()) {
                    case R.id.nextbtn:
                        String res;
                        respuestaUsuario resp = new respuestaUsuario();
                        resp.setTextoRespuesta(respuesta.getText().toString());
                        resp.setIdEncuesta(idEncuesta);
                        resp.setIdPregunta(idPregunta); // Utilizamos el mismo ID de pregunta
                        resp.setIdUsuario(Integer.valueOf(id_usuario));
                        resp.setNumeroIntento(1);
                        resp.setFechaRespondido(helper.getDatePhone());

                        helper.abrir();
                        res = helper.insertar(resp);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();

                        numP++;

                        Intent i = new Intent(v.getContext(), preguntaAudio.class);
                        i.putExtra("nombreEncuesta", nomEncue.getText().toString());
                        i.putExtra("idEncuesta", idEncuesta);
                        i.putExtra("primerIteracion", false);
                        i.putExtra("numP", numP);
                        //i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i, 1234);
                        break;

                    case R.id.guardbtn:
                        String res2;
                        respuestaUsuario resp2 = new respuestaUsuario();
                        resp2.setTextoRespuesta(respuesta.getText().toString());
                        resp2.setIdEncuesta(idEncuesta);
                        resp2.setIdPregunta(idPregunta); // Utilizamos el mismo ID de pregunta
                        resp2.setIdUsuario(Integer.valueOf(id_usuario));
                        resp2.setNumeroIntento(1);
                        resp2.setFechaRespondido(helper.getDatePhone());

                        helper.abrir();
                        res = helper.insertar(resp2);
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(v.getContext(), encuestaLista.class);
                        startActivity(in);
                        break;

                    case R.id.reprobtn:

                        if (audio != null) {
                            // Obtener el archivo multimedia de la pregunta actual
                            byte[] archivoMultimedia =audio;

                            // Crear un archivo temporal para almacenar el blob de audio
                            File tempFile;
                            try {

                                tempFile = File.createTempFile("temp_audio", ".mp3", getCacheDir());
                                FileOutputStream fos = new FileOutputStream(tempFile);
                                fos.write(archivoMultimedia);
                                fos.close();
                                play.setBackgroundResource(R.drawable.play_negro);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;

                            }

                            // Reproducir el archivo de audio
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(tempFile.getPath());
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                play.setBackgroundResource(R.drawable.play_rojo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // Eliminar el archivo temporal después de reproducirlo
                            tempFile.delete();

                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private List<pregunta> obtenerPreguntas(int idEncuesta) {

        List<pregunta> lis = new ArrayList<>();

        Cursor cursor = helper.obtenerPreguntas(idEncuesta);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    pregunta pre = new pregunta();
                    pre.setIdPregunta(cursor.getInt(0));
                    pre.setIdEncuesta(cursor.getInt(1));
                    pre.setIdTpoPregunta(cursor.getInt(2));
                    pre.setTextoPregunta(cursor.getString(3));
                    pre.setOrdenPregunta(cursor.getInt(5));
                    pre.setArchivoMultimedia(cursor.getBlob(6));
                    lis.add(pre);
                } while (cursor.moveToNext());
            }
        }

        return lis;
    }
}
