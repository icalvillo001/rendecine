package com.example.iratxe.rendecine;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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


            Locale locale = new Locale("eu");
            Locale.setDefault(locale);

            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());


        }
    }

}
