package com.example.sportmotor.modelo;

import static com.example.sportmotor.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportmotor.R;
import com.example.sportmotor.utilidades.ImagenesFirebase;

import java.util.List;

public class ListaMotosAdapter extends RecyclerView.Adapter<MotoViewHolder>{
    private Context c;
    private List<Moto> listaMotos;
    private List<String> keys;
    private LayoutInflater minflater;

    public ListaMotosAdapter(Context c, List<Moto> listaMotos, List<String> keys, LayoutInflater minflater) {
        this.c = c;
        this.listaMotos = listaMotos;
        this.keys = keys;
        this.minflater = minflater;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public List<Moto> getListaMotos() {
        return listaMotos;
    }

    public void setListaMotos(List<Moto> listaMotos) {
        this.listaMotos = listaMotos;
        notifyDataSetChanged();
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public LayoutInflater getMinflater() {
        return minflater;
    }

    public void setMinflater(LayoutInflater minflater) {
        this.minflater = minflater;
    }

    public ListaMotosAdapter(Context c) {
        this.c = c;
        this.minflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public MotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = minflater.inflate(R.layout.item_recyclrerview_moto, parent, false);
        return new MotoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MotoViewHolder holder, int position) {
        if (listaMotos != null){
            Moto moto_actual = listaMotos.get(position);
            holder.txt_rv_bstidorm.setText("# Bastidor: " + moto_actual.getNbastidorm());
            holder.txt_rv_marcam.setText("Marca: " + moto_actual.getMarcam());
            holder.txt_rv_modelom.setText("Modelo: " + moto_actual.getModelom());
            holder.txt_rv_preciom.setText("Precio: " + moto_actual.getPreciom() + "€");
            if (moto_actual.getFoto() != null){
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if (bytes != null) {
                            Log.i("firebasedb", "foto descargada correctamente");
                            Bitmap foto = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            holder.img_rv_moto.setImageBitmap(foto);
                        } else {
                            Log.i("firebasedb", "foto no descargada correctamente");
                        }
                    }

                    @Override
                    public void FotoIsUpload() {

                    }

                    @Override
                    public void FotoIsDelete() {

                    }
                }, moto_actual.getFoto());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (listaMotos != null)
            return listaMotos.size();
        else return 0;
    }
}
