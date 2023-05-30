package com.example.proyectoencuesta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class usuarioEliminar extends Activity{
    EditText carnettxt;
    Button eliminar, cancelar;
    conexionDB helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_usuario);
        helper = new conexionDB(this);
        carnettxt = findViewById(R.id.carnettxt);
        eliminar = findViewById(R.id.btnEliminar);
        cancelar = findViewById(R.id.limpiarbtn);
        eliminar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnEliminar:
                        String res;
                        usuario user=new usuario();
                        user.setCarnet(carnettxt.getText().toString());
                        helper.abrir();
                        res=helper.eliminar(user);
                        helper.cerrar();
                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.limpiarbtn:
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

    }
}
