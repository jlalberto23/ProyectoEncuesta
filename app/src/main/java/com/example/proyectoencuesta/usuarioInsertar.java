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
    int tp = 0;
    RadioGroup tipoUsuarioList;
    RadioButton docentebtn, estudiantebtn;
    EditText crearNomtxt, contrasena2, userCreartxt, fechaReg, carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);
        helper = new conexionDB(this);

        tipoUsuarioList = findViewById(R.id.tipoUsuarioList);
        docentebtn = findViewById(R.id.docentebtn);
        estudiantebtn = findViewById(R.id.estudiantebtn);
        crear = findViewById(R.id.crearCuentabtn);
        limpiar = findViewById(R.id.limpiarbtn);
        crearNomtxt = findViewById(R.id.crearNomtxt);
        contrasena2 = findViewById(R.id.contrasena2);
        userCreartxt =  findViewById(R.id.userCreartxt);
        fechaReg = findViewById(R.id.fechaReg);
        carnettxt = findViewById(R.id.carnettxt);
        crear = findViewById(R.id.crearCuentabtn);
        limpiar = findViewById(R.id.limpiarbtn);
        crear.setOnClickListener(onclick);
        limpiar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.crearCuentabtn:
                        if(docentebtn.isChecked())
                            tp = 1;
                        else if (estudiantebtn.isChecked())
                            tp = 2;
                        String res;
                        usuario us = new usuario();
                        us.setUsuario(userCreartxt.getText().toString());
                        us.setCodigoTipoUsuario(tp);
                        us.setNombreUsuario(crearNomtxt.getText().toString());
                        us.setContrasenia(contrasena2.getText().toString());
                        us.setCarnet(carnettxt.getText().toString());
                        us.setFecha_registro(fechaReg.getText().toString());
                        helper.abrir();
                        res = helper.insertarU(us);
                        limpiarTexbox();
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.limpiarbtn:
                        limpiarTexbox();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexbox(){
        crearNomtxt.setText("");
        contrasena2.setText("");
        userCreartxt.setText("");
        fechaReg.setText("");
        carnettxt.setText("");
    }
}
