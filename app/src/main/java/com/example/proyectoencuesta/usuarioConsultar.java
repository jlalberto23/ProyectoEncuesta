package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class usuarioConsultar extends Activity {
    conexionDB helper;
    RadioButton docentebtn,estudiantebtn;
    EditText crearNomtxt,contrasena2,userCreartxt,fechaReg,carnettxt;
    Button consultar, cancelar;

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
        cancelar = findViewById(R.id.btnCancelarC);
        consultar = findViewById(R.id.btnConsultar);
        consultar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnConsultar:
                        helper.abrir();
                        usuario user = helper.consultarUsuario(carnettxt.getText().toString());
                        helper.cerrar();
                        if(user == null)
                            Toast.makeText(v.getContext(), "Usuario con carnet " + carnettxt.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
                        else{
                            if(user.getCodigoTipoUsuario()==1)
                                docentebtn.setSelected(true);
                            else if (user.getCodigoTipoUsuario()==2)
                                estudiantebtn.setSelected(true);
                            crearNomtxt.setText(user.getNombreUsuario());
                            contrasena2.setText(user.getContrasenia());
                            userCreartxt.setText(user.getUsuario());
                            fechaReg.setText(user.getFecha_registro());
                            carnettxt.setText(user.getCarnet());
                        }
                        break;
                    case R.id.btnCancelarC:
                        limpiarTexto();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexto() {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }

}
