package com.example.proyectoencuesta;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class usuarioInsertar extends Activity{
    conexionDB helper;
    Spinner tipoUsuarioList;
    EditText crearNomtxt;
    EditText contrasena2;
    EditText userCreartxt;
    EditText fechaReg;
    EditText carnettxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);
       // helper = new conexionDB(this);

        tipoUsuarioList = (Spinner) findViewById(R.id.tipoUsuarioList);
        crearNomtxt = (EditText) findViewById(R.id.crearNomtxt);
        contrasena2 = (EditText) findViewById(R.id.contrasena2);
        userCreartxt = (EditText) findViewById(R.id.userCreartxt);
        fechaReg = (EditText) findViewById(R.id.fechaReg);
        carnettxt = (EditText) findViewById(R.id.carnettxt);
    }

    public void insertarUsuario(View v) {
        String carnet=carnettxt.getText().toString();
        String nombre=crearNomtxt.getText().toString();
        String usuario=userCreartxt.getText().toString();
        String contrasena=contrasena2.getText().toString();
        String fecha = fechaReg.getText().toString();
        Spinner tipoUsuario = (Spinner) findViewById(R.id.tipoUsuarioList);
        String tipoUsuarioSeleccionado = tipoUsuario.getSelectedItem().toString();

        String regInsertados;
        usuario user=new usuario();
        user.setCarnet(carnet);
        user.setNombreUsuario(nombre);
        user.setUsuario(usuario);
        user.setContrasenia(contrasena);
        user.setFecha_registro(fecha);
        user.setCodigoTipoUsuario(tipoUsuario);


   //     helper.abrir();
     //   regInsertados=helper.insertar(user);
       // helper.cerrar();
        //Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        carnettxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");

    }
}
