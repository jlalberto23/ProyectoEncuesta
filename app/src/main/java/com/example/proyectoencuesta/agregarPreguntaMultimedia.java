package com.example.proyectoencuesta;

import static android.view.View.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.MediaController;

public class agregarPreguntaMultimedia  extends Activity {

    conexionDB cn;
    int numP;
    String nom;
    TextView nombre, numeroPreg;
    EditText pregunta;
    Button next, guardar;

    // One Button
    Button BSelectImage;
    Button BSelectVideo;

    // One Preview Image
    ImageView IVPreviewImage;
    VideoView IVPreviewVideo;

    String multimediaURI;
    int tipoArchivo;
    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    int SELECT_VIDEO = 400;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_pregunta_multimedia);
        cn = new conexionDB(this);
        guardar = findViewById(R.id.btnsave);
        next = findViewById(R.id.sigbtn);
        guardar.setVisibility(INVISIBLE);
        nombre = findViewById(R.id.lblNombreEnc);
        pregunta = findViewById(R.id.txtPreguntaCort);
        numeroPreg = findViewById(R.id.lblNumeroPreg);

        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        BSelectVideo = findViewById(R.id.BSelectVideo);
        IVPreviewVideo = findViewById(R.id.IVPreviewVideo);

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(onclic);
        BSelectVideo.setOnClickListener(onclic);

        try{
            Bundle extra = getIntent().getExtras();
            if(extra.getString("numP")!=null || extra.getString("nomEn")!=null)
            {
                numP = Integer.valueOf(extra.getString("numP"));
                nom = extra.getString("nomEn");
                nombre.setText(nom);
                next = findViewById(R.id.sigbtn);
            }
            else if(extra.getString("cantP")!=null || extra.getString("nom")!=null){
                numP = Integer.valueOf(extra.getString("cantP"));
                nom = extra.getString("nom");
                nombre.setText(nom);
                next = findViewById(R.id.sigbtn);
            }
            if (numP == 1) {
                guardar.setVisibility(VISIBLE);
                next.setVisibility(INVISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        numeroPreg.setText("Pregunta numero " + numP + ":");
        next.setOnClickListener(onclic);
        guardar.setOnClickListener(onclic);


    }

    OnClickListener onclic = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                String res;
                pregunta p = new pregunta();
                switch (view.getId()){
                    case R.id.sigbtn:
                        cn.abrir();
                        p.setIdPregunta(cn.codigoPreg());
                        p.setIdEncuesta(cn.codigoEn(nom));
                        p.setIdTpoPregunta(2);
                        p.setTextoPregunta(pregunta.getText().toString());
                        p.setEsObligatoria(false);
                        p.setOrdenPregunta(numP);
                       // p.setArchivoMultimedia(multimediaURI);
                        p.setTipoArchivo(tipoArchivo);
                        p.setRutaArchivo(multimediaURI);


                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_SHORT).show();
                        int c = numP-1;
                        Intent i = new Intent(view.getContext(),agregarPreguntaMultimedia.class);
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
                       // p.setArchivoMultimedia(multimediaURI);
                        p.setTipoArchivo(tipoArchivo);
                        p.setRutaArchivo(multimediaURI);


                        res = cn.insertar(p);
                        Toast.makeText(view.getContext(), res, Toast.LENGTH_LONG).show();
                        Intent in = new Intent(view.getContext(),vistaDocente.class);
                        startActivity(in);
                        break;
                    case R.id.BSelectImage:
                        imageChooser();
                        break;
                    case R.id.BSelectVideo:
                        Intent iV = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(iV, SELECT_VIDEO);
                        break;
                }
            }catch (Exception e){ e.printStackTrace(); }
        }
    };

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Imagen"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                    System.out.println("IMAGE-URI" + selectedImageUri);
                    this.multimediaURI = selectedImageUri.toString();
                    this.tipoArchivo = 1;
                }

                IVPreviewImage.setVisibility(VISIBLE);
                IVPreviewVideo.setVisibility(INVISIBLE);
            }
            if (requestCode == SELECT_VIDEO) {
                // Get the url of the video from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview video in the layout
                    Uri video = data.getData();
                    IVPreviewVideo.setVideoURI(video);
                    System.out.println("VIDEO-URI" + selectedImageUri);
                    this.multimediaURI = selectedImageUri.toString();
                    this.tipoArchivo = 2;

                    MediaController mediaController = new MediaController(this);
                    mediaController.setAnchorView(IVPreviewVideo);
                    mediaController.setMediaPlayer(IVPreviewVideo);

                    IVPreviewVideo.setMediaController(mediaController);
                    IVPreviewVideo.start();
                }
                IVPreviewVideo.setVisibility(VISIBLE);
                IVPreviewImage.setVisibility(INVISIBLE);
            }
        }
    }
}
