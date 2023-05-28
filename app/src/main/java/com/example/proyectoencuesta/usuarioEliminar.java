package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class usuarioEliminar extends Activity{
    EditText carnettxt;
    conexionDB helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_usuario);
        // helper = new conexionDB(this);

        carnettxt = (EditText) findViewById(R.id.carnettxt);
    }
    public void eliminarUsuario(View v){
        String regEliminadas;
        usuario user=new usuario();
        user.setCarnet(carnettxt.getText().toString());
        //helper.abrir();
        //regEliminadas=helper.eliminar(user);
        //helper.cerrar();
        //Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        carnettxt.setText("");

    }


}
