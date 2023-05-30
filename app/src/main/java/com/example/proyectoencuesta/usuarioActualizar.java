package com.example.proyectoencuesta;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class usuarioActualizar extends Activity{

    conexionDB helper;
    RadioGroup tipoUsuarioList;
    RadioButton docentebtn;
    RadioButton estudiantebtn;
    EditText crearNomtxt;
    EditText contrasena2;
    EditText userCreartxt;
    EditText fechaReg;
    EditText carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_usuario);
        // helper = new conexionDB(this);

        RadioGroup tipoUsuarioList = findViewById(R.id.tipoUsuarioList);
        RadioButton docentebtn = findViewById(R.id.docentebtn);
        RadioButton estudiantebtn = findViewById(R.id.estudiantebtn);
        crearNomtxt = (EditText) findViewById(R.id.crearNomtxt);
        contrasena2 = (EditText) findViewById(R.id.contrasena2);
        userCreartxt = (EditText) findViewById(R.id.userCreartxt);
        fechaReg = (EditText) findViewById(R.id.fechaReg);
        carnettxt = (EditText) findViewById(R.id.carnettxt);
    }

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

    public void limpiarTexto(View v) {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }


}
