package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.iratxe.rendecine.R;

/**
 * Created by aitor on 3/01/17.
 */
public class JuegosActivity extends AppCompatActivity {

    //Se define variable para rellenar la parte de arriba de la pantalla con el nombre introducido por el usuario
    public final static String EXTRA_LOGIN = "com.example.docencia.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        
    }

    public void escena(View view){
        Intent intent=new Intent(this,EscenaActivity.class);
        startActivity(intent);

    }

    public void frase(View view){
        Intent intent=new Intent(this,FraseActivity.class);
        startActivity(intent);

    }
}
