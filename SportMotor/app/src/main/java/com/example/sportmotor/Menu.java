package com.example.sportmotor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void motos(View view) {
        Intent intent = new Intent(this, MostrarMotos.class);
        startActivity(intent);
    }

    public void coches(View view) {
        Intent intent = new Intent(this, MostrarCoches.class);
        startActivity(intent);
    }

    public void cerrar_cesion(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Nosotros(View view) {
        Intent intent = new Intent(this, Nosotros.class);
        startActivity(intent);
    }
}