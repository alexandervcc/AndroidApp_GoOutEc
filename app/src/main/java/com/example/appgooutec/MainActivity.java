package com.example.appgooutec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

    }
    public void Registro (View view){
        Intent registro = new Intent(this, Registro.class);
        startActivity(registro);
    }
    public void Inicio (View view){
        Intent iniciar = new Intent(this, Iniciar_Sesion.class);
        startActivity(iniciar);

    }

    @Override
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Log.i("log","Usuario ya identificado");
            startActivity(new Intent(MainActivity.this, Regiones.class));
            finish();
        }
    }

}