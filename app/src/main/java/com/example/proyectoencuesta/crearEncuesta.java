package com.example.proyectoencuesta;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class crearEncuesta extends Activity {
    Button guardar, cancelar;
    Spinner sp;
    EditText nombreE, fechaC, numP, limInt, fechaIn, fechaFin;
    Switch estado;
    String[] tipos = {"Verdadero o Falso","Respuesta Corta"};
    conexionDB helper;
    int ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_encuesta);
        guardar = findViewById(R.id.btnGuardar);
        cancelar = findViewById(R.id.btnCancelar);
        sp = findViewById(R.id.spTipoEncuesta);
        nombreE = findViewById(R.id.txtNombreEncuesta);
        fechaC = findViewById(R.id.txtFechaC);
        numP = findViewById(R.id.txtNumPre);
        limInt = findViewById(R.id.txtLimInt);
        fechaIn = findViewById(R.id.txtFechaIn);
        fechaFin = findViewById(R.id.txtFechaFin);
        estado = findViewById(R.id.swEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipos);
        sp.setAdapter(adapter);
        guardar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
        helper = new conexionDB(this);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnGuardar:
                        String res;
                        encuesta enc = new encuesta();
                        enc.setNombreEncuesta(nombreE.getText().toString());
                        enc.setFechaCreacion(fechaC.getText().toString());
                        enc.setNumeroPreguntas(Integer.parseInt(numP.getText().toString()));
                        enc.setLimiteIntentos(Integer.parseInt(limInt.getText().toString()));
                        enc.setFechaInicio(fechaIn.getText().toString());
                        enc.setFechaFin(fechaFin.getText().toString());
                        enc.setEstadoEncuesta(estado.isChecked());
                        if(sp.getSelectedItem()=="Verdadero o Falso")
                            ten = 1;
                        else if(sp.getSelectedItem()=="Respuesta Corta")
                            ten = 2;
                        enc.setIdTipoEncuesta(ten);
                        helper.abrir();
                        res=helper.insertar(enc);
                        helper.cerrar();
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                        if(ten==1){
                            Intent intent = new Intent(v.getContext(), agregarPreguntaVyF.class);
                            intent.putExtra("numP", numP.getText().toString());
                            intent.putExtra("nomEn", nombreE.getText().toString());
                            startActivityForResult(intent,1234);
                        } else if (ten==2) {
                            Intent intent = new Intent(v.getContext(), agregarPreguntaResCorta.class);
                            intent.putExtra("numP", numP.getText().toString());
                            intent.putExtra("nomEn", nombreE.getText().toString());
                            startActivityForResult(intent,1234);
                        }
                        break;
                    case R.id.btnCancelar:
                        limpiar();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public void limpiar(){
        nombreE.setText("");
        fechaC.setText("");
        numP.setText("");
        limInt.setText("");
        fechaIn.setText("");
        fechaFin.setText("");
        estado.setSwitchPadding(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipos);
        sp.setAdapter(adapter);
        estado.setChecked(false);
    }
}