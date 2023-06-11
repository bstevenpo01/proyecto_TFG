package com.example.sportmotor.controlador;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sportmotor.modelo.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UsuarioFirebaseController {
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private List<Usuario> usuario;

    public interface UsuarioStatus{

        void UsuarioIsAdd();
    }

    public UsuarioFirebaseController(){
        this.mDatabase = FirebaseDatabase.getInstance();
        this.myRef = mDatabase.getReference("Usuarios");
    }

    public  void insertarUsuario(final UsuarioStatus usuarioStatus, Usuario usuario){
        this.myRef.push().setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                usuarioStatus.UsuarioIsAdd();
                Log.i("firebasedb", "usuario registrado correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("firebasedb", "error al añadir usuario");
            }
        });
    }
}
