package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class materiaInsertar extends Activity{

    conexionDB helper;
    EditText nombreMateria;
    EditText codigoMateria;
    EditText ciclo;
    EditText anio;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_materia);
        helper = new conexionDB(this);
        nombreMateria = findViewById(R.id.nomMateriatxt);
        codigoMateria = findViewById(R.id.codMateriatxt);
        ciclo = findViewById(R.id.ciclotxt);
        anio = findViewById(R.id.aniotxt);

    }

}
