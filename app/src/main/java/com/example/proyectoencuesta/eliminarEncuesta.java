package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class eliminarEncuesta extends Activity {
    Button eliminar, limpiar;
    Spinner spinnerElimEncuesta;
    Spinner sp;
    conexionDB helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_encuesta);
        helper = new conexionDB(this);

        spinnerElimEncuesta = (Spinner) findViewById(R.id.spinnerElimEncuesta);
    }
    public void eliminarUsuario(View v){
        String regEliminadas;
        encuesta enc=new encuesta();
        String selectedItem = spinnerElimEncuesta.getSelectedItem().toString();
        enc.setNombreEncuesta(selectedItem);

        helper.abrir();
        regEliminadas=helper.eliminar(enc);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        //spinnerElimEncuesta.setAdapter("");

    }
}