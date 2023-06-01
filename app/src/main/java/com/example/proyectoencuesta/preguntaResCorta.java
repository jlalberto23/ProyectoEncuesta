package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class preguntaResCorta extends Activity {
    Button siguiente, guardar;
    TextView pregunta,nombre;
    RadioGroup lista;
    RadioButton v,f;
    int numP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas_res_corta);

        pregunta = findViewById(R.id.preguntaCorta);
        nombre = findViewById(R.id.nomCuestilbl);
        siguiente = findViewById(R.id.sigbtn);

        Bundle extra = getIntent().getExtras();
        numP = Integer.valueOf(extra.getString("numP"));
        String e = extra.getString("nomEn");
        nombre.setText(e);
        siguiente.setOnClickListener(onclic);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sigbtn:

                    break;
            }
        }
    };
}