package com.example.appgooutec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextApe;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonRegister;

    private String nombre = " ";
    private String apellido = " ";
    private String email = " ";
    private String contrasena = " ";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextName = (EditText) findViewById(R.id.txtnombre);
        mEditTextApe = (EditText) findViewById(R.id.txtapellido);
        mEditTextEmail = (EditText) findViewById((R.id.txtemail));
        mEditTextPassword = (EditText) findViewById(R.id.txtcontraseña);
        mButtonRegister = (Button) findViewById(R.id.btnregistrarse);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                nombre = mEditTextName.getText().toString();
                apellido = mEditTextApe.getText().toString();
                email = mEditTextEmail.getText().toString();
                contrasena= mEditTextPassword.getText().toString();

                if (!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !contrasena.isEmpty()){

                    if (contrasena.length() >= 6) {
                        registrarUser();

                    }
                    else {
                        Toast.makeText(Registro.this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(Registro.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    private void registrarUser() {
         mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){

                     Map <String, Object> map = new HashMap<>();
                     map.put( "nombre", nombre);
                     map.put("apellido", apellido);
                     map.put("email", email);
                     map.put("contraseña", contrasena);

                     String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent( Registro.this, Regiones.class ));
                                finish();
                            }
                            else {
                                Toast.makeText(Registro.this, "No se pudieron crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                 }
                 else {
                     Toast.makeText(Registro.this, "No se puedo registrar el usuario", Toast.LENGTH_SHORT).show();

                 }
             }
         }

         );
    }
    public void Atras (View view){
        Intent atras = new Intent(this, MainActivity.class);
        startActivity(atras);
    }
    @Override
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(Registro.this, Inicio.class));
            finish();
        }
    }

}