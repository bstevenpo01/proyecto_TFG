package com.example.sportmotor.controlador;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sportmotor.modelo.Coche;
import com.example.sportmotor.modelo.Usuario;
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

public class CochesFirebaseController {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Coche> coches;

    public interface CochesStatus{
        void cochesIsLoaded(List<Coche> coches, List<String> keys);

        void cocheIsAdd();

        void cocheIsUpdate();

        void cocheIsDelete();
    }

    public CochesFirebaseController(){
        this.mDatabase = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Coches");
        this.coches = new ArrayList<Coche>();
    }

    public void obtener_coches(final CochesStatus cochesStatus){
        this.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coches.clear();
                List<String> keys = new ArrayList<String>();
                for (DataSnapshot keynode : snapshot.getChildren()){
                    keys.add(keynode.getKey());
                    Coche c = keynode.getValue(Coche.class);
                    coches.add(c);
                }
                cochesStatus.cochesIsLoaded(coches,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insertarCoche(final CochesStatus cochesStatus, Coche c){
        this.myRef.push().setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                cochesStatus.cocheIsAdd();
                Log.i("firebasedb", "insertado correcto");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "insertado incorrecta");

            }
        });
    }

    public void borrarCoche(final CochesStatus cochesStatus, String key){
        this.myRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                cochesStatus.cocheIsDelete();
                Log.i("firebasedb", "borrado correcto");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "borrado incorrecto");

            }
        });
    }

    public void actualizarCoche(final CochesStatus cochesStatus, String key, Coche c){
        Map<String, Object> nuevoCoche = new HashMap<String,Object>();
        nuevoCoche.put(key,c);
        myRef.updateChildren(nuevoCoche).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                cochesStatus.cocheIsUpdate();
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
