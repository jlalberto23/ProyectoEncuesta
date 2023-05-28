package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class materiaConsultar extends Activity{

    conexion helper;
    EditText nombreMateria;
    EditText codigoMateria;
    EditText ciclo;
    EditText anio;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_materia);
        // helper = new conexion(this);


        nombreMateria = (EditText) findViewById(R.id.nomMateriatxt);
        codigoMateria = (EditText) findViewById(R.id.codMateriatxt);
        ciclo = (EditText) findViewById(R.id.ciclotxt);
        anio = (EditText) findViewById(R.id.aniotxt);

    }

    /*public void consultarMateria(View v) {
        helper.abrir();
        materia mate = helper.consultarMateria(codigoMateria.getText().toString());
        helper.cerrar();

        if(mate  == null)
            Toast.makeText(this, "Codigo de materia " + codigoMateria.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            nombreMateria.setText(mate.getNombreMateria());
            ciclo.setText(mate.getCiclo());
            anio.setText(String.valueOf(mate.getAnio()));
        }
    }*/
    public void limpiarTexto(View v) {
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");

    }

}
