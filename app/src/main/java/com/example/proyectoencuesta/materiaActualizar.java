package com.example.proyectoencuesta;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class materiaActualizar extends Activity{

    conexionDB helper;
    EditText nombreMateria;
    EditText codigoMateria;
    EditText ciclo;
    EditText anio;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_materia);
         helper = new conexionDB(this);


        nombreMateria = (EditText) findViewById(R.id.nomMateriatxt);
        codigoMateria = (EditText) findViewById(R.id.codMateriatxt);
        ciclo = (EditText) findViewById(R.id.ciclotxt);
        anio = (EditText) findViewById(R.id.aniotxt);

    }

    public void insertarMateria(View v) {
        String nomMateria=nombreMateria.getText().toString();
        String codigo=codigoMateria.getText().toString();
        String ciclo1=ciclo.getText().toString();
        String ano=anio.getText().toString();

        //helper.abrir();
        //String estado = helper.actualizar(materia);
        //helper.cerrar();
        //Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");

    }


}
