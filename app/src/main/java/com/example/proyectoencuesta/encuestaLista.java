package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class encuestaLista extends Activity {
    Button responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta_lista);
    }
}