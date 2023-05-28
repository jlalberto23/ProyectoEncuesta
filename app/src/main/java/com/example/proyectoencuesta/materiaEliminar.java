package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class materiaEliminar extends Activity {
    conexion helper;
    EditText codigoMateria;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_materia);
        // helper = new conexion(this);

        codigoMateria = (EditText) findViewById(R.id.codMateriatxt);
    }

    public void eliminarMateria(View v){
        String regEliminadas;
        materia mate=new materia();
        mate.setCodigoMateria(codigoMateria.getText().toString());
        //helper.abrir();
        //regEliminadas=helper.eliminar(user);
        //helper.cerrar();
        //Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        codigoMateria.setText("");

    }

}
