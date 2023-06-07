package com.example.proyectoencuesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class conexionDB {

    private static final String[] camposTipoUsuario = new String[]{"id_tipo_usuario","nombre_tipo_usuario"};
    private static final String[] camposMateria = new String[]{};
    private static final String[] camposEncuesta = new String[]{"id_encuesta","id_usuario","id_tipo_encuesta","nombre_encuesta","fecha_creacion","id_estado_encuesta","numero_preguntas","limite_intentos","fecha_inicio","fecha_fin"};
    private static final String[] camposUsuario = new String [] {"id_usuario","id_tipo_usuario","nombre_usuario","usuario","contrasenia","carnet","fecha_registro", "correo"};
    public static final String[] camposPregunta = new String[] {"id_pregunta", "id_encuesta", "id_tipo_pregunta", "texto_pregunta", "es_obligatoria", "orden_pregunta", "archivo_multimedia"};
    //private int idTipoU;
    private int codigoTipoUsuario;

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public static SharedPreferences prefs;

    public int getCodigoTipoUsuario() {
        return codigoTipoUsuario;
    }

    public void setCodigoTipoUsuario(int codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;

    }

    public conexionDB(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BASE_DATOS = "SurveyUES.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE area_evaluativa (id_area_evaluativa INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , id_encuesta integer, nombre_area char(256), orden_numerico integer);");
                db.execSQL("CREATE TABLE encuesta (id_encuesta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_usuario  integer, id_tipo_encuesta     integer, nombre_encuesta      char(256), fecha_creacion       timestamp, id_estado_encuesta   integer, numero_preguntas     integer, limite_intentos      integer, fecha_inicio         timestamp, fecha_fin            timestamp );");
                db.execSQL("CREATE TABLE materia (id_materia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre_materia  char(50), codigo_materia       char(20), ciclo                char(20), anio                 char(4) );");
                db.execSQL("CREATE TABLE materia_usuario (id_materia_usuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, [id_materia] INTEGER  NOT NULL, [id_usuario] INTEGER  NOT NULL );");
                db.execSQL("CREATE TABLE opcion_respuesta (id_opcion_respuesta INTEGER, id_pregunta integer, texto_respuesta char(256), es_la_correcta smallint);");
                db.execSQL("CREATE TABLE pregunta (id_pregunta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_encuesta  integer, id_tipo_pregunta integer, texto_pregunta       char(256), es_obligatoria       smallint, orden_pregunta       integer, [archivo_multimedia] BLOB);");
                db.execSQL("CREATE TABLE pregunta_area_evaluativa (id_pregunta integer not null, id_area_evaluativa integer not null, primary key (id_pregunta, id_area_evaluativa) );");
                //db.execSQL("CREATE TABLE respuesta_usuarios (id_respuesta_usuarios INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_opcion_respuesta  integer, id_usuario           integer, numero_intento       integer, fecha_respondida     date, dispositivo          char(256), es_usuario_anonimo   smallint, texto_respuesta char(150) );");
                db.execSQL("CREATE TABLE respuesta_usuarios (id_respuesta_usuarios INTEGER PRIMARY KEY AUTOINCREMENT, id_encuesta integer, id_pregunta  integer, id_usuario           integer, numero_intento       integer, fecha_respondida     date, dispositivo          char(256), es_usuario_anonimo   smallint, texto_respuesta char(150) );");
                db.execSQL("CREATE TABLE tipo_encuesta (id_tipo_encuesta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre_tipo_encuesta char(100) );");
                db.execSQL("CREATE TABLE tipo_pregunta (id_tipo_pregunta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre_tipo_pregunta char(100) );");
                db.execSQL("CREATE TABLE tipo_respuesta (id_tipo_respuesta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre_tipo_respuesta char(256) );");
                db.execSQL("CREATE TABLE tipo_usuario (id_tipo_usuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre_tipo_usuario  char(256) );");
                db.execSQL("CREATE TABLE usuario (id_usuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_tipo_usuario integer, nombre_usuario char(100), usuario char(100), contrasenia char(100), carnet char(100), fecha_registro TIMESTAMP, correo char(100), foreign key (id_tipo_usuario) references tipo_usuario (id_tipo_usuario) );");
            }catch(SQLException e){ e.printStackTrace(); }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }


    /*********LOGIN***********/
    public boolean login(String u, String c){
        boolean resp = false;
        String[] id = {u, c};
        Cursor cursor = db.query("usuario", camposUsuario, "usuario = ? AND contrasenia = ?", id, null, null, null);
        if(cursor.moveToFirst()) {
            setCodigoTipoUsuario(cursor.getInt(1));
            resp = true;

            // Guardamos los datos de usuario en session en "SharedPreferenceUsuario" in private mode
            prefs = this.context.getSharedPreferences("SharedPreferenceUsuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor usuarioEdit = prefs.edit();

            // write all the data entered by the user in SharedPreference and apply
            usuarioEdit.putString("id_usuario", String.valueOf(cursor.getInt(0)) );
            usuarioEdit.putInt("id_tipo_usuario", cursor.getInt(1) );
            usuarioEdit.putInt("correo_usuario", cursor.getInt(1) );
            usuarioEdit.apply();

            System.out.println("ID_USUARIO DE SESSION " + prefs.getString("id_usuario", ""));
        }
        return resp;
    }

    public SQLiteDatabase getReadableDatabase() {
        return null;
    }

    /************************/


    public String insertar(tipoEncuesta tipoEncuesta){

        String regInsertados="Encuesta registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues tipoEn = new ContentValues();
        tipoEn.put("id_tipo_encuesta", tipoEncuesta.getIdTipoEncuesta());
        tipoEn.put("nombre_tipo_encuesta", tipoEncuesta.getNombreTipoEncuesta());

        contador=db.insert("tipo_encuesta", null, tipoEn);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(tipoPregunta tipoPregunta){

        String regInsertados="Pregunta registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues tipoPre = new ContentValues();
        tipoPre.put("id_tipo_pregunta", tipoPregunta.getIdTipoPregunta());
        tipoPre.put("nombre_tipo_pregunta", tipoPregunta.getNombreTipoPregunta());

        contador=db.insert("tipo_pregunta", null, tipoPre);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(tipoUsuario tipoUsuario){

        String regInsertados="Tipo registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues tipoUsu = new ContentValues();
        tipoUsu.put("id_tipo_usuario", tipoUsuario.getCodigoTipoUsuario());
        tipoUsu.put("nombre_tipo_usuario", tipoUsuario.getNombreTipoUsuario());

        contador=db.insert("tipo_usuario", null, tipoUsu);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(usuario usuario){

            String regInsertados="Usuario registrado exitosamente #= ";
            long contador=0;
            /*if (verificarIntegridad(alumno,5)) {
                regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
            }
            else
            {*/
            ContentValues usuA = new ContentValues();
            usuA.put("id_usuario", usuario.getCodigUsuario());
            usuA.put("id_tipo_usuario", usuario.getCodigoTipoUsuario());
            usuA.put("nombre_usuario", usuario.getNombreUsuario());
            usuA.put("usuario", usuario.getUsuario());
            usuA.put("contrasenia", usuario.getContrasenia());
            usuA.put("carnet", usuario.getCarnet());
            usuA.put("fecha_registro", usuario.getFecha_registro());
            usuA.put("correo", usuario.getCorreo());

            contador=db.insert("usuario", null, usuA);
            regInsertados=regInsertados+contador;
            //}

            return regInsertados;
        }

    public String insertar(encuesta encuesta){

        String regInsertados="Encuesta registrada exitosamente #= ";
        long contador=0;

        ContentValues encue = new ContentValues();
        //encue.put("id_encuesta", encuesta.getIdEncuesta());
        encue.put("id_usuario", encuesta.getIdUsuario());
        encue.put("id_tipo_encuesta", encuesta.getIdTipoEncuesta());
        encue.put("nombre_encuesta", encuesta.getNombreEncuesta());
        encue.put("fecha_creacion", encuesta.getFechaCreacion());
        encue.put("numero_preguntas", encuesta.getNumeroPreguntas());
        encue.put("limite_intentos", encuesta.getLimiteIntentos());
        encue.put("fecha_inicio", encuesta.getFechaInicio());
        encue.put("fecha_fin", encuesta.getFechaFin());

        contador=db.insert("encuesta", null, encue);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public String insertarEncuestaInicial(encuesta encuesta){

        String regInsertados="Registro Insertado #= ";
        long contador=0;

        ContentValues encue = new ContentValues();
        encue.put("id_encuesta", encuesta.getIdEncuesta());
        encue.put("id_usuario", encuesta.getIdUsuario());
        encue.put("id_tipo_encuesta", encuesta.getIdTipoEncuesta());
        encue.put("nombre_encuesta", encuesta.getNombreEncuesta());
        encue.put("fecha_creacion", encuesta.getFechaCreacion());
        encue.put("id_tipo_encuesta", encuesta.getIdTipoEncuesta());
        encue.put("numero_preguntas", encuesta.getNumeroPreguntas());
        encue.put("limite_intentos", encuesta.getLimiteIntentos());
        encue.put("fecha_inicio", encuesta.getFechaInicio());
        encue.put("fecha_fin", encuesta.getFechaFin());

        contador=db.insert("encuesta", null, encue);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public String insertar(pregunta pregunta){

        String regInsertados="Pregunta registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues preg = new ContentValues();
        preg.put("id_pregunta", pregunta.getIdPregunta());
        preg.put("id_encuesta", pregunta.getIdEncuesta());
        preg.put("id_tipo_pregunta", pregunta.getIdTpoPregunta());
        preg.put("texto_pregunta", pregunta.getTextoPregunta());
        preg.put("es_obligatoria", pregunta.isEsObligatoria());
        preg.put("orden_pregunta", pregunta.getOrdenPregunta());
        preg.put("archivo_multimedia", pregunta.getArchivoMultimedia());


        contador=db.insert("pregunta", null, preg);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(materiaUsuario materiaUsuario){

        String regInsertados="Materia registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues matUsu = new ContentValues();
        matUsu.put("id_materia_usuario", materiaUsuario.getIdMateriaUsuario());
        matUsu.put("id_materia", materiaUsuario.getIdMateria());
        matUsu.put("id_usuario", materiaUsuario.getIdUsuario());

        contador=db.insert("materia_usuario", null, matUsu);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(materia materia){

        String regInsertados="Materia registrada exitosamente #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues mate = new ContentValues();
        mate.put("id_materia", materia.getIdMateria());
        mate.put("nombre_materia", materia.getNombreMateria());
        mate.put("codigo_materia", materia.getCodigoMateria());
        mate.put("ciclo", materia.getCiclo());
        mate.put("anio", materia.getAnio());

        contador=db.insert("materia", null, mate);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(opcionRespuesta opcRespuesta){

        String regInsertados="Opcion ingresada exitosamente #= ";
        long contador=0;

        ContentValues opcresp = new ContentValues();
       // opcresp.put("id_opcion_respuesta", opcRespuesta.getIdOpcionRespuesta());
        opcresp.put("id_pregunta", opcRespuesta.getIdPregunta());
        opcresp.put("texto_respuesta", opcRespuesta.getTextoRespuesta());
        opcresp.put("es_la_correcta", opcRespuesta.isEsLaCorrecta());

        contador=db.insert("opcion_respuesta", null, opcresp);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public String insertar(respuestaUsuario respuestaUsuario){

        String regInsertados="Respuesta ingresada OK #= ";
        long contador=0;

        ContentValues respUsu = new ContentValues();
        //respUsu.put("id_respuesta_usuarios", respuestaUsuario.getIdRespuestaUsuario());
        respUsu.put("id_encuesta", respuestaUsuario.getIdEncuesta());
        respUsu.put("id_pregunta", respuestaUsuario.getIdPregunta());
        respUsu.put("id_usuario", respuestaUsuario.getIdUsuario());
        respUsu.put("numero_intento", respuestaUsuario.getNumeroIntento());
        respUsu.put("fecha_respondida", respuestaUsuario.getFechaRespondido());
        respUsu.put("dispositivo", respuestaUsuario.getDispositivo());
        respUsu.put("es_usuario_anonimo", respuestaUsuario.isEsAnonima());
        respUsu.put("texto_respuesta", respuestaUsuario.getTextoRespuesta());

        contador=db.insert("respuesta_usuarios", null, respUsu);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public String insertarInicial(respuestaUsuario respuestaUsuario){

        String regInsertados="Respuesta ingresada OK #= ";
        long contador=0;

        ContentValues respUsu = new ContentValues();
        respUsu.put("id_respuesta_usuarios", respuestaUsuario.getIdRespuestaUsuario());
        respUsu.put("id_encuesta", respuestaUsuario.getIdEncuesta());
        respUsu.put("id_pregunta", respuestaUsuario.getIdPregunta());
        respUsu.put("id_usuario", respuestaUsuario.getIdUsuario());
        respUsu.put("numero_intento", respuestaUsuario.getNumeroIntento());
        respUsu.put("fecha_respondida", respuestaUsuario.getFechaRespondido());
        respUsu.put("dispositivo", respuestaUsuario.getDispositivo());
        respUsu.put("es_usuario_anonimo", respuestaUsuario.isEsAnonima());
        respUsu.put("texto_respuesta", respuestaUsuario.getTextoRespuesta());

        contador=db.insert("respuesta_usuarios", null, respUsu);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public String llenarDatos(){

        final int[] VTipoEncuesta_id = {1,2};
        final String[] VTipoEncuesta_nombre = {"Encuesta de uso libre","Encuesta Comunidad Universitaria"};

        final int[] VTipoPregunta_id = {1,2,3,4,5};
        final String[] VTipoPregunta_nombre = {"Verdadero o Falso","Seleccion Multiple","Respuesta Corta","Emparejamiento", "Ordenamiento"};

        final int[] VTipoUsuario_id = {1,2,3};
        final String[] VTipoUsuario_nombre = {"Docente","Estudiante","Anonimo"};

        final int[] VUsuario_id =      {1, 2, 3, 4};
        final int[] VUsuario_tipo =      {1, 2, 3, 4};
        final String[] VUsuario_nombre = {"Jose Alberto","Heinrich Sanchez","Mario Rodriguez"};
        final String[] VUsuario_usuario = {"jalberto","hsanchez","mrodriguez"};
        final String[] VUsuario_contra = {"12345","12345","12345"};
        final String[] VUsuario_carnet = {"AA17025","SA04005","RR10056",""};
        final String[] VUsuario_fecha = {"2023-05-28 10:30:00","2023-05-30 13:30:50","2023-05-31 15:30:24",""};
        final String[] VUsuario_correo = {"aa17025@ues.edu.sv","hsanchez.sv@gmail.com","sa04005@ues.edu.sv",""};

        final int[] VEncuesta_id =      {1, 2, 3};
        final int[] VEncuesta_usuario = {1, 2, 1};
        final int[] VEncuesta_tipo =    {2, 1, 2};
        final String[] VEncuesta_nombre = {"Evaluacion PDM115", "Encuesta Estructura de Datos", "Encuesta Historia de El Salvador y CA"};
        final String[] VEncuesta_fecha =  {"2023-05-15 20:00:10", "2023-05-15 20:00:10", "2023-05-15 20:00:10"};
        final boolean[] VEncuesta_estado = {true, true, false};
        final int[] VEncuesta_numpreg = {5, 5, 5};
        final int[] VEncuesta_limite =    {1, 1, 2};
        final String[] VEncuesta_fechaini = {"2023-05-15 20:00:10", "2023-05-15 20:00:10", "2023-05-15 20:00:10"};
        final String[] VEncuesta_fechafin = {"2023-06-30 20:00:10", "2023-05-31 20:00:10", "2023-06-08 20:00:10"};

        final int[] VPregunta_id =      {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        final int[] VPregunta_encu =    {1,1,1,1,1,2,2,2,2,2,3,3,3,3,3};
        final int[] VPregunta_tipo =    {2,2,2,2,2,1,1,1,1,1,2,2,2,2,2};
        final String[] VPregunta_texto = {
            "El ciclo de vida de una aplicacion para dispositivos moviles incluye etapas como la creacion, el inicio, la pausa y la destruccion. \n F o V?",
            "Las bases de datos no relacionales son ampliamente utilizadas en el desarrollo de aplicaciones para dispositivos moviles debido a su flexibilidad y escalabilidad. \n F o V?",
            "¿Cual es el objetivo principal de una base de datos no relacional?",
            "Menciona dos componentes comunes en el desarrollo de apps moviles: ",
            "Cual es la ultima version de Android Desarrollada?",

            "Las Pilas es un tipo abstracto de datos. \n F o V?",
            "Las siglas FIFO, significa: First-Input, First-Output. \n F o V?",
            "Las estructura de datos Cola se define como un Array de datos. \n F o V? ",
            "Un arbol binario es una estructura de datos. \n F o V? ",
            "Un arbol AVL es un arbol binario perfectamente equilibrado. \n F o V?",

            "El pais mas pequeño de Centro America es?",
            "Cuantos municipios tiene El Salvador?",
            "La cabecera departamental de San Salvador es? ",
            "En que año empezo a gobernar Maximiliano Hernandez Martinez? ",
            "La guerra de la Cien Horas se peleo con?"

        };
        final boolean[] VPregunta_es_obligatoria = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
        final int[] VPregunta_orden_preg = {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};
        final String[] VPregunta_blob = {"","","","","","","","","","","","","","",""};

        final int[] VMateria_id =    {1, 2, 3};
        final String[] VMateria_nom = {"Programacion para Dispositios Moviles", "Sistemas Operativos", "Microprogramacion"};
        final String[] VMateria_cod = {"PDM115", "SIO115", "MIP115"};
        final String[] VMateria_cic = {"I2023", "II2023", "I2023"};
        final String[] VMateria_ani = {"2023", "2023", "2023"};

        final int[] VMateUsuario_id = {1, 2, 3, 4, 5};
        final int[] VMateUsuario_mate = {1, 1, 3, 2, 2};
        final int[] VMateUsuario_usua = {2, 1, 3, 1, 4};

        final int[] VOpcionResp_id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final int[] VOpcionResp_pregunta = {1, 1, 2, 2, 3, 3, 3, 3, 4, 5, 5, 5};
        final boolean[] VOpcionResp_escorrecta = {true, false, false, true, false, false, true, false, true, false, false, false};
        final String[] VOpcionResp_texto = {"Falso", "Verdadero", "Falso", "Verdadero", "Almacenar datos en tablas y relaciones.", "Proporcionar una estructura flexible para almacenar datos.", "Garantizar la integridad y consistencia de los datos.", "Permitir consultas complejas mediante el lenguaje SQL.", "Botones, campos de texto.", "Actividad principal", "Fragmento", "Vista"};

        final int[] VRespuestaUsu_id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        final int[] VRespuestaUsu_encuesta = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        final int[] VRespuestaUsu_pregunta = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        final int[] VRespuestaUsu_usuario = {2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1};
        final int[] VRespuestaUsu_intento = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final String[] VRespuestaUsu_fecha = {"2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10", "2023-06-30 20:00:10"};
        final String[] VRespuestaUsu_dispositivo = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        final boolean[] VRespuestaUsu_isanonimo = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        final String[] VRespuestaUsu_texto = {"F", "V", "Integridad Referencial", "Activity, Database", "14", "V", "V", "V", "V", "V", "El Salvador", "262", "San Salvador", "1931", "Honduras"};

        abrir();
        db.execSQL("DELETE FROM tipo_encuesta");
        db.execSQL("DELETE FROM tipo_pregunta");
        db.execSQL("DELETE FROM tipo_respuesta");
        db.execSQL("DELETE FROM tipo_usuario");
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM pregunta");
        db.execSQL("DELETE FROM encuesta");
        db.execSQL("DELETE FROM materia");
        db.execSQL("DELETE FROM materia_usuario");
        db.execSQL("DELETE FROM opcion_respuesta");
        db.execSQL("DELETE FROM respuesta_usuarios");

        tipoEncuesta tipoEnc = new tipoEncuesta();
        for(int i=0;i<2;i++){
            tipoEnc.setIdTipoEncuesta(VTipoEncuesta_id[i]);
            tipoEnc.setNombreTipoEncuesta(VTipoEncuesta_nombre[i]);
            insertar(tipoEnc);
        }

        tipoPregunta tipoPre = new tipoPregunta();
        for(int i=0;i<5;i++){
            tipoPre.setIdTipoPregunta(VTipoPregunta_id[i]);
            tipoPre.setNombreTipoPregunta(VTipoPregunta_nombre[i]);
            insertar(tipoPre);
        }

        tipoUsuario tipoUsu = new tipoUsuario();
        for(int i=0;i<3;i++){
            tipoUsu.setCodigoTipoUsuario(VTipoUsuario_id[i]);
            tipoUsu.setNombreTipoUsuario(VTipoUsuario_nombre[i]);
            insertar(tipoUsu);
        }

        usuario usuario = new usuario();
        for(int i=0;i<3;i++){
            usuario.setCodigUsuario(VUsuario_id[i]);
            usuario.setCodigoTipoUsuario(VUsuario_tipo[i]);
            usuario.setNombreUsuario(VUsuario_nombre[i]);
            usuario.setUsuario(VUsuario_usuario[i]);
            usuario.setContrasenia(VUsuario_contra[i]);
            usuario.setCarnet(VUsuario_carnet[i]);
            usuario.setFecha_registro(VUsuario_fecha[i]);
            usuario.setCorreo(VUsuario_correo[i]);
            insertar(usuario);
        }

        encuesta encu = new encuesta();
        for(int i=0;i<3;i++){
            encu.setIdEncuesta(VEncuesta_id[i]);
            encu.setIdUsuario(VEncuesta_usuario[i]);
            encu.setIdTipoEncuesta(VEncuesta_tipo[i]);
            encu.setNombreEncuesta(VEncuesta_nombre[i]);
            encu.setFechaCreacion(VEncuesta_fecha[i]);
            encu.setEstadoEncuesta(VEncuesta_estado[i]);
            encu.setNumeroPreguntas(VEncuesta_numpreg[i]);
            encu.setLimiteIntentos(VEncuesta_limite[i]);
            encu.setFechaInicio(VEncuesta_fechaini[i]);
            encu.setFechaFin(VEncuesta_fechafin[i]);
            insertarEncuestaInicial(encu);
        }

        pregunta pregu = new pregunta();
        for(int i=0;i<15;i++){
            pregu.setIdPregunta(VPregunta_id[i]);
            pregu.setIdEncuesta(VPregunta_encu[i]);
            pregu.setIdTpoPregunta(VPregunta_tipo[i]);
            pregu.setTextoPregunta(VPregunta_texto[i]);
            pregu.setEsObligatoria(VPregunta_es_obligatoria[i]);
            pregu.setOrdenPregunta(VPregunta_orden_preg[i]);
            pregu.setArchivoMultimedia(VPregunta_blob[i]);
            insertar(pregu);
        }

        materia mate = new materia();
        for(int i=0;i<3;i++){
            mate.setIdMateria(VMateria_id[i]);
            mate.setNombreMateria(VMateria_nom[i]);
            mate.setCodigoMateria(VMateria_cod[i]);
            mate.setCiclo(VMateria_cic[i]);
            mate.setAnio(VMateria_ani[i]);
            insertar(mate);
        }

        materiaUsuario mateUsu = new materiaUsuario();
        for(int i=0;i<5;i++){
            mateUsu.setIdMateriaUsuario(VMateUsuario_id[i]);
            mateUsu.setIdMateria(VMateUsuario_mate[i]);
            mateUsu.setIdUsuario(VMateUsuario_usua[i]);
            insertar(mateUsu);
        }

        opcionRespuesta opcResp = new opcionRespuesta();
        for(int i=0;i<12;i++){
            opcResp.setIdOpcionRespuesta(VOpcionResp_id[i]);
            opcResp.setIdPregunta(VOpcionResp_pregunta[i]);
            opcResp.setTextoRespuesta(VOpcionResp_texto[i]);
            opcResp.setEsLaCorrecta(VOpcionResp_escorrecta[i]);
            insertar(opcResp);
        }

        respuestaUsuario respUsu = new respuestaUsuario();
        for(int i=0;i<15;i++){
            respUsu.setIdRespuestaUsuario(VRespuestaUsu_id[i]);
            respUsu.setIdEncuesta(VRespuestaUsu_encuesta[i]);
            respUsu.setIdPregunta(VRespuestaUsu_pregunta[i]);
            respUsu.setIdUsuario(VRespuestaUsu_usuario[i]);
            respUsu.setNumeroIntento(VRespuestaUsu_intento[i]);
            respUsu.setFechaRespondido(VRespuestaUsu_fecha[i]);
            respUsu.setDispositivo(VRespuestaUsu_dispositivo[i]);
            respUsu.setEsAnonima(VRespuestaUsu_isanonimo[i]);
            respUsu.setTextoRespuesta(VRespuestaUsu_texto[i]);
            insertarInicial(respUsu);
        }

        cerrar();
        return "Guardo Correctamente";
    }

     public usuario consultarUsuario(String carnet) {
        String[] id = {carnet};
        Cursor cursor = db.query("usuario", camposUsuario, "carnet = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            usuario user = new usuario();
            user.setCarnet(cursor.getString(5));
            user.setCorreo(cursor.getString(7));
            user.setNombreUsuario(cursor.getString(2));
            user.setUsuario(cursor.getString(3));
            user.setContrasenia(cursor.getString(4));
            user.setFecha_registro(cursor.getString(6));
            user.setCodigoTipoUsuario(cursor.getInt(1));

            return user;
        }else{
            return null;
        }
     }


    public materia consultarMateria(String codigoMateria) {
        String[] id = {codigoMateria};
        Cursor cursor = db.query("materia", camposMateria, "codigo_materia = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            materia mate = new materia();
            mate.setCodigoMateria(cursor.getString(2));
            mate.setNombreMateria(cursor.getString(1));
            mate.setAnio(cursor.getString(4));
            mate.setCiclo(cursor.getString(3));

            return mate;
        }else{
            return null;
        }
    }

    public encuesta consultarEncuesta(String nomEncuesta){
        if (nomEncuesta != null) {
            String[] id = {nomEncuesta};
            Cursor cursor = db.query("encuesta", camposEncuesta, "nombre_encuesta = ?", id, null, null, null);
            if(cursor.moveToFirst()){
                encuesta encu = new encuesta();
                encu.setIdEncuesta(cursor.getInt(0));
                encu.setIdTipoEncuesta(cursor.getInt(2));
                encu.setNombreEncuesta(cursor.getString(3));
                encu.setFechaCreacion(cursor.getString(4));
                encu.setEstadoEncuesta(true);
                encu.setNumeroPreguntas(cursor.getInt(6));
                encu.setLimiteIntentos(cursor.getInt(7));
                encu.setFechaInicio(cursor.getString(8));
                encu.setFechaFin(cursor.getString(9));

                return encu;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public int consultarNumeroPreguntas(String nomEncuesta) {
        if (nomEncuesta != null) {
            String[] id = {nomEncuesta};
            Cursor cursor = db.query("encuesta", camposEncuesta, "nombre_encuesta = ?", id, null, null, null);
            if (cursor.moveToFirst()) {
                encuesta encu = new encuesta();
                encu.setNumeroPreguntas(cursor.getInt(6));
                return encu.getNumeroPreguntas();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }



    public String actualizar(usuario usuario){

        //if(verificarIntegridad(alumno, 5)){

            String[] id = {usuario.getUsuario()};
            ContentValues cv = new ContentValues();
            cv.put("id_tipo_usuario", usuario.getCodigoTipoUsuario());
            cv.put("nombre_usuario", usuario.getNombreUsuario());
            cv.put("usuario", usuario.getUsuario());
            cv.put("contrasenia", usuario.getContrasenia());
            cv.put("carnet", usuario.getCarnet());
            cv.put("fecha_registro", usuario.getFecha_registro());
            db.update("usuario", cv, "usuario = ?", id);
            return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(encuesta encuesta){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(encuesta.getIdEncuesta())};
        ContentValues cv = new ContentValues();
        cv.put("id_usuario", encuesta.getIdUsuario());
        cv.put("id_tipo_encuesta", encuesta.getIdTipoEncuesta());
        cv.put("nombre_encuesta", encuesta.getNombreEncuesta());
        cv.put("fecha_creacion", encuesta.getFechaCreacion());
        cv.put("id_estado_encuesta", encuesta.getEstadoEncuesta());
        cv.put("numero_preguntas", encuesta.getNumeroPreguntas());
        cv.put("limite_intentos", encuesta.getLimiteIntentos());
        cv.put("fecha_inicio", encuesta.getFechaInicio());
        cv.put("fecha_fin", encuesta.getFechaFin());

        db.update("encuesta", cv, "id_encuesta = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(materia materia){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {materia.getCodigoMateria()};
        ContentValues cv = new ContentValues();
        cv.put("nombre_materia", materia.getNombreMateria());
        cv.put("codigo_materia", materia.getCodigoMateria());
        cv.put("ciclo", materia.getCiclo());
        cv.put("anio", materia.getAnio());

        db.update("materia", cv, "codigo_materia = ?", id);
        return "Registro Actualizado Correctamente";
    }

    public String actualizar(materiaUsuario materiaUsuario){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(materiaUsuario.getIdUsuario())};
        ContentValues cv = new ContentValues();
        cv.put("id_materia", materiaUsuario.getIdMateria());
        cv.put("id_usuario", materiaUsuario.getIdUsuario());


        db.update("materia_usuario", cv, "id_usuario = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(opcionRespuesta opcResp){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(opcResp.getIdOpcionRespuesta())};
        ContentValues cv = new ContentValues();
        cv.put("id_pregunta", opcResp.getIdPregunta());
        cv.put("texto_respuesta", opcResp.getTextoRespuesta());
        cv.put("es_la_correcta", opcResp.isEsLaCorrecta());

        db.update("opcion_respuesta", cv, "id_opcion_respuesta = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(pregunta pregunta){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(pregunta.getIdPregunta())};
        ContentValues cv = new ContentValues();
        cv.put("id_encuesta", pregunta.getIdEncuesta());
        cv.put("id_tipo_pregunta", pregunta.getIdTpoPregunta());
        cv.put("texto_pregunta", pregunta.getTextoPregunta());
        cv.put("es_obligatoria", pregunta.isEsObligatoria());
        cv.put("orden_pregunta", pregunta.getOrdenPregunta());

        db.update("pregunta", cv, "id_pregunta = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(preguntaArea pregAreaEva){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(pregAreaEva.getIdPregunta())};
        ContentValues cv = new ContentValues();
        cv.put("id_area_evaluativa", pregAreaEva.getIdAreaEvaluativa());

        db.update("pregunta_area_evaluativa", cv, "id_pregunta = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }

    public String actualizar(respuestaUsuario respUsu){

        //if(verificarIntegridad(alumno, 5)){

        String[] id = {String.valueOf(respUsu.getIdRespuestaUsuario())};
        ContentValues cv = new ContentValues();
        cv.put("id_encuesta", respUsu.getIdEncuesta());
        cv.put("id_pregunta", respUsu.getIdPregunta());
        cv.put("id_usuario", respUsu.getIdUsuario());
        cv.put("numero_intento", respUsu.getNumeroIntento());
        cv.put("fecha_respondida", respUsu.getFechaRespondido());
        cv.put("dispositivo", respUsu.getDispositivo());
        cv.put("es_usuario_anonimo", respUsu.isEsAnonima());

        db.update("respuesta_usuarios", cv, "id_respuesta_usuarios = ?", id);
        return "Registro Actualizado Correctamente";

            /*}else{
            return "Registro con carnet " + alumno.getCarnet() + " no existe";
        }*/
    }



    public String eliminar(usuario usuario){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/

        contador+=db.delete("usuario", "carnet='"+usuario.getCarnet()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(materia materia){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/
        contador+=db.delete("materia", "codigo_materia='"+materia.getCodigoMateria()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(materiaUsuario mateUsu){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/
        contador+=db.delete("materia_usuario", "id_materia_usuario='"+mateUsu.getIdMateriaUsuario()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(opcionRespuesta opcResp){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/
        contador+=db.delete("opcion_respuesta", "id_opcion_respuesta='"+opcResp.getIdOpcionRespuesta()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(pregunta pregunta){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/
        contador+=db.delete("pregunta", "id_pregunta='"+pregunta.getIdPregunta()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(respuestaUsuario respUsu){

        String regAfectados="filas afectadas= ";
        int contador=0;
        /*if (verificarIntegridad(usuario,3)) {
            contador+=db.delete("nota", "carnet='"+usuario.getCarnet()+"'", null);
        }*/
        contador+=db.delete("respuesta_usuarios", "id_repuesta_usuarios='"+respUsu.getIdRespuestaUsuario()+"'", null);
        regAfectados+=contador;
        return regAfectados;
    }

    public String eliminar(encuesta encuesta){
        // Eliminacion en cascada, preguntas primero
        String regAfectadosp="Preguntas eliminadas = ";
        int contadorp=0;

        contadorp+=db.delete("pregunta", "id_encuesta='"+encuesta.getIdEncuesta()+"'", null);
        regAfectadosp+=contadorp;

        String regAfectados="Encuestas eliminadas = ";
        long contador=0;

        contador+=db.delete("encuesta", "id_encuesta='"+encuesta.getIdEncuesta()+"'", null);
        regAfectados+=contador;
        return regAfectadosp + regAfectados;

    }

    public String insertarU(usuario usuario){

        String regInsertados="Registro Insertado #= ";
        long contador=0;

        ContentValues usuA = new ContentValues();
        usuA.put("id_usuario", codigoUs());
        usuA.put("id_tipo_usuario", usuario.getCodigoTipoUsuario());
        usuA.put("nombre_usuario", usuario.getNombreUsuario());
        usuA.put("usuario", usuario.getUsuario());
        usuA.put("contrasenia", usuario.getContrasenia());
        usuA.put("carnet", usuario.getCarnet());
        usuA.put("fecha_registro", usuario.getFecha_registro());
        usuA.put("correo", usuario.getCorreo());

        contador=db.insert("usuario", null, usuA);
        regInsertados=regInsertados+contador;

        return regInsertados;
    }

    public int codigoUs() {
        int resp = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM usuario", null);
        if(cursor.moveToFirst()){
            resp = cursor.getInt(0)+1;
        }
        return resp;
    }

    public int codigoPreg() {
        int resp = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM pregunta", null);
        if(cursor.moveToFirst()){
            resp = cursor.getInt(0)+1;
        }
        return resp;
    }

    public int codigoEn(String nom) {
        String[] id = {nom};
        int resp = 0;
        Cursor cursor = db.rawQuery("SELECT id_encuesta FROM encuesta WHERE nombre_encuesta = ?", id);
        if(cursor.moveToFirst()){
            resp = cursor.getInt(0);
        }
        return resp;
    }

    public Cursor mostrarEncuestasSP(){
        try{
            Cursor filas = db.rawQuery("select id_encuesta,nombre_encuesta FROM encuesta",null);
            //Cursor filas = db.rawQuery("SELECT id_encuesta,nombre_encuesta FROM encuesta",null);
            if (filas.moveToFirst())
                return  filas;
            else
                return  null;
        }catch (Exception e){
            return null;
        }
    }
    public Cursor mostrarUsuarios(){
        try{
            Cursor filas = db.rawQuery("select carnet FROM usuario",null);
            if (filas.moveToFirst())
                return  filas;
            else
                return  null;
        }catch (Exception e){
            return null;
        }
    }

    public Cursor mostrarMaterias(){
        try{
            Cursor filas = db.rawQuery("select codigo_materia FROM materia",null);
            if (filas.moveToFirst())
                return  filas;
            else
                return  null;
        }catch (Exception e){
            return null;
        }
    }


    public pregunta consultarPreguntas(int idEncuesta){

        //SQLiteDatabase db = this.getReadableDatabase();
        String[] id = {String.valueOf(idEncuesta)};
        Cursor cursor = db.query("pregunta", camposPregunta, "id_encuesta = ?",id,null, null, null);
        if (cursor.moveToFirst()){
            pregunta pre = new pregunta();
            pre.setIdPregunta(cursor.getInt(0));
            pre.setIdEncuesta(cursor.getInt(1));
            pre.setIdTpoPregunta(cursor.getInt(2));
            pre.setTextoPregunta(cursor.getString(3));
            //pre.setEsObligatoria(preguntas.(4));
            pre.setOrdenPregunta(cursor.getInt(5));
            pre.setArchivoMultimedia(cursor.getString(6));

            return pre;
        } else {
            return null;
        }
    }

    public Cursor obtenerPreguntas(int idEncuesta){
        try{
            String[] id = {String.valueOf(idEncuesta)};
            Cursor filas = db.rawQuery("select * FROM pregunta where id_encuesta = ?",id);
            if (filas.moveToFirst())
                return  filas;
            else
                return  null;
        }catch (Exception e){
            return null;
        }
    }

    public String getDatePhone()

    {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        String formatteDate = df.format(date);
        return formatteDate;
    }
}