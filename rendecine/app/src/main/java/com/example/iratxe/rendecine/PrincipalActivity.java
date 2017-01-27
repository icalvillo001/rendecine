package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by aitor on 3/01/17.
 */
public class PrincipalActivity extends AppCompatActivity {

    //Se define variable para rellenar la parte de arriba de la pantalla con el nombre introducido por el usuario
    public final static String EXTRA_LOGIN = "com.example.docencia.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void juegos(View v){
        Intent intent=new Intent(this,JuegosActivity.class);
        startActivity(intent);

    }

    public void obras(View view){
        Intent intent=new Intent(this,ObrasActivity.class);
        startActivity(intent);
    }

    public void peliculas(View view){
        Intent intent = new Intent(this,peliculasActivity.class);
        startActivity(intent);
    }
}
