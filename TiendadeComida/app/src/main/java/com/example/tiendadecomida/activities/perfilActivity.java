package com.example.tiendadecomida.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.MainActivity;
import com.example.tiendadecomida.R;

import com.example.tiendadecomida.ui.modeloUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class perfilActivity extends AppCompatActivity
{
    Toolbar toolbar;

    CircleImageView perfilImg;
    EditText nombre,apellido,correo,contraseña;
    Button actualizar;

    FirebaseStorage storage;
    FirebaseAuth autenticar;
    FirebaseDatabase dba;
    DatabaseReference dbEdit;
    modeloUsuario modelUser;
    FirebaseUser datos;
    StorageReference storageReference;
    Uri perfilUri;

    String nombreUsuario;
    String apellidoUsuario;
    String correoUsuario;
    String contrasenaUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //View headerView = navigationView.getHeaderView(0);


        toolbar = findViewById(R.id.barraTituloEditar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autenticar = FirebaseAuth.getInstance();
        dba = FirebaseDatabase.getInstance();
        dbEdit = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        //datos = FirebaseAuth.getInstance().getCurrentUser();
        perfilImg = findViewById(R.id.perfil_img);
        nombre = findViewById(R.id.nombre_perfil);
        apellido = findViewById(R.id.apellido_perfil);
        correo = findViewById(R.id.correo_perfil);
        //CircleImageView imgDrawner = headerView.findViewById(R.id.drawner_img);
        contraseña = findViewById(R.id.contraseña_perfil);
        actualizar = findViewById(R.id.actualizar);




        dba.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        modelUser = snapshot.getValue(modeloUsuario.class);
                        String img;

                        img = modelUser.getPerfilImg();

                        nombreUsuario = modelUser.getNombre();
                        nombre.setText(nombreUsuario.toString());


                        apellidoUsuario = modelUser.getApellido();
                        apellido.setText(apellidoUsuario.toString());

                        contrasenaUsuario = modelUser.getContrasena();
                        contraseña.setText(contrasenaUsuario.toString());

                        correoUsuario = modelUser.getCorreo();
                        correo.setText(correoUsuario.toString());


                        if(snapshot.hasChild("perfilImg"))
                        {
                            Glide.with(perfilActivity.this).load(modelUser.getPerfilImg()).into(perfilImg);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });

        perfilImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });



        actualizar.setOnClickListener(new View.OnClickListener()
        {



            @Override
            public void onClick(View v)
            {

                actualizar.setEnabled(false);
                nombreUsuario = nombre.getText().toString();
                apellidoUsuario = apellido.getText().toString();
                correoUsuario = correo.getText().toString();
                contrasenaUsuario = contraseña.getText().toString();



                final HashMap<String,Object> carritoMap = new HashMap<>();

                carritoMap.put("nombre",nombreUsuario);
                carritoMap.put("apellido",apellidoUsuario);
                carritoMap.put("contrasena",contrasenaUsuario);
                carritoMap.put("correo",correoUsuario);




                if(nombre.length() == 0)
                {
                    Toast.makeText(perfilActivity.this, "El campo nombre está vacío", Toast.LENGTH_SHORT).show();
                    actualizar.setEnabled(true);

                }


                else if(apellido.length() == 0)
                {
                    Toast.makeText(perfilActivity.this, "El campo apellido está vacío", Toast.LENGTH_SHORT).show();
                    actualizar.setEnabled(true);

                }

                else if (contraseña.length() < 6 && contraseña.length() > 0)
                {
                    Toast.makeText(perfilActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    actualizar.setEnabled(true);

                }

                else if(contraseña.length() == 0)
                {
                    Toast.makeText(perfilActivity.this, "El campo contraseña está vacío", Toast.LENGTH_SHORT).show();
                    actualizar.setEnabled(true);

                }

                else if (correo.length() == 0)
                {
                    Toast.makeText(perfilActivity.this, "El campo correo está vacío", Toast.LENGTH_SHORT).show();
                    actualizar.setEnabled(true);

                }


                else
                {
                    autenticar.getCurrentUser().updateEmail(correoUsuario)
                            .addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {

                                        autenticar.getCurrentUser().updatePassword(contrasenaUsuario);
                                        dbEdit.child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(carritoMap).addOnSuccessListener(new OnSuccessListener<Void>()
                                        {
                                            @Override
                                            public void onSuccess(Void unused)
                                            {

                                                storageReference = storage.getReference().child("foto_perfil")
                                                        .child(FirebaseAuth.getInstance().getUid());
                                                if(perfilUri != null)
                                                {
                                                    storageReference.putFile(perfilUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                                                    {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                        {
                                                            Toast.makeText(perfilActivity.this,"Subido",Toast.LENGTH_SHORT).show();

                                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                                            {
                                                                @Override
                                                                public void onSuccess(Uri uri)
                                                                {
                                                                    dba.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                                                            .child("perfilImg").setValue(uri.toString());
                                                                    Toast.makeText(perfilActivity.this,"Foto de Perfil Actualizada", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });
                                                        }
                                                    });

                                                    //Toast.makeText(perfilActivity.this,nombreUsuario+apellidoUsuario+correoUsuario+contrasenaUsuario,Toast.LENGTH_SHORT).show();
                                                    actualizar.setEnabled(true);
                                                    startActivity(new Intent(perfilActivity.this, MainActivity.class));




                                                }

                                                else
                                                    {
                                                        //Toast.makeText(perfilActivity.this,nombreUsuario+apellidoUsuario+correoUsuario+contrasenaUsuario,Toast.LENGTH_SHORT).show();
                                                        actualizar.setEnabled(true);
                                                        startActivity(new Intent(perfilActivity.this, MainActivity.class));
                                                    }


                                            }
                                        });
                                    }

                                    else
                                        {
                                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                            if(errorCode.equals("ERROR_INVALID_EMAIL"))
                                            {
                                                Toast.makeText(perfilActivity.this, "Este correo es inválido", Toast.LENGTH_SHORT).show();
                                                actualizar.setEnabled(true);
                                                return;
                                            }

                                            if(errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE"))
                                            {
                                                Toast.makeText(perfilActivity.this, "Este correo está en uso", Toast.LENGTH_SHORT).show();
                                                actualizar.setEnabled(true);
                                                return;
                                            }

                                        }
                                }
                            });


                }


                /*dbEdit.child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener()
                {



                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.exists())
                        {







                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });*/


            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null)
        {
            perfilUri = data.getData();
            perfilImg.setImageURI(perfilUri);

            /*final StorageReference reference = storage.getReference().child("foto_perfil")
                    .child(FirebaseAuth.getInstance().getUid());*/

            /*storageReference = storage.getReference().child("foto_perfil")
                    .child(FirebaseAuth.getInstance().getUid());*/

            /*storageReference.putFile(perfilUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(perfilActivity.this,"Subido",Toast.LENGTH_SHORT).show();

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            dba.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("perfilImg").setValue(uri.toString());
                            Toast.makeText(perfilActivity.this,"Foto de Perfil Actualizada", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                /*Intent intent = new Intent(VerTodoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;*/

                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
