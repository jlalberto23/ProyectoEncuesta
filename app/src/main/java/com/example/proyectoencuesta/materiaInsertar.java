package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class materiaInsertar extends Activity{

    conexionDB helper;
    EditText nombreMateria,codigoMateria,ciclo,anio;
    Button guardar, cancelar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_materia);
        helper = new conexionDB(this);
        nombreMateria = findViewById(R.id.nomMateriatxt);
        codigoMateria = findViewById(R.id.codMateriatxt);
        ciclo = findViewById(R.id.ciclotxt);
        anio = findViewById(R.id.aniotxt);
        guardar = findViewById(R.id.btnCrearM);
        cancelar = findViewById(R.id.btnCancelarCM);
        guardar.setOnClickListener(onclic);
        cancelar.setOnClickListener(onclic);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnCrearM:
                        String res;
                        materia mat = new materia();
                        mat.setNombreMateria(nombreMateria.getText().toString());
                        mat.setCodigoMateria(codigoMateria.getText().toString());
                        mat.setAnio(anio.getText().toString());
                        mat.setCiclo(ciclo.getText().toString());
                        helper.abrir();
                        res=helper.insertar(mat);
                        helper.cerrar();
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnCancelarCM:
                        limpiar();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiar(){
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");
    }
}
