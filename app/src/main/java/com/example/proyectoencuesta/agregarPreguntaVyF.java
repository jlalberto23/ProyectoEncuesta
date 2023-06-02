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
    TextView nombre, numeroPreg;
    EditText pregunta;
    Button next,menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn = new conexionDB(this);
        setContentView(R.layout.agregar_preguntas_vyf);
        nombre = findViewById(R.id.nomCuestionario);
        pregunta = findViewById(R.id.preguntaEdit);
        menu = findViewById(R.id.btnVolver);
        menu.setVisibility(View.INVISIBLE);
        numeroPreg = findViewById(R.id.lblNumpre);
        try{
            Bundle extra = getIntent().getExtras();
            if(extra.getString("numP")!=null || extra.getString("nomEn")!=null)
            {
                numP = Integer.valueOf(extra.getString("numP"));
                nom = extra.getString("nomEn");
                nombre.setText(nom);
                next = findViewById(R.id.btnNext);
            }
            else if(extra.getString("cantP")!=null || extra.getString("nom")!=null){
                numP = Integer.valueOf(extra.getString("cantP"));
                nom = extra.getString("nom");
                nombre.setText(nom);
                next = findViewById(R.id.btnNext);
            }
            if (numP == 1) {
                menu.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        numeroPreg.setText("Pregunta numero " + numP + ":");
        next.setOnClickListener(onclic);
        menu.setOnClickListener(onclic);
    }

    View.OnClickListener onclic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                String res;
                pregunta p = new pregunta();
                switch (view.getId()){
                    case R.id.btnNext:
                        cn.abrir();
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
                        i.putExtra("cantP", String.valueOf(c));
                        i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i,1234);
                        break;
                    case R.id.btnVolver:
                        cn.abrir();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(1);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(view.getContext(),vistaDocente.class);
                        startActivity(in);
                        break;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
}
