package com.example.tiendadecomida.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiendadecomida.MainActivity;
import com.example.tiendadecomida.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity
{

    Button iniciarSesionBtn;
    EditText correo,contrasena;
    TextView registrarse;
    ProgressBar progressBar;

    FirebaseAuth autenticar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticar = FirebaseAuth.getInstance();
        iniciarSesionBtn = findViewById(R.id.btnSesion);
        correo = findViewById(R.id.editTextLog);
        contrasena = findViewById(R.id.editTextContrasena);
        registrarse = findViewById(R.id.registro);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        /*if(autenticar.getCurrentUser() != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Toast.makeText(this,"ya estas logeado",Toast.LENGTH_SHORT).show();
            finish();
        }*/


        registrarse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, RegistrarActivity.class));
            }
        });

        iniciarSesionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                iniciarSesionBtn.setEnabled(false);
                loginUsuario();
                iniciarSesionBtn.setEnabled(true);
                progressBar.setVisibility(View.VISIBLE);
            }

        });

    }
    private void loginUsuario()
    {
        String correoUsuario = correo.getText().toString();
        String contrasenaUsuario = contrasena.getText().toString();



        if (TextUtils.isEmpty(correoUsuario))
        {
            Toast.makeText(this, "El campo Correo está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(contrasenaUsuario))
        {
            Toast.makeText(this, "El campo Contraseña está Vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isConnected(this))
        {
            autenticar.signInWithEmailAndPassword(correoUsuario,contrasenaUsuario)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if (task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }

                            else
                            {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                progressBar.setVisibility(View.GONE);
                                if(errorCode.equals("ERROR_WRONG_PASSWORD"))
                                {
                                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(!errorCode.equals("ERROR_CREDENTIAL_ALREADY_IN_USE"))
                                {
                                    Toast.makeText(LoginActivity.this, "Este correo no está registrado", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                //progressBar.setVisibility(View.GONE);
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Error"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(LoginActivity.this, "No hay conexíón de internet", Toast.LENGTH_SHORT).show();
        }



    }

    private boolean isConnected(LoginActivity login)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected()))
        {
            return true;
        }

        else
        {
            return false;
        }
    }



}