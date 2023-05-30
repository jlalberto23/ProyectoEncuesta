package com.example.proyectoencuesta;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class vistaDocente extends ListActivity {

    String[] menu={"Crear usuario","Consultar Usuarios", "Actualizar usuario","Eliminar Usuarios","Crear Materias","Consultar Materias", "Actualizar Materias","Eliminar Materias","Crear Encuestas", "Eliminar Encuestas","Responder Encuesta"};
    String[] activities={"usuarioInsertar","usuarioConsultar", "usuarioEliminar","usuarioActualizar","materiaInsertar","materiaConsultar", "materiaActualizar","materiaEliminar","crearEncuesta","eliminarEncuesta","encuestaLista" };
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
