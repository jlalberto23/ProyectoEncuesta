    package com.example.proyectoencuesta;

    import android.annotation.SuppressLint;
    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;
    import java.util.ArrayList;
    import java.util.List;


    public class encuestaLista extends Activity {
        Button responder,cancelar;
        Spinner spinnerEncuesta;
        String idTipoEncuesta;
        TextView nombre, numeroPreg;
        String nom;
        String nombreEncuesta;

        conexionDB helper;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.encuesta_lista);
            helper = new conexionDB(this);
            spinnerEncuesta = findViewById(R.id.spinnerEncuestas);
            responder = findViewById(R.id.responderbtn);
            cancelar = findViewById(R.id.cancelarbtn);


            helper.abrir();
            List<encuesta> lista = llenarSp();
            ArrayAdapter<encuesta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            spinnerEncuesta.setAdapter(adapter);
            spinnerEncuesta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    // Obtener el nombre de la encuesta seleccionada en el Spinner
                    encuesta en = (encuesta) adapterView.getItemAtPosition(position);
                    nombreEncuesta = en.getNombreEncuesta();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            helper.cerrar();
            responder.setOnClickListener(onclic);
            cancelar.setOnClickListener(onclic);
        }
        private List<encuesta> llenarSp(){
            List<encuesta> lis = new ArrayList<>();

            Cursor cursor = helper.mostrarEncuestasSP();
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        encuesta en = new encuesta();
                        en.setIdEncuesta(cursor.getInt(0));
                        en.setNombreEncuesta(cursor.getString(1));
                        lis.add(en);
                    }while (cursor.moveToNext());
                }
            }
            return lis;
        }
        /////
        View.OnClickListener onclic = new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                try {
                    switch (view.getId()) {
                        case R.id.responderbtn:
                            helper.abrir();
                            System.out.println(spinnerEncuesta.getSelectedItem());
                            encuesta encue = helper.consultarEncuesta(nombreEncuesta);

                            helper.cerrar();
                            if (encue == null)
                                Toast.makeText(view.getContext(), "Encuesta no encontrada", Toast.LENGTH_LONG).show();
                            else if (encue.getIdTipoEncuesta() == 1) {
                                Intent intent = new Intent(view.getContext(), preguntasVyF.class);
                                intent.putExtra("nombreEncuesta", nombreEncuesta);
                                intent.putExtra("idEncuesta", encue.getIdEncuesta());
                                startActivityForResult(intent, 1234);
                            } else if (encue.getIdTipoEncuesta() == 2) {
                                Intent intent = new Intent(view.getContext(), preguntaResCorta.class);
                                intent.putExtra("nombreEncuesta", nombreEncuesta);
                                intent.putExtra("idEncuesta", encue.getIdEncuesta());
                                startActivityForResult(intent, 1234);
                            }
                            break;
                        case R.id.cancelarbtn:
                            limpiarTexto();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        public void limpiarTexto() {
            finish();

        }


    }

