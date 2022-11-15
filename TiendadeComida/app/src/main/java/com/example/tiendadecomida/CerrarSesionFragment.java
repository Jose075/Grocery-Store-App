package com.example.tiendadecomida;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiendadecomida.activities.LoginActivity;
import com.example.tiendadecomida.activities.PedidoHechoActivity;
import com.google.firebase.auth.FirebaseAuth;


public class CerrarSesionFragment extends Fragment
{

    FirebaseAuth autenticar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root =  inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);





        return root;
    }
}