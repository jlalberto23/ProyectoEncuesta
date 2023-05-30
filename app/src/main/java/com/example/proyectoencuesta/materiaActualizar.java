package com.example.proyectoencuesta;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class materiaActualizar extends Activity{

    conexionDB helper;
    EditText nombreMateria,codigoMateria,ciclo,anio;
    Button actualizar, cancelar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_materia);
         helper = new conexionDB(this);

        nombreMateria = findViewById(R.id.nomMateriatxt);
        codigoMateria = findViewById(R.id.codMateriatxt);
        ciclo = findViewById(R.id.ciclotxt);
        anio = findViewById(R.id.aniotxt);
        actualizar = findViewById(R.id.btnActualizarM);
        cancelar = findViewById(R.id.btnCancelarAM);
        actualizar.setOnClickListener(onclick);
        cancelar.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.btnActualizarM:
                        materia mat = new materia();
                        mat.setCiclo(ciclo.getText().toString());
                        mat.setAnio(anio.getText().toString());
                        mat.setCodigoMateria(codigoMateria.getText().toString());
                        mat.setNombreMateria(nombreMateria.getText().toString());
                        helper.abrir();
                        String estado = helper.actualizar(mat);
                        helper.cerrar();
                        Toast.makeText(v.getContext(), estado, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnCancelarAM:
                        limpiarTexto();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public void limpiarTexto() {
        nombreMateria.setText("");
        codigoMateria.setText("");
        ciclo.setText("");
        anio.setText("");
    }
}