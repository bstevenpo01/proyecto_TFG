package com.example.sportmotor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sportmotor.controlador.MotosFirebaseController;
import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.modelo.Moto;
import com.example.sportmotor.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

public class Add_moto extends AppCompatActivity {
    EditText edt_add_nbastidorm, edt_add_marcam, edt_add_modelom, edt_add_preciom;
    ImageView img_add_foto;
    private FirebaseAuth mAuth;
    double preciom;

    Moto m;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_selecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moto);
        edt_add_nbastidorm = (EditText) findViewById(R.id.edt_add_nbastidorm);
        edt_add_marcam = (EditText) findViewById(R.id.edt_add_marcam);
        edt_add_modelom = (EditText) findViewById(R.id.edt_add_modelom);
        edt_add_preciom = (EditText) findViewById(R.id.edt_add_preciom);
        img_add_foto = (ImageView) findViewById(R.id.img_add_fotom);
        mAuth = FirebaseAuth.getInstance();
    }

    public void insertar_moto(View view) {
        String nbastidorm = String.valueOf(edt_add_nbastidorm.getText());
        String marcam = String.valueOf(edt_add_marcam.getText());
        String modelom = String.valueOf(edt_add_modelom.getText());
        //double preciom = Double.valueOf(String.valueOf(edt_add_preciom.getText()));
        try {
            preciom = Double.valueOf(String.valueOf(edt_add_preciom.getText()));
        }catch (Exception e){
            preciom = 0;
        }
        if (nbastidorm.isEmpty()){
            Toast.makeText(this, "verifique el numero de bastidor", Toast.LENGTH_SHORT).show();
            return;
        } else if (marcam.isEmpty()){
            Toast.makeText(this, "verifique la marca", Toast.LENGTH_SHORT).show();
            return;
        } else if (modelom.isEmpty()){
            Toast.makeText(this, "verifique el modelo", Toast.LENGTH_SHORT).show();
            return;
        }else if (preciom == 0){
            Toast.makeText(this, "verifique el precio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imagen_selecionada != null){
            String email = mAuth.getCurrentUser().getEmail();
            new ImagenesFirebase().subirFoto(new ImagenesFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {

                }

                @Override
                public void FotoIsUpload() {
                    Toast.makeText(Add_moto.this,"foto subida correcta",Toast.LENGTH_LONG).show();

                }

                @Override
                public void FotoIsDelete() {

                }
            },email, nbastidorm, img_add_foto);

            m = new Moto(nbastidorm,marcam,modelom,preciom,email+"/"+String.valueOf(nbastidorm)+".png");
        }else{
            m = new Moto(nbastidorm,marcam,modelom,preciom, null);
        }
        new MotosFirebaseController().insertarMoto(new MotosFirebaseController.MotosStatus() {
            @Override
            public void motosIsLoaded(List<Moto> motos, List<String> keys) {

            }

            @Override
            public void motoIsAdd() {
                Toast.makeText(Add_moto.this, "moto insertada correctamente", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void motoIsUpdate() {

            }

            @Override
            public void motoIsDelete() {

            }
        },m);
    }

    public void cambiar_imagenm(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_selecionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_selecionada);
                img_add_foto.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}