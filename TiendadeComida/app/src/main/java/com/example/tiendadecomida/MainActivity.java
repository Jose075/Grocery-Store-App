package com.example.tiendadecomida;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.activities.LoginActivity;
import com.example.tiendadecomida.activities.perfilActivity;
import com.example.tiendadecomida.ui.modeloUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.tiendadecomida.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth autenticar;
    FirebaseDatabase dba;
    Button boton;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        autenticar = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dba = FirebaseDatabase.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categoria, R.id.nav_perfil,R.id.nav_ofertas,R.id.nav_nuevos_productos,
                R.id.nav_pedidos,R.id.nav_carrito)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);
        TextView nombreDrawner = headerView.findViewById(R.id.nombre_drawner);
        TextView correoDrawner = headerView.findViewById(R.id.correo_drawner);
        CircleImageView imgDrawner = headerView.findViewById(R.id.drawner_img);
        boton = headerView.findViewById(R.id.cerrarSesion);
        String apellido;



        dba.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        modeloUsuario modeloUsuario = snapshot.getValue(modeloUsuario.class);

                        if(snapshot.hasChild("perfilImg"))
                        {
                            Glide.with(MainActivity.this).load(modeloUsuario.getPerfilImg()).into(imgDrawner);
                        }
                        nombreDrawner.setText(modeloUsuario.getNombre() + " " + modeloUsuario.getApellido());
                        correoDrawner.setText(modeloUsuario.getCorreo());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });



        drawer.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset)
            {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView)
            {
                dba.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                modeloUsuario modeloUsuario = snapshot.getValue(modeloUsuario.class);

                                if(snapshot.hasChild("perfilImg"))
                                {
                                    Glide.with(MainActivity.this).load(modeloUsuario.getPerfilImg()).into(imgDrawner);
                                }
                                nombreDrawner.setText(modeloUsuario.getNombre() + " " + modeloUsuario.getApellido());
                                correoDrawner.setText(modeloUsuario.getCorreo());



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {

                            }
                        });
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView)
            {

            }

            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
        });

    }




    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cerrarSesion) {
            autenticar.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                autenticar.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
    }
}