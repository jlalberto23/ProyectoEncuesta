package com.example.proyectoencuesta;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class usuarioInsertar extends Activity{
    conexionDB helper;
    Button crear, limpiar;
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
        setContentView(R.layout.crear_usuario);
       helper = new conexionDB(this);

        RadioGroup tipoUsuarioList = findViewById(R.id.tipoUsuarioList);
        RadioButton docentebtn = findViewById(R.id.docentebtn);
        RadioButton estudiantebtn = findViewById(R.id.estudiantebtn);
        crear = findViewById(R.id.crearCuentabtn);
        limpiar = findViewById(R.id.limpiarbtn);
        crearNomtxt = (EditText) findViewById(R.id.crearNomtxt);
        contrasena2 = (EditText) findViewById(R.id.contrasena2);
        userCreartxt = (EditText) findViewById(R.id.userCreartxt);
        fechaReg = (EditText) findViewById(R.id.fechaReg);
        carnettxt = (EditText) findViewById(R.id.carnettxt);

    }

    private void obtenerdatos(){
        helper.abrir();

    }

    public void insertarUsuario(View v) {
        String regInsertados;
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

        usuario user=new usuario();
        user.setCarnet(carnet);
        user.setNombreUsuario(nombre);
        user.setUsuario(usuario);
        user.setContrasenia(contrasena);
        user.setFecha_registro(fecha);
        user.setCodigoTipoUsuario(tipoUsuarioSeleccionado);


        helper.abrir();
        regInsertados=helper.insertar(user);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }


}
