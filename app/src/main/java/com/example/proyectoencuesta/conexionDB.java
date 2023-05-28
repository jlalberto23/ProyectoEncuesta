package com.example.proyectoencuesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class conexionDB {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
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
                db.execSQL("CREATE TABLE area_evaluativa (id_area_evaluativa   integer not null, id_encuesta          integer, nombre_area          char(256), orden_numerico       integer, primary key (id_area_evaluativa AUTOINCREMENT), foreign key (id_encuesta) references encuesta (id_encuesta) );");
                db.execSQL("CREATE TABLE encuesta (id_encuesta  integer  not null, id_usuario  integer, id_tipo_encuesta     integer              not null, nombre_encuesta      char(256), fecha_creacion       timestamp, id_estado_encuesta   integer, numero_preguntas     integer, limite_intentos      integer, fecha_inicio         timestamp, fecha_fin            timestamp, primary key (id_encuesta AUTOINCREMENT), foreign key (id_usuario) references usuario (id_usuario), foreign key (id_tipo_encuesta) references tipo_encuesta (id_tipo_encuesta) );");
                db.execSQL("CREATE TABLE materia (id_materia integer  not null, nombre_materia  char(50), codigo_materia       char(20), ciclo                char(20), anio                 char(4), primary key (id_materia AUTOINCREMENT));");
                db.execSQL("CREATE TABLE materia_usuario (id_materia_usuario INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, [id_materia] INTEGER  NOT NULL, [id_usuario] INTEGER  NOT NULL );");
                db.execSQL("CREATE TABLE opcion_respuesta (id_opcion_respuesta integer not null, id_pregunta integer, texto_respuesta char(256), es_la_correcta smallint, primary key (id_opcion_respuesta AUTOINCREMENT));");
                db.execSQL("CREATE TABLE pregunta (id_pregunta  integer  not null, id_encuesta          integer, id_tipo_pregunta     integer, texto_pregunta       char(256), es_obligatoria       smallint, orden_pregunta       integer, primary key (id_pregunta AUTOINCREMENT), foreign key (id_encuesta) references encuesta (id_encuesta), foreign key (id_tipo_pregunta) references tipo_pregunta (id_tipo_pregunta) );");
                db.execSQL("CREATE TABLE pregunta_area_evaluativa (id_pregunta  integer  not null, id_area_evaluativa   integer              not null, primary key (id_pregunta, id_area_evaluativa), foreign key (id_pregunta) references pregunta (id_pregunta), foreign key (id_area_evaluativa) references area_evaluativa (id_area_evaluativa) );");
                db.execSQL("CREATE TABLE respuesta_usuarios (id_respuesta_usuarios integer  not null, id_opcion_respuesta  integer, id_usuario           integer, numero_intento       integer, fecha_respondida     date, dispositivo          char(256), es_usuario_anonimo   smallint, primary key (id_respuesta_usuarios AUTOINCREMENT), foreign key (id_usuario) references usuario (id_usuario), foreign key (id_opcion_respuesta) references opcion_respuesta (id_opcion_respuesta) );");
                db.execSQL("CREATE TABLE tipo_encuesta (id_tipo_encuesta     integer  not null, nombre_tipo_encuesta char(100), primary key (id_tipo_encuesta AUTOINCREMENT) );");
                db.execSQL("CREATE TABLE tipo_pregunta (id_tipo_pregunta     integer not null, nombre_tipo_pregunta char(100), primary key (id_tipo_pregunta AUTOINCREMENT) );");
                db.execSQL("CREATE TABLE tipo_respuesta (id_tipo_respuesta    integer not null, nombre_tipo_respuesta char(256), primary key (id_tipo_respuesta AUTOINCREMENT) );");
                db.execSQL("CREATE TABLE tipo_usuario (id_tipo_usuario      integer not null, nombre_tipo_usuario  char(256), primary key (id_tipo_usuario AUTOINCREMENT) );");
                db.execSQL("CREATE TABLE usuario (id_usuario  integer  not null, id_tipo_usuario      integer, nombre_usuario       char(100), usuario             char(100), contrasenia             char(100), carnet               char(100), fecha_registro       TIMESTAMP, primary key (id_usuario AUTOINCREMENT), foreign key (id_tipo_usuario) references tipo_usuario (id_tipo_usuario) );");


            }catch(SQLException e){

                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }


    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }

    public String insertar(tipoEncuesta tipoEncuesta){

        String regInsertados="Registro Insertado #= ";
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

        String regInsertados="Registro Insertado #= ";
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

        String regInsertados="Registro Insertado #= ";
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

        String regInsertados="Registro Insertado #= ";
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

        contador=db.insert("usuario", null, usuA);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(encuesta encuesta){

        String regInsertados="Registro Insertado #= ";
        long contador=0;
        /*if (verificarIntegridad(alumno,5)) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado(PK). Verificar inserción";
        }
        else
        {*/
        ContentValues encue = new ContentValues();
        encue.put("id_encuesta", encuesta.getIdEncuesta());
        encue.put("id_usuario", encuesta.getIdUsuario());
        encue.put("id_tipo_encuesta", encuesta.getIdTipoEncuesta());
        encue.put("nombre_encuesta", encuesta.getNombreEncuesta());
        encue.put("fecha_creacion", encuesta.getFechaCreacion());
        encue.put("id_estado_encuesta", encuesta.getEstadoEncuesta());
        encue.put("numero_preguntas", encuesta.getNumeroPreguntas());
        encue.put("limite_intentos", encuesta.getLimiteIntentos());
        encue.put("fecha_inicio", encuesta.getFechaInicio());
        encue.put("fecha_fin", encuesta.getFechaFin());

        contador=db.insert("encuesta", null, encue);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(pregunta pregunta){

        String regInsertados="Registro Insertado #= ";
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


        contador=db.insert("pregunta", null, preg);
        regInsertados=regInsertados+contador;
        //}

        return regInsertados;
    }

    public String insertar(materiaUsuario materiaUsuario){

        String regInsertados="Registro Insertado #= ";
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

        String regInsertados="Registro Insertado #= ";
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

    public String llenarDatos(){

        final int[] VTipoEncuesta_id = {1,2};
        final String[] VTipoEncuesta_nombre = {"Encuesta de uso libre","Encuesta Comunidad Universitaria"};

        final int[] VTipoPregunta_id = {1,2,3,4,5};
        final String[] VTipoPregunta_nombre = {"Verdadero o Falso","Seleccion Multiple","Respuesta Corta","Emparejamiento", "Ordenamiento"};

        final int[] VTipoUsuario_id = {1,2,3};
        final String[] VTipoUsuario_nombre = {"Docente","Estudiante","Anonimo"};

        final int[] VUsuario_id =      {1, 2, 3, 4, 5};
        final int[] VUsuario_tipo =      {1, 1, 2, 2, 3};
        final String[] VUsuario_nombre = {"Jose Alberto","Carlos Orellana","Heinrich Sanchez","Mario Rodriguez",""};
        final String[] VUsuario_usuario = {"jalberto","corellana","hsanchez","mrodriguez",""};
        final String[] VUsuario_contra = {"12345","12345","12345","12345",""};
        final String[] VUsuario_carnet = {"AA17025","OM17034","SA04005","RR10056",""};
        final String[] VUsuario_fecha = {"2023-05-28 10:30:00","2023-05-29 12:30:05","2023-05-30 13:30:50","2023-05-31 15:30:24",""};

        final int[] VEncuesta_id =      {1, 2, 3};
        final int[] VEncuesta_usuario = {1, 2, 1};
        final int[] VEncuesta_tipo =    {2, 2, 1};
        final String[] VEncuesta_nombre = {"Evaluacion PDM115", "Encuesta Estructura de Datos", "Encuesta Historia de El Salvador y CA"};
        final String[] VEncuesta_fecha =  {"2023-05-15 20:00:10", "2023-05-15 20:00:10", "2023-05-15 20:00:10"};
        final boolean[] VEncuesta_estado = {true, true, false};
        final int[] VEncuesta_numpreg = {10, 5, 3};
        final int[] VEncuesta_limite =    {1, 1, 2};
        final String[] VEncuesta_fechaini = {"2023-05-15 20:00:10", "2023-05-15 20:00:10", "2023-05-15 20:00:10"};
        final String[] VEncuesta_fechafin = {"2023-06-30 20:00:10", "2023-05-31 20:00:10", "2023-06-08 20:00:10"};

        final int[] VPregunta_id =      {1, 2, 3, 4, 5};
        final int[] VPregunta_encu =    {1,1,1,1,1};
        final int[] VPregunta_tipo =    {1,1,2,3,5};
        final String[] VPregunta_texto = {"El ciclo de vida de una aplicacion para dispositivos moviles incluye etapas como la creacion, el inicio, la pausa y la destruccion.",
                                      "Las bases de datos no relacionales son ampliamente utilizadas en el desarrollo de aplicaciones para dispositivos moviles debido a su flexibilidad y escalabilidad.",
                                      "¿Cual es el objetivo principal de una base de datos no relacional?",
                                      "Menciona dos componentes comunes en el desarrollo de apps moviles.",
                                      "Ordena los siguientes componentes de una aplicacion movil segun su jerarquia visual, de arriba hacia abajo:"

                                    };
        final boolean[] VPregunta_es_obligatoria = {true,true,true,true,false};
        final int[] VPregunta_orden_preg = {1,2,3,4,5};

        final int[] VMateria_id =    {1, 2, 3};
        final String[] VMateria_nom = {"Programacion para Dispositios Moviles", "Sistemas Operativos", "Microprogramacion"};
        final String[] VMateria_cod = {"PDM115", "SIO115", "MIP115"};
        final String[] VMateria_cic = {"I2023", "II2023", "I2023"};
        final String[] VMateria_ani = {"2023", "2023", "2023"};

        final int[] VMateUsuario_id =    {1, 2, 3, 4, 5};
        final int[] VMateUsuario_mate =    {1, 1, 3, 2, 2};
        final int[] VMateUsuario_usua =    {2, 1, 3, 1, 4};

        abrir();
        db.execSQL("DELETE FROM tipo_encuesta");
        db.execSQL("DELETE FROM tipo_pregunta");
        db.execSQL("DELETE FROM tipo_respuesta");
        db.execSQL("DELETE FROM tipo_usuario");
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM encuesta");
        db.execSQL("DELETE FROM pregunta");
        db.execSQL("DELETE FROM materia");
        db.execSQL("DELETE FROM materia_usuario");

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
        for(int i=0;i<5;i++){
            usuario.setCodigUsuario(VUsuario_id[i]);
            usuario.setCodigoTipoUsuario(VUsuario_tipo[i]);
            usuario.setNombreUsuario(VUsuario_nombre[i]);
            usuario.setUsuario(VUsuario_usuario[i]);
            usuario.setContrasenia(VUsuario_contra[i]);
            usuario.setCarnet(VUsuario_carnet[i]);
            usuario.setFecha_registro(VUsuario_fecha[i]);
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

            insertar(encu);
        }

        pregunta pregu = new pregunta();
        for(int i=0;i<5;i++){
            pregu.setIdPregunta(VPregunta_id[i]);
            pregu.setIdEncuesta(VPregunta_encu[i]);
            pregu.setIdTpoPregunta(VPregunta_tipo[i]);
            pregu.setTextoPregunta(VPregunta_texto[i]);
            pregu.setEsObligatoria(VPregunta_es_obligatoria[i]);
            pregu.setOrdenPregunta(VPregunta_orden_preg[i]);

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

        cerrar();
        return "Guardo Correctamente";
    }
}