package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class preguntaResCorta extends Activity {
    Button siguiente, guardar;
    TextView pregunta;
    RadioGroup lista;
    RadioButton v,f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas_res_corta);

        pregunta = findViewById(R.id.txtPreguntaC);

        Bundle extra = getIntent().getExtras();
        String s = extra.getString("numP");
        pregunta.setText("Numero de preguntas: " + s);
    }
}