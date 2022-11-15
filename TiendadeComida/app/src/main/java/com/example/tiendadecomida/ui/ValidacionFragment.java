package com.example.tiendadecomida.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tiendadecomida.R;
import com.example.tiendadecomida.activities.DetalleProductoActivity;
import com.example.tiendadecomida.activities.LoginActivity;
import com.example.tiendadecomida.activities.perfilActivity;
import com.example.tiendadecomida.ui.categoria.CategoriaFragment;
import com.example.tiendadecomida.ui.perfil.PerfilFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class ValidacionFragment extends Fragment
{
    Button ingresarSesion;
    EditText contrasena;
    FirebaseAuth autenticar2;
    FirebaseUser correo;
    String correoTxt;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        autenticar2 = FirebaseAuth.getInstance();
        correo = FirebaseAuth.getInstance().getCurrentUser();

        View root = inflater.inflate(R.layout.fragment_validacion,container,false);
        contrasena = root.findViewById(R.id.editFragmentContrasena);
        ingresarSesion = root.findViewById(R.id.btnIngresar);
        correoTxt = correo.getEmail();

        ingresarSesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ingresarSesion.setEnabled(false);
                ingresoUsuario();

                //progressBar.setVisibility(View.VISIBLE);
            }

        });




        return root;





    }




    private void ingresoUsuario()
    {

        ingresarSesion.setEnabled(false);
        String contrasenaUsuario = contrasena.getText().toString();

        if(!contrasenaUsuario.isEmpty())
        {
            autenticar2.signInWithEmailAndPassword(correoTxt,contrasenaUsuario)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getContext(), perfilActivity.class);
                                Toast.makeText(getContext(), "Inicio exitoso", Toast.LENGTH_SHORT).show();
                                contrasena.setText("");
                                getContext().startActivity(intent);
                                ingresarSesion.setEnabled(true);



                            }

                            else
                            {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                //progressBar.setVisibility(View.GONE);
                                if (errorCode.equals("ERROR_WRONG_PASSWORD")) {
                                    Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                    ingresarSesion.setEnabled(true);
                                    return;
                                }

                                //progressBar.setVisibility(View.GONE);
                                else {
                                    Toast.makeText(getContext(), "Error" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    ingresarSesion.setEnabled(true);

                                }

                            }

                        }

                    });
        }

        else
        {
            Toast.makeText(getContext(), "Ingresa la Contraseña", Toast.LENGTH_SHORT).show();
            ingresarSesion.setEnabled(true);
        }




    }
}