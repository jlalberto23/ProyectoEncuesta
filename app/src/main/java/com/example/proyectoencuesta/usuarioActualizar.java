package com.example.proyectoencuesta;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class usuarioActualizar extends Activity{

    conexionDB helper;
    int tp = 0;
    Button modificar, cancelar;
    RadioButton docentebtn, estudiantebtn;
    EditText crearNomtxt,contrasena2,userCreartxt,fechaReg,carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_usuario);
        helper = new conexionDB(this);

        docentebtn = findViewById(R.id.docentebtn);
        estudiantebtn = findViewById(R.id.estudiantebtn);
        crearNomtxt = findViewById(R.id.crearNomtxt);
        contrasena2 = findViewById(R.id.contrasena2);
        userCreartxt = findViewById(R.id.userCreartxt);
        fechaReg = findViewById(R.id.fechaReg);
        carnettxt = findViewById(R.id.carnettxt);
        modificar = findViewById(R.id.btnActualizar);
        cancelar = findViewById(R.id.btnLimpiar);
        modificar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnActualizar:
                        String res;
                        if(docentebtn.isChecked())
                            tp = 1;
                        else if (estudiantebtn.isChecked())
                            tp = 2;
                        usuario us = new usuario();
                        us.setUsuario(userCreartxt.getText().toString());
                        us.setCodigoTipoUsuario(tp);
                        us.setNombreUsuario(crearNomtxt.getText().toString());
                        us.setContrasenia(contrasena2.getText().toString());
                        us.setCarnet(carnettxt.getText().toString());
                        us.setFecha_registro(fechaReg.getText().toString());
                        helper.abrir();
                        String estado = helper.actualizar(us);
                        helper.cerrar();

                        Toast.makeText(v.getContext(), estado, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnLimpiar:
                        limpiarTexto();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void actualizarUsuario(View v) {
        String carnet=carnettxt.getText().toString();
        String nombre=crearNomtxt.getText().toString();
        String usuario=userCreartxt.getText().toString();
        String contrasena=contrasena2.getText().toString();
        String fecha = fechaReg.getText().toString();
        int tipoUsuarioSeleccionado;
        if (docentebtn.isChecked()) {
            tipoUsuarioSeleccionado = 1; // Docente
        } else if (estudiantebtn.isChecked()) {
            tipoUsuarioSeleccionado = 2; // Estudiante
        } else {
            tipoUsuarioSeleccionado = 0; // Ninguno seleccionado (trata este caso según tus necesidades)
        }



        helper.abrir();
        //String estado = helper.actualizar(usuario);
        helper.cerrar();
       // Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto() {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }


}
