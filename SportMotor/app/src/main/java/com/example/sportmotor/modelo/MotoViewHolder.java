package com.example.sportmotor.modelo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportmotor.MostrarDetallesMoto;
import com.example.sportmotor.R;

import java.security.Key;
import java.util.List;

public class MotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final String EXTRA_OBJETO_MOTO = "es.bryan.motoViewHolder.objeto_motos";
    public static final String EXTRA_OBJETO_MOTO_KEY = "es.bryan.motoViewHolder.objeto_keys";
    public TextView txt_rv_bstidorm, txt_rv_marcam, txt_rv_modelom, txt_rv_preciom = null;
    public ImageView img_rv_moto = null;
    ListaMotosAdapter lcAdapter;

    public MotoViewHolder(@NonNull View itemView, ListaMotosAdapter lcAdapter) {
        super(itemView);
        txt_rv_bstidorm = (TextView) itemView.findViewById(R.id.txt_rv_bastidorm);
        txt_rv_marcam = (TextView) itemView.findViewById(R.id.txt_rv_marcam);
        txt_rv_modelom = (TextView) itemView.findViewById(R.id.txt_rv_modelom);
        txt_rv_preciom = (TextView) itemView.findViewById(R.id.txt_rv_preciom);
        img_rv_moto = (ImageView) itemView.findViewById(R.id.img_rv_moto);
        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View view) {
        int mPosition = getAdapterPosition();
        List<Moto> motos = this.lcAdapter.getListaMotos();
        List<String> keys = this.lcAdapter.getKeys();
        Moto moto = motos.get(mPosition);
        String key = keys.get(mPosition);
        Intent intent = new Intent(lcAdapter.getC(), MostrarDetallesMoto.class);
        intent.putExtra(EXTRA_OBJETO_MOTO, moto);
        intent.putExtra(EXTRA_OBJETO_MOTO_KEY, key);
        lcAdapter.getC().startActivity(intent);

    }
}
