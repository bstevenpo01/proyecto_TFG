package com.example.videojuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NuevoVideojuego extends AppCompatActivity {

    private EditText edt_add_videojuego;
    private EditText edt_add_descripcion;
    VideojuegoViewModel mVideojuegoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_videojuego);
        edt_add_videojuego = (EditText) findViewById(R.id.edt_nombrev);
        edt_add_descripcion = (EditText) findViewById(R.id.edt_descripcionv);
        mVideojuegoViewModel
    }

    public void add_videojuego(View view) {

    }
}