package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class areaEvaluacionInsertar extends Activity {
    conexion helper;
    EditText idAreaEvaluativa;
    EditText idEncuesta;
    EditText ordenNumerico;
    EditText nombreArea;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);

    }

}
