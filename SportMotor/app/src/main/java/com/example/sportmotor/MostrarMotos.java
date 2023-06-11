package com.example.sportmotor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sportmotor.controlador.MotosFirebaseController;
import com.example.sportmotor.modelo.ListaMotosAdapter;
import com.example.sportmotor.modelo.Moto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MostrarMotos extends AppCompatActivity {
    private RecyclerView rv_motos = null;
    private ListaMotosAdapter mAdapter;
    private ArrayList<Moto> motos;
    private ArrayList<String> keys;
    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(MostrarMotos.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(MostrarMotos.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_motos);
        mAuth = FirebaseAuth.getInstance();
        rv_motos = findViewById(R.id.rv_motos);
        mAdapter = new ListaMotosAdapter(this);
        new MotosFirebaseController().obtener_motos(new MotosFirebaseController.MotosStatus() {
            @Override
            public void motosIsLoaded(List<Moto> motos, List<String> keys) {
                mAdapter.setListaMotos(motos);
                mAdapter.setKeys(keys);
            }

            @Override
            public void motoIsAdd() {

            }

            @Override
            public void motoIsUpdate() {

            }

            @Override
            public void motoIsDelete() {

            }
        });

        rv_motos.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_motos.setLayoutManager(new LinearLayoutManager(this));
        }else{

        }
    }

    public void addMoto(View view) {
        Intent intent = new Intent(this, Add_moto.class);
        startActivity(intent);
    }

    public void salirm(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}