package com.example.proyectoencuesta;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class crearEncuesta extends Activity {
    Button guardar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_encuesta);
    }
}