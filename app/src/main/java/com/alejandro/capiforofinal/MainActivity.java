package com.alejandro.capiforofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button botonregistro;
    EditText ETCorreo, ETContraseña;
    Button BotonLogin;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_CapiForo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonregistro = findViewById(R.id.botonregistro);

        ETCorreo = findViewById(R.id.ETCorreo);
        ETContraseña = findViewById(R.id.ETContraseña);
        BotonLogin = findViewById(R.id.BotonLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this);
        dialog = new Dialog(MainActivity.this);



        BotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Correo = ETCorreo.getText().toString().trim();
                String Contra   = ETContraseña.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(Correo).matches()){
                    ETCorreo.setError("Correo inválido");
                    ETCorreo.setFocusable(true);
                }else if (Contra.length()<7){
                    ETContraseña.setError("La contraseña es incorrecta");
                    ETContraseña.setFocusable(true);
                }else {
                    LOGINUSUARIO(Correo, Contra);
                }
            }
        });

        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,actividadRegistrarse.class));

            }
        });
    }

    private void LOGINUSUARIO(String Correo, String Contra) {
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Correo, Contra)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();

                            startActivity(new Intent(MainActivity.this,MainActivity2.class));

                        }else{
                            progressDialog.dismiss();
                            NoSesion();
                            //Toast.makeText(MainActivity.this, "UPS! Algo salío mal", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private Void NoSesion(){
        Button NoSesion;
        dialog.setContentView(R.layout.no_sesion);

        NoSesion = dialog.findViewById(R.id.NoInicio);
        NoSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        return null;
    }
}