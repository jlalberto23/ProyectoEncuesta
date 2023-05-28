package com.example.proyectoencuesta;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class vistaEstudiante extends ListActivity {


    String[] menu={"Consultar Usuarios", "Actualizar usuario","Consultar Materias", "Actualizar Materias","Consultar Encuestas", "Actualizar Encuestas"};
    String[] activities={"consultar_usuarios", "actualizar_usuario","consultar_materias", "actualizar_materias","encuesta"};

    conexionDB BDhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        BDhelper=new conexionDB(this);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        if(position!=3){
            String nombreValue=activities[position];
            try{
                Class<?>
                        clase=Class.forName("com.example.proyectoencuesta."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }else{

        }
    }
}
