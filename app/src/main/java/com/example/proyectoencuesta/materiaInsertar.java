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


        String regInsertados;
        materia mate=new materia();
        mate.setNombreMateria(nomMateria);
        mate.setCodigoMateria(codigo);
        mate.setCiclo(ciclo1);
        mate.setAnio(ano);

        //     helper.abrir();
        //   regInsertados=helper.insertar(mate);
        // helper.cerrar();
        //Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");

    }

}
