package com.example.proyectoencuesta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class agregarPreguntaResCorta  extends Activity {

    conexionDB cn;
    int numP;
    String nom;
    TextView nombre, numeroPreg;
    EditText pregunta;
    Button next, volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_pregunta_res_corta);
        cn = new conexionDB(this);
        volver = findViewById(R.id.btnsave);
        next = findViewById(R.id.sigbtn);
        volver.setVisibility(View.INVISIBLE);
        nombre = findViewById(R.id.lblNombreEnc);
        pregunta = findViewById(R.id.txtPreguntaCort);
        numeroPreg = findViewById(R.id.lblNumeroPreg);
        try{
            Bundle extra = getIntent().getExtras();
            if(extra.getString("numP")!=null || extra.getString("nomEn")!=null){
                numP = Integer.valueOf(extra.getString("numP"));
                nom = extra.getString("nomEn");
            }else if(extra.getString("cantP")!=null || extra.getString("nom")!=null){
                numP = Integer.valueOf(extra.getString("cantP"));
                nom = extra.getString("nom");
            }
            nombre.setText(nom);
            numeroPreg.setText("Pregunta numero " + numP + ":");
            if (numP == 1) {
                volver.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){ e.printStackTrace(); }
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
                        p.setIdTpoPregunta(2);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_SHORT).show();
                        int c = numP-1;
                        Intent i = new Intent(view.getContext(),agregarPreguntaResCorta.class);
                        i.putExtra("cantP", String.valueOf(c));
                        i.putExtra("nom", nombre.getText().toString());
                        startActivityForResult(i,1234);
                        break;
                    case R.id.btnsave:
                        cn.abrir();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(2);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_LONG).show();
                        Intent in = new Intent(view.getContext(),agregarPreguntaResCorta.class);
                        startActivity(in);
                        break;
                }
            }catch (Exception e){ e.printStackTrace(); }
        }
    };
}
