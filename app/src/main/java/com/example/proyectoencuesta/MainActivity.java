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

    private int codigoTipoUsuario;

    public void setCodigoTipoUsuario(int codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;

    }
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
        System.out.println(u.getCodigUsuario());
    }

    View.OnClickListener onclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            System.out.println(u.getCodigUsuario());
            try {
                switch (v.getId()) {
                    case R.id.btnIngresar:
                        cn.abrir();
                        System.out.println(u.getCodigUsuario());
                        if (cn.login(usuario.getText().toString(), contra.getText().toString())) {

                            int codigoTipoUsuario = u.getCodigoTipoUsuario();
                            System.out.println(u.getCodigUsuario());

                            if (codigoTipoUsuario == 0) {
                                Intent intent = new Intent(MainActivity.this, vistaDocente.class);
                                startActivity(intent);
                            } else if (codigoTipoUsuario == 2) {
                                Intent intent = new Intent(MainActivity.this, vistaEstudiante.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Tipo de usuario desconocido", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }

                        break;
                    case R.id.btnCancelar:
                        usuario.setText("");
                        contra.setText("");
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}