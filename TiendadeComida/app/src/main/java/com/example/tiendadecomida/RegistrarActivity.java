package com.example.tiendadecomida;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity
{


    Button registrarsebtn;
    EditText nombre,apellido,correo,contrasena,confirm_contrasena;
    TextView captionBntLog;
    FirebaseAuth autenticacion;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        autenticacion = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        captionBntLog = findViewById(R.id.r_inicioSesion);
        registrarsebtn = findViewById(R.id.btnregistro);
        nombre = findViewById(R.id.editTextReg);
        apellido = findViewById(R.id.editTextReg2);
        correo = findViewById(R.id.editTextReg3);
        contrasena = findViewById(R.id.editTextReg4);
        confirm_contrasena = findViewById(R.id.editTextReg5);

        captionBntLog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegistrarActivity.this,LoginActivity.class));
            }
        });

        registrarsebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                crearUsuario();
                progressBar.setVisibility(View.VISIBLE);

            }
        });


    }

    private void crearUsuario() {

        String nombreUsuario = nombre.getText().toString();
        String apellidoUsuario = apellido.getText().toString();
        String correoUsuario = correo.getText().toString();
        String contrasenaUsuario = contrasena.getText().toString();
        String confirmarContrasena = confirm_contrasena.getText().toString();


        if (TextUtils.isEmpty(nombreUsuario)){
            Toast.makeText(this, "El campo Nombre está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(apellidoUsuario)){
            Toast.makeText(this, "El campo Apellido está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!confirmarContrasena.equals(contrasenaUsuario))
        {
            Toast.makeText(this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(correoUsuario)){
            Toast.makeText(this, "El campo Correo está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(contrasenaUsuario)){
            Toast.makeText(this, "El campo Contraseña está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contrasenaUsuario.length() < 6){
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }



        //Create User
        autenticacion.createUserWithEmailAndPassword(correoUsuario,contrasenaUsuario)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful())
                        {
                            modeloUsuario modeloUsuario = new modeloUsuario(nombreUsuario,apellidoUsuario,correoUsuario,contrasenaUsuario);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(modeloUsuario);
                            Toast.makeText(RegistrarActivity.this, "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(RegistrarActivity.this,LoginActivity.class));
                        }
                        else
                        {

                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            progressBar.setVisibility(View.GONE);
                            if(errorCode.equals("ERROR_INVALID_EMAIL"))
                            {
                                Toast.makeText(RegistrarActivity.this, "Este correo es inválido", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            /*if(errorCode.equals("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL"))
                            {
                                Toast.makeText(RegistrarActivity.this, "Este cuenta ya está registrado", Toast.LENGTH_SHORT).show();
                                return;
                            }*/

                            if(errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE"))
                            {
                                Toast.makeText(RegistrarActivity.this, "Este correo está en uso", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            else
                            {
                                    Toast.makeText(RegistrarActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    }

                });/*.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {

                if(e instanceof FirebaseNetworkException)
                {
                    Toast.makeText(RegistrarActivity.this, "Revisa tu conexión de internet", Toast.LENGTH_SHORT).show();
                }
            }
        });*/



    }


    }
