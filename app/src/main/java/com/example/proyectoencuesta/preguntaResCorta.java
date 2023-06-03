package com.example.proyectoencuesta;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class preguntaResCorta extends Activity {
    String nombre;
    Button siguiente, guardar;
    TextView pregunta, nomEncue;
    EditText respuesta;
    conexionDB helper;
    int numP;
    String nom,preg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new conexionDB(this);
        helper.llenarDatos();
        setContentView(R.layout.preguntas_res_corta);
        helper.cerrar();

        guardar = findViewById(R.id.guardarPreguntas);
        siguiente = findViewById(R.id.sigbtn);
        guardar.setVisibility(View.INVISIBLE);
        pregunta = findViewById(R.id.preguntaCorta);
        nomEncue = findViewById(R.id.nomCuestilbl);
        respuesta = findViewById(R.id.resCortatxt);

       // numeroPreg = findViewById(R.id.lblNumeroPreg);
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
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (extra.containsKey("idEncuesta")) {
                int idEncuesta = extra.getInt("idEncuesta");
                // Utiliza el ID de la encuesta según tus necesidades
            }
        }

        //*************************************************


    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sigbtn:
                    // Lógica para el botón "SIGUIENTE"
                    break;
            }
        }
    };
}
