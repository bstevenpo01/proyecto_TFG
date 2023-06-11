package com.example.sportmotor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sportmotor.controlador.CochesFirebaseController;
import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.modelo.ListaCochesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MostrarCoches extends AppCompatActivity {
    private RecyclerView rv_coches = null;
    private ListaCochesAdapter mAdapter;
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
            Toast.makeText(MostrarCoches.this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(MostrarCoches.this, MainActivity.class);
            startActivity(intent);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coches);
        mAuth = FirebaseAuth.getInstance();
        rv_coches = findViewById(R.id.rv_coches);
        mAdapter = new ListaCochesAdapter(this);
        new CochesFirebaseController().obtener_coches(new CochesFirebaseController.CochesStatus() {
            @Override
            public void cochesIsLoaded(List<Coche> coches, List<String> keys) {
                mAdapter.setListaCoches(coches);
                mAdapter.setKeys(keys);
            }

            @Override
            public void cocheIsAdd() {

            }

            @Override
            public void cocheIsUpdate() {

            }

            @Override
            public void cocheIsDelete() {

            }
        });

        rv_coches.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_coches.setLayoutManager(new LinearLayoutManager(this));
        }else{

        }
    }

    public void addCoche(View view) {
        Intent intent = new Intent(this, Add_coche.class);
        startActivity(intent);
    }

    public void salir(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}