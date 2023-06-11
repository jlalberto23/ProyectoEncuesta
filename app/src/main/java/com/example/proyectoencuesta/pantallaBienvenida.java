package com.example.proyectoencuesta;

import static android.view.View.INVISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class pantallaBienvenida extends Activity {
    Button ingresar, crear, sincuenta, llenarDBBtn;
    conexionDB cn;
    int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Base de datos
        cn = new conexionDB(this);//Enviamos el contexto
        //cn.abrir();
        //cn.llenarDatos();//Llenamos datos
        setContentView(R.layout.pantalla_bienvenida);
        cn.cerrar();
        //INICIALIZANDO LAS VARIABLES LOCALES
        ingresar = findViewById(R.id.btnLogin);
        crear = findViewById(R.id.btnCrearC);
        sincuenta = findViewById(R.id.btnIngresarSC);
        llenarDBBtn = findViewById(R.id.btnLlenarDB);
        //SETEO DE METODOS
        ingresar.setOnClickListener(onclick);
        crear.setOnClickListener(onclick);
        sincuenta.setOnClickListener(onclick);
        llenarDBBtn.setOnClickListener(onclick);
        verificarPermisos();
    }
    private void verificarPermisos(){
        int permisoAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permisoWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permisoAudio == PackageManager.PERMISSION_GRANTED && permisoWrite == PackageManager.PERMISSION_GRANTED &&
                permisoRead == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Todos los permisos fueron concedidos correctamente",Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnLogin:
                        Intent intent = new Intent(pantallaBienvenida.this,MainActivity.class);
                        pantallaBienvenida.this.startActivity(intent);
                        break;
                    case R.id.btnCrearC:
                        Intent intent3 = new Intent(pantallaBienvenida.this,usuarioInsertar.class);
                        pantallaBienvenida.this.startActivity(intent3);
                        break;
                    case R.id.btnIngresarSC:
                        Intent intent2 = new Intent(pantallaBienvenida.this,encuestaLista.class);
                        pantallaBienvenida.this.startActivity(intent2);
                        break;
                    case R.id.btnLlenarDB:
                        cn.abrir();
                        Toast.makeText(pantallaBienvenida.this, "Database: " + cn.llenarDatos(), Toast.LENGTH_SHORT).show();
                        llenarDBBtn.setVisibility(INVISIBLE);
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
}
