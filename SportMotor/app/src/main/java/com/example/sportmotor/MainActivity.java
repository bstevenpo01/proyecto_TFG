package com.example.sportmotor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportmotor.modelo.Coche;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edt_correo, edt_contraseña;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_correo = findViewById(R.id.edt_correo);
        edt_contraseña = findViewById(R.id.edt_contraseña);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public  void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            currentUser.reload();
        }
    }

    public void ingresar(View view) {
        String email = String.valueOf(edt_correo.getText());
        String password = String.valueOf(edt_contraseña.getText());
        if (email.isEmpty()){
            Toast.makeText(this, "Verifique el correo", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.isEmpty()){
            Toast.makeText(this, "Verifique la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.length() < 6){
            Toast.makeText(this, "La cotraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                }else {
                    Log.i("firebasedb", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registrarse(View view) {
        Intent intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent);
    }
}