package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.iratxe.rendecine.R;
import com.example.iratxe.rendecine.model.RestClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void castellano(View v){

        cambiarIdioma(1);

        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }
    public void euskera(View v){

        cambiarIdioma(0);

        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }


    public void cambiarIdioma(int a){

        if(a==1){

            //Se define el idioma que se quiere
            Locale locale = new Locale("es");
            Locale.setDefault(locale);

            //Se carga la nueva configuracion
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }else{

            //It selects a new locale from the language string that was passed in, and then updates the app’s resource files.
            Locale locale = new Locale("eu");
            Locale.setDefault(locale);

            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());


        }
    }

}
