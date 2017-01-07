package com.example.iratxe.rendecine;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by aitor on 4/01/17.
 */
public class EscenaActivity extends AppCompatActivity {

    public static String[] preguntas = {"Gladiator", "300", "Peter Pan","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","0","Lavida es bella"," La vida es bella","La vida es bella","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","1"};
    String[] advise={"http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escena);
        cargarVideo();
        makeTest();
    }
    int posEscena=0;
    public void makeTest() {
        int id=0;
        RadioGroup group = (RadioGroup) findViewById(R.id.escena_choices);
        for (int a=posEscena; a<posEscena+3; a++) {
            RadioButton radio = new RadioButton(this);
            radio.setId(id);
            id=id+1;
            radio.setText(preguntas[a]);
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_corregir).setVisibility(View.VISIBLE);
                }
            });
            group.addView(radio);


        }
        posEscena=posEscena+5;
    }
    int videoPosicion=3;
    public void cargarVideo(){

       // VideoView video =new VideoView(this);
        VideoView video= (VideoView)findViewById(R.id.VideoTest);

        //video.setId(0);
        //video.setVideoURI(Uri.parse(advise[0]));
        video.setVideoURI(Uri.parse(preguntas[videoPosicion]));
        videoPosicion=videoPosicion+5;
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
