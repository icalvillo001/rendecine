package com.example.iratxe.rendecine;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 4/01/17.
 */
public class EscenaActivity extends AppCompatActivity {

    public static String[] preguntas = {"Gladiator", "300", "Peter Pan","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","0","Lavida es bella"," La vida es bella","La vida es bella","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","1"};
    String[] advise={"http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/RendecineBD/rest/rendecine");
    final Escenas escena = new Escenas();
    final Escenas.Escena escena1 = new Escenas.Escena();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escena);
        cargarVideo();
        makeTest();
    }

    public void cargarTest(){
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                   // String login="12345678A";
                    //String passwd="tta";
                    //rest.setHttpBasicAuth(login,passwd);


                    //Se solicita los datos del test al servidor
                    JSONObject json = rest.getJSON(String.format("requestEscena"));

                    //Se coge las diferentes opciones y los datos necesarios
                    JSONArray array = json.getJSONArray("escena");
                    for(int i=0;i<array.length();i++){
                        JSONObject itemJSON = array.getJSONObject(i);
                        escena1.setIdEscena(itemJSON.getInt("idEscena"));
                        escena1.setOpc1(itemJSON.getString("opc1"));
                        escena1.setOpc2(itemJSON.getString("opc2"));
                        escena1.setOpc3(itemJSON.getString("opc3"));
                        escena1.setSol(itemJSON.getString("sol"));
                        escena1.setSrcVideo(itemJSON.getString("srcVideo"));
                        escena.getEscenaList().add(escena1);
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

                //Se visualiza el enunciado del test
                RadioButton radio1=(RadioButton)findViewById(R.id.radio1);
                radio1.setText(escena.getEscenaList().get(0).getOpc1());
                radio1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
                    }
                });
                RadioButton radio2=(RadioButton)findViewById(R.id.radio2);
                radio2.setText(escena.getEscenaList().get(1).getOpc2());
                radio2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
                    }
                });
                RadioButton radio3=(RadioButton)findViewById(R.id.radio3);
                radio3.setText(escena.getEscenaList().get(2).getOpc3());
                radio3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
                    }
                });
                cargarVideo();


                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    int posEscena=0;
    public void makeTest() {
        int id=0;
        RadioButton radio1=(RadioButton)findViewById(R.id.radio1);
        radio1.setText(escena.getEscenaList().get(0).getOpc1());
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
        RadioButton radio2=(RadioButton)findViewById(R.id.radio2);
        radio2.setText(escena.getEscenaList().get(1).getOpc2());
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
        RadioButton radio3=(RadioButton)findViewById(R.id.radio3);
        radio3.setText(escena.getEscenaList().get(2).getOpc3());
        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
            }
        });
    }

    int videoPosicion=0;
    public void cargarVideo(){

       // VideoView video =new VideoView(this);
        VideoView video= (VideoView)findViewById(R.id.VideoTest);

        //video.setId(0);
        //video.setVideoURI(Uri.parse(advise[0]));
       // video.setVideoURI(Uri.parse(preguntas[videoPosicion]));
        video.setVideoURI(Uri.parse(escena.getEscenaList().get(0).getSrcVideo()));
        videoPosicion=videoPosicion+1;
        //   ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //  video.setLayoutParams(params);
        MediaController mediacontroller = new MediaController(this) {
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


      //  LinearLayout layout=(LinearLayout)findViewById(R.id.escenaLayout);
       // layout.addView(video);

    }
    int posicionCorrecta=4;
    public void corregir(View view){
        RadioGroup group = (RadioGroup)findViewById(R.id.escena_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        findViewById(R.id.button_siguiente).setVisibility(View.VISIBLE);

        int f= Integer.parseInt(preguntas[posicionCorrecta]);
        posicionCorrecta=posicionCorrecta+5;
        group.getChildAt(f).setBackgroundColor(Color.GREEN);

        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
            int select= group.getCheckedRadioButtonId();
            if(select!=f){
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

        if((posEscena)==preguntas.length){
            irPrincipalEscena();
        }else{
            findViewById(R.id.button_siguiente).setVisibility(View.INVISIBLE);
            cargarVideo();
            makeTest();
        }
    }

    public void irPrincipalEscena(){
        Intent intent=new Intent(this,PrincipalActivity.class);
        startActivity(intent);
    }
}
