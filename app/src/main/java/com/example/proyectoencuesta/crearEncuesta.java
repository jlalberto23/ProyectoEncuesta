package com.example.proyectoencuesta;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class crearEncuesta extends Activity {
    Button guardar, cancelar;
    Spinner sp;
    EditText nombreE, fechaC, numP, limInt, fechaIn, fechaFin;
    Switch estado;
    String[] tipos = {"Verdadero o Falso","Respuesta Corta", "Multimedia", "Audio"};
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        guardar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
        helper = new conexionDB(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        fechaC.setText(dateFormat.format(new Date()));

        // Calendar para campo fecha Inicio y Fin
        fechaIn.setOnClickListener(v -> {

            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    crearEncuesta.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        fechaIn.setText(year1 + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    },
                    year, month, day);

            datePickerDialog.show();
        });

        fechaFin.setOnClickListener(v -> {

            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(

                    crearEncuesta.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        fechaFin.setText(year1 + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        numP.setFilters(new InputFilter[]{ new MinMaxFilter("1", "20")});
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Fetching the stored data from the SharedPreference
            SharedPreferences sh = getSharedPreferences("SharedPreferenceUsuario", MODE_PRIVATE);
            String id_usuario = sh.getString("id_usuario", "");

            try{
                switch (v.getId()){
                    case R.id.btnGuardar:
                        String res;
                        encuesta enc = new encuesta();
                        enc.setIdUsuario(Integer.valueOf(id_usuario));
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
                        else if(sp.getSelectedItem()=="Multimedia")
                            ten = 3;
                        else if(sp.getSelectedItem()=="Audio")
                            ten = 4;
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
                        } else if (ten==3) {
                            Intent intent = new Intent(v.getContext(), agregarPreguntaMultimedia.class);
                            intent.putExtra("numP", numP.getText().toString());
                            intent.putExtra("nomEn", nombreE.getText().toString());
                            startActivityForResult(intent,1234);
                        }
                        else if (ten==4) {
                            Intent intent = new Intent(v.getContext(), AgregarAudioPregunta.class);
                            intent.putExtra("numP", numP.getText().toString());
                            intent.putExtra("nomEn", nombreE.getText().toString());
                            startActivityForResult(intent,1234);
                        }
                        break;
                    case R.id.btnCancelar:
                        limpiar(v);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public void limpiar(View v){
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
        Intent in = new Intent(v.getContext(),vistaDocente.class);
        startActivity(in);
    }
}