package com.example.sportmotor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportmotor.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {
    private EditText edt_nombre_registro, edt_apellidos_registro, edt_correo_registro, edt_contraseña_registro;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edt_nombre_registro = (EditText) findViewById(R.id.edt_nombre_registro);
        edt_apellidos_registro = (EditText) findViewById(R.id.edt_apellidos_registro);
        edt_correo_registro = (EditText) findViewById(R.id.edt_correo_registro);
        edt_contraseña_registro = (EditText) findViewById(R.id.edt_contraseña_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();



        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


    }

    public void crearUsuario(View view) {
        final  String nombre = edt_nombre_registro.getText().toString();
        final  String apellidos = edt_apellidos_registro.getText().toString();
        final  String email = edt_correo_registro.getText().toString();
        final  String password = edt_contraseña_registro.getText().toString();

        if (nombre.isEmpty()){
            Toast.makeText(this, "Verifique el nombre", Toast.LENGTH_SHORT).show();
            return;
        }else if (apellidos.isEmpty()){
            Toast.makeText(this, "Verifique los apellidos", Toast.LENGTH_SHORT).show();
            return;
        }else if (email.isEmpty()){
            Toast.makeText(this, "Verifique el correo", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.isEmpty()){
            Toast.makeText(this, "Verifique la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }else if (password.length() < 6){
            Toast.makeText(this, "La cotraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.setMessage("Conectando con la base de datos...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Usuario u = new Usuario();
                        u.setNombre(nombre);
                        u.setApellidos(apellidos);
                        u.setCorreo(email);
                        u.setContraseña(password);

                        DatabaseReference myRef = mDatabase.getReference("Usuarios");
                        myRef.push().setValue(u);

                        Toast.makeText(Registro.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent intent = new Intent(Registro.this, Menu.class);
                        startActivity(intent);

                    }else{
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(Registro.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.i("firebasedb", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registro.this, "Error al registrarse", Toast.LENGTH_SHORT).show();

                        }

                    }
                    progressDialog.dismiss();
                }
            });
        }

    }
}