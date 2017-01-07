package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        /*        Intent intent=getIntent();
        TextView textLogin=(TextView)findViewById(R.id.menu_login);
        textLogin.setText(intent.getStringExtra(EXTRA_LOGIN));*/
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
