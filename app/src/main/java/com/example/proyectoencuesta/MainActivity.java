package com.example.proyectoencuesta;

import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    Button ingresar, cancelar;
    EditText usuario, contra;
    conexionDB BDhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingresar = findViewById(R.id.btnIngresar);
        cancelar = findViewById(R.id.btnCancelar);
        usuario = findViewById(R.id.txtUsuario);
        contra = findViewById(R.id.txtContraseña);

        BDhelper = new conexionDB(this);

        BDhelper.abrir();
        String tost=BDhelper.llenarDatos();
        BDhelper.cerrar();
        Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
    }


}