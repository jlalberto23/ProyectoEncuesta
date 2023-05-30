package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class usuarioConsultar extends Activity {
    conexionDB helper;
    RadioButton docentebtn;
    RadioButton estudiantebtn;
    EditText crearNomtxt;
    EditText contrasena2;
    EditText userCreartxt;
    EditText fechaReg;
    EditText carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_usuario);
        helper = new conexionDB(this);
        docentebtn = findViewById(R.id.docentebtn);
        estudiantebtn = findViewById(R.id.estudiantebtn);
        crearNomtxt = findViewById(R.id.crearNomtxt);
        contrasena2 = findViewById(R.id.contrasena2);
        userCreartxt = findViewById(R.id.userCreartxt);
        fechaReg = findViewById(R.id.fechaReg);
        carnettxt = findViewById(R.id.carnettxt);
    }
    public void consultarUsuario(View v) {
        helper.abrir();
        usuario user = helper.consultarUsuario(carnettxt.getText().toString());
        helper.cerrar();

        if(user == null)
            Toast.makeText(this, "Usuario con carnet " + carnettxt.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{

            //tipoUsuarioList.setOnCheckedChangeListener();
            crearNomtxt.setText(user.getNombreUsuario());
            contrasena2.setText(user.getContrasenia());
            userCreartxt.setText(user.getUsuario());
            fechaReg.setText(user.getFecha_registro());
            carnettxt.setText(user.getCarnet());

        }
    }

    private int obtenerPosicionEnSpinner(Spinner tipoUsuarioList, Spinner codigoTipoUsuario) {
        return 0;
    }

    public void limpiarTexto(View v) {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }

}
