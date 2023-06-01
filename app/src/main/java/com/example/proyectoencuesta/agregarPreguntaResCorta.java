package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class agregarPreguntaResCorta  extends Activity {

    conexionDB cn;
    int numP;
    String nom;
    TextView nombre;
    EditText pregunta;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_pregunta_res_corta);
        cn = new conexionDB(this);
        Bundle extra = getIntent().getExtras();
        numP = Integer.valueOf(extra.getString("numP"));
        nom = extra.getString("nomEn");
        nombre = findViewById(R.id.nomCuestilbl);
        pregunta = findViewById(R.id.resCortatxt);
        nombre.setText(nom);
    }
}
