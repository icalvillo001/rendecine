package com.example.iratxe.rendecine;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void castellano(View v){
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }
    public void euskera(View v){
        //Intent intent=new Intent(this,RegistroActivity.class);
        Intent intent=new Intent(this,peliculasActivity.class);
        startActivity(intent);
    }

    public void escribir(View v){

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    String men="hola";
                    String asun="hola";
                    JSONObject json2 = new JSONObject();
                    json2.put("mensaje",men);
                    json2.put("asunto",asun);
                    rest.postJson(json2, "addMensaje?nombre=Titanic");


                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                Intent intent=new Intent(MainActivity.this,InicioActivity.class);
                startActivity(intent);

                super.onPostExecute(aVoid);
            }
        }.execute();

    }
}
