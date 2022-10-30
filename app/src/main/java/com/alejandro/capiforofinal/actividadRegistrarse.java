package com.alejandro.capiforofinal;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class actividadRegistrarse extends AppCompatActivity {

    EditText Correo, Contraseña;
    Button RegistrarUsuario;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_registrarse);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Contraseña = findViewById(R.id.Contraseña);
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario);


        firebaseAuth = FirebaseAuth.getInstance();

        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String Contra = Contraseña.getText().toString();

                //validacion

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("Correo no válido");
                    Correo.setFocusable(true);
                }
                else if (Contra.length()<7){
                    Contraseña.setError("Ingrese un minimo de 7 caracteres");
                    Contraseña.setFocusable(true);
                }else{
                    RegistrarUsuario(correo,Contra);
                }
            }
        });
    }

    private void RegistrarUsuario(String correo, String contra) {
        firebaseAuth.createUserWithEmailAndPassword(correo,contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String contra = Contraseña.getText().toString();

                            HashMap<Object,String> DatosUsuario = new HashMap<>();
                            DatosUsuario.put("uid", uid);
                            DatosUsuario.put("correo", correo);
                            DatosUsuario.put("contra", contra);

                            DatosUsuario.put("imagen","");


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Creacion base de datos
                            DatabaseReference reference = database.getReference("USUARIOS");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(actividadRegistrarse.this, "Se registró exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(actividadRegistrarse.this,MainActivity2.class ));
                        }else{
                            Toast.makeText(actividadRegistrarse.this, "UPS! Algio salió mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(actividadRegistrarse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}