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

import com.example.sportmotor.controlador.CochesFirebaseController;
import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.modelo.CocheViewHolder;
import com.example.sportmotor.modelo.Configuracion;
import com.example.sportmotor.utilidades.ImagenesFirebase;

import java.util.List;

public class MostrarDetallesCoche extends AppCompatActivity {
    private EditText edt_detalles_nbastidor, edt_detalles_marca, edt_detalles_modelo, edt_detalles_precio;
    private ImageView img_detalles_coche;
    private  String key;
    private Coche c;
    double precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles_coche);
        edt_detalles_nbastidor = (EditText) findViewById(R.id.edt_detalles_nbastidor);
        edt_detalles_marca = (EditText) findViewById(R.id.edt_detalles_marca);
        edt_detalles_modelo = (EditText) findViewById(R.id.edt_detalles_modelo);
        edt_detalles_precio = (EditText) findViewById(R.id.edt_detalles_precio);
        img_detalles_coche = (ImageView) findViewById(R.id.img_detalles_coche);
        Intent intent = getIntent();
        if (intent != null){
            c = (Coche) intent.getSerializableExtra(CocheViewHolder.EXTRA_OBJETO_COCHE);
            key = intent.getStringExtra(CocheViewHolder.EXTRA_OBJETO_COCHE_KEY);
            edt_detalles_nbastidor.setText(c.getNbastidor());
            edt_detalles_marca.setText(c.getMarca());
            edt_detalles_modelo.setText(c.getModelo());
            edt_detalles_precio.setText(String.valueOf(c.getPrecio()));
            if (c.getFoto() != null){
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if(bytes != null) {
                            Log.i("firebasedb","foto descargada correctamente");
                            Bitmap foto = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            img_detalles_coche.setImageBitmap(foto);
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
                },c.getFoto());
            }
        }
    }

    public void borrar_coche(View view) {
        new CochesFirebaseController().borrarCoche(new CochesFirebaseController.CochesStatus() {
            @Override
            public void cochesIsLoaded(List<Coche> coches, List<String> keys) {

            }

            @Override
            public void cocheIsAdd() {

            }

            @Override
            public void cocheIsUpdate() {

            }

            @Override
            public void cocheIsDelete() {
                Toast.makeText(MostrarDetallesCoche.this, "borrado correctamente", Toast.LENGTH_LONG).show();
                finish();

            }
        }, key);
        /*
        new  ImagenesFirebase().borrarFoto(new ImagenesFirebase.FotoStatus() {
            @Override
            public void FotoIsDownload(byte[] bytes) {

            }

            @Override
            public void FotoIsUpload() {


            }

            @Override
            public void FotoIsDelete() {
                Toast.makeText(MostrarDetallesCoche.this, "imagen eliminada correctamente", Toast.LENGTH_LONG).show();
                finish();

            }
        },c.getFoto());

         */
    }

    public void actualizar_coche(View view) {
        String nbastidor = String.valueOf(edt_detalles_nbastidor.getText());
        String marca = String.valueOf(edt_detalles_marca.getText());
        String modelo = String.valueOf(edt_detalles_modelo.getText());
        //Double precio = Double.valueOf(String.valueOf(edt_detalles_precio.getText()));
        try {
            precio = Double.valueOf(String.valueOf(edt_detalles_precio.getText()));
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
        Coche c = new Coche(nbastidor, marca, modelo, precio);
        new  CochesFirebaseController().actualizarCoche(new CochesFirebaseController.CochesStatus() {
            @Override
            public void cochesIsLoaded(List<Coche> coches, List<String> keys) {

            }

            @Override
            public void cocheIsAdd() {

            }

            @Override
            public void cocheIsUpdate() {
                Toast.makeText(MostrarDetallesCoche.this,"actualizado corectamente", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void cocheIsDelete() {

            }
        },key,c);
    }
}