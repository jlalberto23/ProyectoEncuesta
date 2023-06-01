package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class agregarPreguntaVyF extends Activity {

    int numP;
    TextView nombre;
    EditText pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_preguntas_vyf);
        nombre = findViewById(R.id.nomCuestionario);
        pregunta = findViewById(R.id.preguntaEdit);

        Bundle extra = getIntent().getExtras();
        numP = Integer.valueOf(extra.getString("numP"));
        String e = extra.getString("nomEn");
        nombre.setText(e);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                switch (view.getId()){
                    case R.id.btnNext:

                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
}
