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

public class ListaCochesAdapter extends RecyclerView.Adapter<CocheViewHolder> {
    private Context c;
    private List<Coche> listaCoches;
    private  List<String> keys;
    private LayoutInflater minflater;

    public ListaCochesAdapter(Context c, List<Coche> listaCoches, List<String> keys, LayoutInflater minflater) {
        this.c = c;
        this.listaCoches = listaCoches;
        this.keys = keys;
        this.minflater = minflater;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public List<Coche> getListaCoches() {
        return listaCoches;
    }

    public void setListaCoches(List<Coche> listaCoches) {
        this.listaCoches = listaCoches;
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

    public ListaCochesAdapter(Context c) {
        this.c = c;
        minflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public CocheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = minflater.inflate(R.layout.item_recyclrerview_coche, parent,false);
        return new CocheViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CocheViewHolder holder, int position) {
        if (listaCoches != null) {
            Coche coche_actual = listaCoches.get(position);
            holder.txt_rv_bastidor.setText("# Bastidor: " + coche_actual.getNbastidor());
            holder.txt_rv_marca.setText("Marca: " + coche_actual.getMarca());
            holder.txt_rv_modelo.setText("Modelo: " + coche_actual.getModelo());
            holder.txt_rv_precio.setText("Precio: " + coche_actual.getPrecio() + " €");
            if (coche_actual.getFoto() != null) {
                new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                    @Override
                    public void FotoIsDownload(byte[] bytes) {
                        if (bytes != null) {
                            Log.i("firebasedb", "foto descargada correctamente");
                            Bitmap foto = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                            holder.img_rv_coche.setImageBitmap(foto);
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
                }, coche_actual.getFoto());
            }
        }

    }

    @Override
    public int getItemCount() {
                if (listaCoches != null)
                    return listaCoches.size();
                else  return 0;
            }
}
