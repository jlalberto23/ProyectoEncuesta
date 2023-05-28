package com.example.proyectoencuesta;

import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button ingresar, cancelar;
    EditText usuario, contra;

    conexionDB cn;
    usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn = new conexionDB(this);
        u = new usuario();
        setContentView(R.layout.activity_main);
        ingresar = findViewById(R.id.btnIngresar);
        cancelar = findViewById(R.id.btnCancelar);
        usuario = findViewById(R.id.txtUsuario);
        contra = findViewById(R.id.txtContraseña);
        ingresar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnIngresar:
                        cn.abrir();
                        if(cn.login(usuario.getText().toString(),contra.getText().toString())) {
                            if(u.getCodigoTipoUsuario()==1){Intent intent = new Intent(MainActivity.this,menu.class);
                                MainActivity.this.startActivity(intent);}

                            Intent intent = new Intent(MainActivity.this,vistaDocente.class);
                            MainActivity.this.startActivity(intent);

                        else if (u.getCodigoTipoUsuario()==2){Intent intent = new Intent(MainActivity.this,menu.class);
                            MainActivity.this.startActivity(intent);}
                        Intent intent = new Intent(MainActivity.this,vistaEstudiante.class);
                        MainActivity.this.startActivity(intent);
                        }


                        else{
                            Toast.makeText(v.getContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.btnCancelar:
                        usuario.setText("");
                        contra.setText("");
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };

}