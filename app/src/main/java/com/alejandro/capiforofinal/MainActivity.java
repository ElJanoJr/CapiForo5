package com.alejandro.capiforofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_CapiForo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Siguiente(View view){
        Intent siguiente = new Intent(getBaseContext(), MainActivity2.class);
        startActivity(siguiente);
    }
}