package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class eliminarEncuesta extends Activity {
    Button eliminar, limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_encuesta);
    }
}