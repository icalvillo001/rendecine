package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iratxe.rendecine.R;
import com.example.iratxe.rendecine.model.Escenas;
import com.example.iratxe.rendecine.model.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 4/01/17.
 */
public class EscenaActivity extends AppCompatActivity {


    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    Escenas escenas = new Escenas();
    int posEscena =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escena);
        cargarDatosEscena();
    }

    public void cargarDatosEscena(){

        //Llamada al servidor para que coja la lista con las imagenes y opciones del test.
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{

                    JSONObject json = rest.getJSON(String.format("requestEscenas"));

                    //Se coge las diferentes opciones y los datos necesario
                    JSONArray array = json.getJSONArray("escena");
                    for(int i=0;i<array.length();i++) {
                        JSONObject itemJSON = array.getJSONObject(i);

                        Escenas.Escena escena = new Escenas.Escena();

                        escena.setSrcVideo(itemJSON.getString("srcVideo"));
                        escena.setOpc1(itemJSON.getString("opc1"));
                        escena.setOpc2(itemJSON.getString("opc2"));
                        escena.setOpc3(itemJSON.getString("opc3"));
                        escena.setSol(itemJSON.getString("sol"));

                        escenas.getEscenaList().add(escena);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mostrarDatosEscena();
                super.onPostExecute(aVoid);
            }
        }.execute();


    }

    public void mostrarDatosEscena(){

        cargarVideo();

        RadioGroup group = (RadioGroup) findViewById(R.id.escena_choices);

        int id=0;

        RadioButton radio = new RadioButton(this);
        radio.setText(escenas.getEscenaList().get(posEscena).getOpc1());
        radio.setId(id);
        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
        group.addView(radio);

        RadioButton radio1 = new RadioButton(this);
        radio1.setText(escenas.getEscenaList().get(posEscena).getOpc2());
        radio1.setId(id+1);
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
        group.addView(radio1);

        RadioButton radio2 = new RadioButton(this);
        radio2.setText(escenas.getEscenaList().get(posEscena).getOpc3());
        radio2.setId(id+2);
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
        group.addView(radio2);

        posEscena++;


    }


    public void cargarVideo(){


        VideoView video= (VideoView)findViewById(R.id.VideoTest);

        video.setVideoURI(Uri.parse(escenas.getEscenaList().get(posEscena).getSrcVideo()));


        final MediaController mediacontroller = new MediaController(this) {
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };

        mediacontroller.setAnchorView(video);
        video.setMediaController(mediacontroller);


    }
    public int comprobarCorrecta() {

        int correcta = 0;

        if (escenas.getEscenaList().get(posEscena - 1).getOpc1().equals(escenas.getEscenaList().get(posEscena - 1).getSol())) {
            correcta = 0;
        } else if (escenas.getEscenaList().get(posEscena - 1).getOpc2().equals(escenas.getEscenaList().get(posEscena - 1).getSol())) {
            correcta = 1;
        } else {
            correcta = 2;
        }
        return correcta;
    }
    public void corregir(View view){

        RadioGroup group = (RadioGroup)findViewById(R.id.escena_choices);

        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        findViewById(R.id.button_siguiente).setVisibility(View.VISIBLE);

        int correcta=comprobarCorrecta();
        group.getChildAt(correcta).setBackgroundColor(Color.GREEN);

        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
            int select= group.getCheckedRadioButtonId();
            if(select!=correcta){
                group.getChildAt(select).setBackgroundColor(Color.RED);
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }
    }

    public void siguiente(View v){
        RadioGroup group = (RadioGroup) findViewById(R.id.escena_choices);
        group.removeAllViews();

        if((escenas.getEscenaList().size())==posEscena){
            irPrincipalEscena();
        }else{
            findViewById(R.id.button_siguiente).setVisibility(View.INVISIBLE);
            findViewById(R.id.button_corregir).setVisibility(View.INVISIBLE);
            mostrarDatosEscena();

        }
    }

    public void irPrincipalEscena(){
        Intent intent=new Intent(this,PrincipalActivity.class);
        startActivity(intent);
    }
}
