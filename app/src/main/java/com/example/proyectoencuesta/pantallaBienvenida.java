package com.example.proyectoencuesta;

import static android.view.View.INVISIBLE;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class pantallaBienvenida extends AppCompatActivity {
    Button ingresar, crear, sincuenta, llenarDBBtn;
    conexionDB cn;
    int REQUEST_CODE = 200;
    private static final String TAG ="PERMISSION_TAG";
    private static final int STORAGE_PERMISSION_CODE = 100;

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

        if (verificarPermisos()){
            Log.d(TAG, "onCreate: Todos los permisos fueron concedidos...");
        } else {
            Log.d(TAG, "onCreate: Faltan permisos...");
            solicitarPermisos();
        }

    }

    private void solicitarPermisos(){
        System.out.println(Build.VERSION.SDK_INT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android 11 o superior
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
            try{
                Log.d(TAG,"Peticion de permiso: try");
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
            catch (Exception e){
                Log.d(TAG,"Peticion de permiso: catch",e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);

            }

        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {

            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }

    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    Log.d(TAG, "onActivityResult: ");
                    //aqui se maneja el resultado del intent
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        //Android 11 o superior
                        if (Environment.isExternalStorageManager()){
                            //Manage external storage permission is granted
                            Log.d(TAG, "onActivityResult: Manage external storage permission is granted");

                        }
                        else {
                            //Manage external storage is denied
                            Log.d(TAG, "onActivityResult: Manage external storage is denied");
                            Toast.makeText(pantallaBienvenida.this, "Manage external storage is denied",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        //Android 10 o inferior

                    }
                }
            }
    );


    public boolean verificarPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //Android 11 o superior
            return Environment.isExternalStorageManager();
        }
        else {
            //Android 10 o inferior
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0){
                //Verificar si todos los permisos fueron concedidos
                boolean write = grantResults [0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults [1] == PackageManager.PERMISSION_GRANTED;

                if (write && read){
                    //External storage permissions granted
                    Log.d(TAG, "onRequestPermissionsResult: External storage permissions granted");

                }
                else {
                    //External storage permissions denied
                    Log.d(TAG, "onRequestPermissionsResult: External storage permissions denied");
                    Toast.makeText(this, "External storage permissions denied", Toast.LENGTH_SHORT).show();

                }

            }
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
