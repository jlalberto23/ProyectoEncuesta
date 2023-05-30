package com.example.proyectoencuesta;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class crearEncuesta extends Activity {
    Button guardar, cancelar;
    Spinner sp;
    EditText nombreE, fechaC, numP, limInt, fechaIn, fechaFin;
    Switch estado;
    String[] tipos = {"Verdadero o Falso","Respuesta Corta"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_encuesta);
        guardar = findViewById(R.id.btnGuardar);
        cancelar = findViewById(R.id.btnCancelar);
        sp = findViewById(R.id.spinner);
        nombreE = findViewById(R.id.txtNombreEncuesta);
        fechaC = findViewById(R.id.txtFechaC);
        numP = findViewById(R.id.txtNumPre);
        limInt = findViewById(R.id.txtLimInt);
        fechaIn = findViewById(R.id.txtFechaIn);
        fechaFin = findViewById(R.id.txtFechaFin);
        estado = findViewById(R.id.swEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipos);
        sp.setAdapter(adapter);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnGuardar:
                        //
                        break;
                    case R.id.btnCancelar:
                        break;
                }
            }catch (Exception e){

            }
        }
    };
}