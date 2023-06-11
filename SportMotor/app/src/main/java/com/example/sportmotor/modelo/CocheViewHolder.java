package com.example.sportmotor.modelo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportmotor.MostrarDetallesCoche;
import com.example.sportmotor.R;

import java.util.List;

public class CocheViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final String EXTRA_OBJETO_COCHE = "es.bryan.cocheViewHolder.objeto_coches";
    public static final String EXTRA_OBJETO_COCHE_KEY = "es.bryan.cocheViewHolder.objeto_keys" ;
    public TextView txt_rv_bastidor, txt_rv_marca, txt_rv_modelo, txt_rv_precio = null;
    public ImageView img_rv_coche = null;
    ListaCochesAdapter lcAdapter;


    public CocheViewHolder(@NonNull View itemView, ListaCochesAdapter lcAdapter) {
        super(itemView);
        txt_rv_bastidor = (TextView) itemView.findViewById(R.id.txt_rv_bastidor);
        txt_rv_marca = (TextView) itemView.findViewById(R.id.txt_rv_marca);
        txt_rv_modelo = (TextView) itemView.findViewById(R.id.txt_rv_modelo);
        txt_rv_precio = (TextView) itemView.findViewById(R.id.txt_rv_precio);
        img_rv_coche = (ImageView) itemView.findViewById(R.id.img_rv_coche);
        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View view) {
        int mPosition = getAdapterPosition();
        List<Coche> coches = this.lcAdapter.getListaCoches();
        List<String> keys = this.lcAdapter.getKeys();
        Coche coche = coches.get(mPosition);
        String key = keys.get(mPosition);
        Intent intent = new Intent(lcAdapter.getC(), MostrarDetallesCoche.class);
        intent.putExtra(EXTRA_OBJETO_COCHE, coche);
        intent.putExtra(EXTRA_OBJETO_COCHE_KEY, key);
        lcAdapter.getC().startActivity(intent);

    }
}
