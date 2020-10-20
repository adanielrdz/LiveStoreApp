package com.app.livestoreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroUser extends AppCompatActivity {

    EditText muser,memail,mtel,mdireccion,mpass,mconfirmpass;
    Button mRegisterbtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_user);

        muser=findViewById(R.id.txtNombreUser);
        memail=findViewById(R.id.txtCorreo);
        mpass=findViewById(R.id.txtPass);
        mconfirmpass=findViewById(R.id.txtConfirmarPass);
      mRegisterbtn=findViewById(R.id.btnRegistrado);
        mtel=findViewById(R.id.txtTel);
        mdireccion= findViewById(R.id.txtDireccion);

        fAuth=FirebaseAuth.getInstance();

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpass.getText().toString().trim();
                String confirmarPass=mconfirmpass.toString().trim();
                //Validaciones
                if(TextUtils.isEmpty(email)){
                    memail.setError("El email es requerido");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mpass.setError("La contraseña es requerida");
                    return;
                }
                if(password.length()<6)
                {
                    mpass.setError("La contraseña debe tener mas de 6 caracteres");
                }
                if(password!=confirmarPass)
                {
                    mconfirmpass.setError("No coincide la contraseña");
                }

                //Registro en firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegistroUser.this,"Verificar email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else
                        {
                            Toast.makeText(RegistroUser.this,"Error al registrar"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}