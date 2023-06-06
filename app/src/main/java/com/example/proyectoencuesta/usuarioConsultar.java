package com.example.proyectoencuesta;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class usuarioConsultar extends Activity {
    conexionDB helper;
    RadioGroup grupo;
    RadioButton docentebtn,estudiantebtn;
    EditText crearNomtxt,contrasena2,userCreartxt,fechaReg,carnettxt, correotxt;
    Button consultar, cancelar;
    AutoCompleteTextView listaUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_usuario);
        helper = new conexionDB(this);
        grupo = findViewById(R.id.tipoUsuarioList);
        docentebtn = findViewById(R.id.rbDocente);
        estudiantebtn = findViewById(R.id.rbEstudiante);
        crearNomtxt = findViewById(R.id.crearNomtxt);
        contrasena2 = findViewById(R.id.contrasena2);
        userCreartxt = findViewById(R.id.userCreartxt);
        fechaReg = findViewById(R.id.fechaReg);
        carnettxt = findViewById(R.id.carnettxt);
        correotxt = findViewById(R.id.correotxt2);
        cancelar = findViewById(R.id.btnCancelarC);
        consultar = findViewById(R.id.btnConsultar);
        consultar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
        listaUsuario = findViewById(R.id.usuarioAuto);

        List<String> lista = listaUsuarios();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaUsuario.setAdapter(adapter);
    }

    private List<String> listaUsuarios(){
        List<String> lista = new ArrayList<>();
        helper.abrir();
        Cursor cursor = helper.mostrarUsuarios();
        helper.cerrar();
        if(cursor != null && cursor.moveToFirst()){
            do{
                String usuario = cursor.getString(cursor.getColumnIndexOrThrow("carnet"));
                lista.add(usuario);
            } while (cursor.moveToNext());
        } else {
            Log.d("usuarioConsultar", "El cursor está vacío");
        }


        if(cursor != null){
            cursor.close();
        }

        return lista;

    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnConsultar:
                        helper.abrir();
                        usuario user = helper.consultarUsuario(listaUsuario.getText().toString());
                        helper.cerrar();
                        if(user == null)
                            Toast.makeText(v.getContext(), "Usuario con carnet " + listaUsuario.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
                        else{
                            grupo.clearCheck();
                            if(user.getCodigoTipoUsuario()==1)
                                docentebtn.setChecked(true);
                            else if (user.getCodigoTipoUsuario()==2)
                                estudiantebtn.setChecked(true);
                            crearNomtxt.setText(user.getNombreUsuario());
                            contrasena2.setText(user.getContrasenia());
                            userCreartxt.setText(user.getUsuario());
                            fechaReg.setText(user.getFecha_registro());
                            carnettxt.setText(user.getCarnet());
                            correotxt.setText(user.getCorreo());
                        }
                        break;
                    case R.id.btnCancelarC:
                        limpiarTexto();
                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexto() {
        carnettxt.setText("");
        correotxt.setText("");
        crearNomtxt.setText("");
        userCreartxt.setText("");
        contrasena2.setText("");
        fechaReg.setText("");
        listaUsuario.setText("");
    }
}
