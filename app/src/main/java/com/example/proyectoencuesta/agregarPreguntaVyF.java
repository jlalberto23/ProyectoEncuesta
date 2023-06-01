package com.example.proyectoencuesta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class agregarPreguntaVyF extends Activity {

    conexionDB cn;
    int numP;
    String nom;
    TextView nombre;
    EditText pregunta;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn = new conexionDB(this);
        setContentView(R.layout.agregar_preguntas_vyf);
        nombre = findViewById(R.id.nomCuestionario);
        pregunta = findViewById(R.id.preguntaEdit);

        Bundle extra = getIntent().getExtras();
        numP = Integer.valueOf(extra.getString("numP"));
        nom = extra.getString("nomEn");
        nombre.setText(nom);
        next = findViewById(R.id.btnNext);

        next.setOnClickListener(onclic);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                switch (view.getId()){
                    case R.id.btnNext:
                        cn.abrir();
                        String res;
                        pregunta p = new pregunta();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(1);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_SHORT).show();
                        int c = numP-1;
                        Intent i = new Intent(view.getContext(),agregarPreguntaVyF.class);
                        finish();
                        i.putExtra("numP", Integer.valueOf(c));
                        i.putExtra("nomEn", nom);
                        startActivityForResult(i,1234);
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
}
