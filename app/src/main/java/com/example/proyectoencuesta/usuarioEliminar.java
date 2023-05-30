package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class usuarioEliminar extends Activity{
    EditText carnettxt;
    Button modificar, cancelar;
    conexionDB helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_usuario);
        helper = new conexionDB(this);
        carnettxt = findViewById(R.id.carnettxt);
        modificar = findViewById(R.id.crearCuentabtn);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.crearCuentabtn:
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void eliminarUsuario(View v){
        String regEliminadas;
        usuario user=new usuario();
        user.setCarnet(carnettxt.getText().toString());
        helper.abrir();
        //regEliminadas=helper.eliminar(user);
        helper.cerrar();
        //Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        carnettxt.setText("");

    }
}
