package com.example.sportmotor;

import static com.example.sportmotor.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sportmotor.controlador.MotosFirebaseController;
import com.example.sportmotor.modelo.Configuracion;
import com.example.sportmotor.modelo.Moto;
import com.example.sportmotor.modelo.MotoViewHolder;
import com.example.sportmotor.utilidades.ImagenesFirebase;

import java.util.List;

public class MostrarDetallesMoto extends AppCompatActivity {
    private EditText edt_detalles_nbastidorm, edt_detalles_marcam, edt_detalles_modelom, edt_detalles_preciom;
    private ImageView img_detalles_moto;
    private  String key;
    private Moto m;
    double preciom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles_moto);
        edt_detalles_nbastidorm = (EditText) findViewById(R.id.edt_detalles_nbastidorm);
        edt_detalles_marcam = (EditText) findViewById(R.id.edt_detalles_marcam);
        edt_detalles_modelom = (EditText) findViewById(R.id.edt_detalles_modelom);
        edt_detalles_preciom = (EditText) findViewById(R.id.edt_detalles_preciom);
        img_detalles_moto = (ImageView) findViewById(R.id.img_detalles_moto);
        Intent intent = getIntent();
        if (intent != null){
            m = (Moto) intent.getSerializableExtra(MotoViewHolder.EXTRA_OBJETO_MOTO);
            key = intent.getStringExtra(MotoViewHolder.EXTRA_OBJETO_MOTO_KEY);
            edt_detalles_nbastidorm.setText(m.getNbastidorm());
            edt_detalles_marcam.setText(m.getMarcam());
            edt_detalles_modelom.setText(m.getModelom());
            edt_detalles_preciom.setText(String.valueOf(m.getPreciom()));
            if (m.getFoto() != null){
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap foto = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            img_detalles_moto.setImageBitmap(foto);
                        }
                        else{
                            Log.i("firebasedb","foto no descargada correctamente");
                        }
                    }

                    @Override
                    public void FotoIsUpload() {

                    }

                    @Override
                    public void FotoIsDelete() {

                    }
                }, m.getFoto());
            }
        }
    }

    public void borrar_moto(View view) {
        new MotosFirebaseController().borrarMoto(new MotosFirebaseController.MotosStatus() {
            @Override
            public void motosIsLoaded(List<Moto> motos, List<String> keys) {

            }

            @Override
            public void motoIsAdd() {

            }

            @Override
            public void motoIsUpdate() {

            }

            @Override
            public void motoIsDelete() {
                Toast.makeText(MostrarDetallesMoto.this, "borrado correctamente", Toast.LENGTH_LONG).show();
                finish();

            }
        },key);
        /*new ImagenesFirebase().borrarFoto(new ImagenesFirebase.FotoStatus() {
            @Override
            public void FotoIsDownload(byte[] bytes) {

            }

            @Override
            public void FotoIsUpload() {
                Toast.makeText(MostrarDetallesMoto.this, "imagen eliminada correctamente", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void FotoIsDelete() {

            }
        },m.getFoto());

         */
    }

    public void actualizar_moto(View view) {
        String nbastidorm = String.valueOf(edt_detalles_nbastidorm.getText());
        String marcam = String.valueOf(edt_detalles_marcam.getText());
        String modelom = String.valueOf(edt_detalles_modelom.getText());
        //Double preciom = Double.valueOf(String.valueOf(edt_detalles_preciom.getText()));
        try {
            preciom = Double.valueOf(String.valueOf(edt_detalles_preciom.getText()));
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
        Moto m = new Moto(nbastidorm, marcam, modelom, preciom);
        new  MotosFirebaseController().actualizarMoto(new MotosFirebaseController.MotosStatus() {
            @Override
            public void motosIsLoaded(List<Moto> motos, List<String> keys) {

            }

            @Override
            public void motoIsAdd() {

            }

            @Override
            public void motoIsUpdate() {
                Toast.makeText(MostrarDetallesMoto.this, "actualizado corectamente", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void motoIsDelete() {

            }
        },key,m);
    }
}