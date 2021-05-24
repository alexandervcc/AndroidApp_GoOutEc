package com.example.appgooutec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Iniciar_Sesion extends AppCompatActivity {

    private EditText mEditemail;
    private EditText mEditpassword;
    private Button mButtonInicio;

    private String email1 = " ";
    private String password = " ";

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mEditemail = (EditText) findViewById((R.id.txtusuario));
        mEditpassword = (EditText) findViewById(R.id.txtpassword);
        mButtonInicio = (Button) findViewById(R.id.btnInicio);

        mAuth = FirebaseAuth.getInstance();

        mButtonInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                email1 = mEditemail.getText().toString();
                password= mEditpassword.getText().toString();

                if (!email1.isEmpty() && !password.isEmpty()){
                        loginUser();
                }
                else {
                    Toast.makeText(Iniciar_Sesion.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void loginUser (){
        mAuth.signInWithEmailAndPassword(email1, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task3) {
                        if (task3.isSuccessful()){
                            startActivity(new Intent(Iniciar_Sesion.this, SierraLagoSanPablo.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void Atras (View view){
        Intent atras = new Intent(this, MainActivity.class);
        startActivity(atras);
    }
    @Override
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(Iniciar_Sesion.this, SierraLagoSanPablo.class));
            finish();
        }
    }
}