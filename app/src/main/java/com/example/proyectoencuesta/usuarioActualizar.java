package com.example.proyectoencuesta;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class usuarioActualizar extends Activity{

    conexion helper;
    Spinner tipoUsuarioList;
    EditText crearNomtxt;
    EditText contrasena2;
    EditText userCreartxt;
    EditText fechaReg;
    EditText carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_usuario);
        // helper = new conexion(this);

        tipoUsuarioList = (Spinner) findViewById(R.id.tipoUsuarioList);
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
        Spinner tipoUsuario = (Spinner) findViewById(R.id.tipoUsuarioList);
        String tipoUsuarioSeleccionado = tipoUsuario.getSelectedItem().toString();



        //helper.abrir();
        //String estado = helper.actualizar(usuario);
        //helper.cerrar();
        //Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }


}
