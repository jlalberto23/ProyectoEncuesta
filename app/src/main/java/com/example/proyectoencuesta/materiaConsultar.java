package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class materiaConsultar extends Activity{

    conexionDB helper;
    EditText nombreMateria, codigoMateria,ciclo,anio;
    Button consultar,cancelar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_materia);
         helper = new conexionDB(this);

        nombreMateria = findViewById(R.id.nomMateriatxt);
        codigoMateria = findViewById(R.id.codMateriatxt);
        ciclo = findViewById(R.id.ciclotxt);
        anio = findViewById(R.id.aniotxt);
        consultar = findViewById(R.id.btnConsultarM);
        cancelar = findViewById(R.id.btnCancelarCM);
        consultar.setOnClickListener(conclic);
        cancelar.setOnClickListener(conclic);
    }

    View.OnClickListener conclic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnConsultarM:
                        helper.abrir();
                        materia mate = helper.consultarMateria(codigoMateria.getText().toString());
                        helper.cerrar();
                        if(mate  == null)
                            Toast.makeText(v.getContext(), "Codigo de materia " + codigoMateria.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
                        else{
                            nombreMateria.setText(mate.getNombreMateria());
                            ciclo.setText(mate.getCiclo());
                            anio.setText(String.valueOf(mate.getAnio()));
                        }
                        break;
                    case R.id.btnCancelarCM:
                        limpiarTexto();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexto() {
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");

    }

}
