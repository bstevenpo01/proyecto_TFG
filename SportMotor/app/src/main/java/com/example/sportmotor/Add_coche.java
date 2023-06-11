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

import com.example.sportmotor.controlador.CochesFirebaseController;
import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.utilidades.ImagenesFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

public class Add_coche extends AppCompatActivity {

    EditText edt_add_nbastidor, edt_add_marca, edt_add_modelo, edt_add_precio;
    ImageView img_add_foto;
    private FirebaseAuth mAuth;
    double precio = 0;

    Coche c;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_selecionada = null;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(Add_coche.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(Add_coche.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coche);
        edt_add_nbastidor = (EditText) findViewById(R.id.edt_add_nbastidor);
        edt_add_marca = (EditText) findViewById(R.id.edt_add_marca);
        edt_add_modelo = (EditText) findViewById(R.id.edt_add_modelo);
        edt_add_precio = (EditText) findViewById(R.id.edt_add_precio);
        img_add_foto = (ImageView) findViewById(R.id.img_add_foto);
        mAuth = FirebaseAuth.getInstance();
    }


    public void insertar_coche(View view) {
        String nbastidor = String.valueOf(edt_add_nbastidor.getText());
        String marca = String.valueOf(edt_add_marca.getText());
        String modelo = String.valueOf(edt_add_modelo.getText());
        //double precio = Double.valueOf(String.valueOf(edt_add_precio.getText()));
        try {
            precio = Double.valueOf(String.valueOf(edt_add_precio.getText()));
        }catch (Exception e){
            precio = 0;
        }
        if (nbastidor.isEmpty()){
            Toast.makeText(this, "verifique el numero de bastidor", Toast.LENGTH_SHORT).show();
            return;
        } else if (marca.isEmpty()){
            Toast.makeText(this, "verifique la marca", Toast.LENGTH_SHORT).show();
            return;
        } else if (modelo.isEmpty()){
            Toast.makeText(this, "verifique el modelo", Toast.LENGTH_SHORT).show();
            return;
        }else if (precio == 0){
            Toast.makeText(this, "verifique el precio", Toast.LENGTH_SHORT).show();
            return;
        }

        if(imagen_selecionada != null)
        {
            String email =  mAuth.getCurrentUser().getEmail();
            new ImagenesFirebase().subirFoto(new ImagenesFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                }
                @Override
                public void FotoIsDelete() {
                }
                @Override
                public void FotoIsUpload() {
                    Toast.makeText(Add_coche.this,"foto subida correcta",Toast.LENGTH_LONG).show();
                }
            },email, nbastidor, img_add_foto);

            c = new Coche(nbastidor,marca, modelo, precio,email+"/"+String.valueOf(nbastidor)+".png");
        }else{
            c = new Coche(nbastidor,marca,modelo,precio, null);
        }
        new CochesFirebaseController().insertarCoche(new CochesFirebaseController.CochesStatus() {
            @Override
            public void cochesIsLoaded(List<Coche> coches, List<String> keys) {

            }

            @Override
            public void cocheIsAdd() {
                Toast.makeText(Add_coche.this,"coche insertado correctamente", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void cocheIsUpdate() {

            }

            @Override
            public void cocheIsDelete() {

            }
        },c);
    }

    public void cambiar_imagen(View view) {
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