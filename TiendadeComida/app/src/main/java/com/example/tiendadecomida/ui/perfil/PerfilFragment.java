package com.example.tiendadecomida.ui.perfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tiendadecomida.R;
import com.example.tiendadecomida.ui.ValidacionFragment;


public class PerfilFragment extends Fragment
{
    String texto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {


        View root = inflater.inflate(R.layout.fragment_perfil,container,false);
        return root;





    }

}