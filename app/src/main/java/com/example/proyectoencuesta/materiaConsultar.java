package com.example.proyectoencuesta;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class materiaConsultar extends Activity{

    conexionDB helper;
    EditText nombreMateria, codigoMateria,ciclo,anio;
    Button consultar,cancelar;
    AutoCompleteTextView listamateria;

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

        listamateria = findViewById(R.id.materiaAuto);
        List<String> lista = listaMaterias();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listamateria.setAdapter(adapter);
    }

    private List<String> listaMaterias(){
        List<String> lista = new ArrayList<>();
        helper.abrir();
        Cursor cursor = helper.mostrarMaterias();
        helper.cerrar();
        if(cursor != null && cursor.moveToFirst()){
            do{
                String materia = cursor.getString(cursor.getColumnIndexOrThrow("codigo_materia"));
                lista.add(materia);
            } while (cursor.moveToNext());
        } else {
            Log.d("materiaConsultar", "El cursor está vacío");
        }


        if(cursor != null){
            cursor.close();
        }

        return lista;

    }

    View.OnClickListener conclic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnConsultarM:
                        helper.abrir();
                        materia mate = helper.consultarMateria(listamateria.getText().toString());
                        helper.cerrar();
                        if(mate  == null)
                            Toast.makeText(v.getContext(), "Codigo de materia " + listamateria.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
                        else{
                            nombreMateria.setText(mate.getNombreMateria());
                            ciclo.setText(mate.getCiclo());
                            anio.setText(String.valueOf(mate.getAnio()));
                            codigoMateria.setText(mate.getCodigoMateria());
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
        listamateria.setText("");

    }

}
