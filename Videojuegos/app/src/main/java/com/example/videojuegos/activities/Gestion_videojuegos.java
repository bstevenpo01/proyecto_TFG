package com.example.videojuegos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.videojuegos.R;

public class Gestion_videojuegos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_videojuegos);
    }

    public void add_videojuego(View view) {
        Intent intent = new Intent(this, NuevoVideojuego.class);
        startActivity(intent);
    }

    public void actualizar_videojuego(View view) {
    }

    public void mostrar_videojuego(View view) {
    }

    public void borrar_videojuego(View view) {
    }
}