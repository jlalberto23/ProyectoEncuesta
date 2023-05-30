package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class materiaEliminar extends Activity {
    conexionDB helper;
    EditText txtCod;
    Button eliminar, cancelar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_materia);
        helper = new conexionDB(this);
        txtCod = findViewById(R.id.codMateriatxt);
        eliminar = findViewById(R.id.btnEliminarM);
        cancelar = findViewById(R.id.btnCancelarEM);
        eliminar.setOnClickListener(onclic);
        cancelar.setOnClickListener(onclic);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                switch (view.getId()){
                    case R.id.btnEliminarM:
                        String regEliminadas;
                        materia mate=new materia();
                        mate.setCodigoMateria(txtCod.getText().toString());
                        helper.abrir();
                        regEliminadas=helper.eliminar(mate);
                        helper.cerrar();
                        Toast.makeText(view.getContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnCancelarEM:
                        limpiarTexto();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexto() {
        txtCod.setText("");
    }

}
