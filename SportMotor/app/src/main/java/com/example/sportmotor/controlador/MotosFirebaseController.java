package com.example.sportmotor.controlador;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.modelo.Moto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotosFirebaseController {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Moto> motos;


    public interface MotosStatus{
        void motosIsLoaded(List<Moto> motos, List<String> keys);

        void motoIsAdd();

        void motoIsUpdate();

        void motoIsDelete();
    }

    public MotosFirebaseController(){
        this.mDatabase = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Motos");
        this.motos = new ArrayList<Moto>();
    }
    public void obtener_motos(final MotosStatus motosStatus){
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                motos.clear();
                List<String> keys = new ArrayList<String>();
                for (DataSnapshot keynode : snapshot.getChildren()){
                    keys.add(keynode.getKey());
                    Moto m = keynode.getValue(Moto.class);
                    motos.add(m);
                }
                motosStatus.motosIsLoaded(motos, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insertarMoto(final MotosFirebaseController.MotosStatus motosStatus, Moto m){
        this.myRef.push().setValue(m).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                motosStatus.motoIsAdd();
                Log.i("firebasedb", "insertado correcto");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "insertado incorrecta");
            }
        });
    }

    public void borrarMoto(final MotosFirebaseController.MotosStatus motosStatus, String key){
        this.myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                motosStatus.motoIsDelete();
                Log.i("firebasedb", "borrado correcto");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "borrado incorrecto");

            }
        });
    }

    public void actualizarMoto(final MotosFirebaseController.MotosStatus motosStatus, String key, Moto m){
        Map<String, Object> nuevaMoto = new HashMap<String,Object>();
        nuevaMoto.put(key,m);
        myRef.updateChildren(nuevaMoto).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                motosStatus.motoIsUpdate();
                Log.i("firebasedb", "actualizado correcto");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "actualizado incorrecto");
            }
        });
    }

}
