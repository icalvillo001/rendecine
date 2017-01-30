package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iratxe.rendecine.R;
import com.example.iratxe.rendecine.model.Interpretar;
import com.example.iratxe.rendecine.model.RestClient;
import com.example.iratxe.rendecine.model.datos;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by aitor on 5/01/17.
 */
public class InterpretaraudioActivity extends AppCompatActivity {

    public static final int AUDIO_REQUEST_CODE=1;

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    Interpretar interpretar = new Interpretar();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpretaraudio);
        enlaceVideo();


    }
    int videoPosicion=0;
    public void makeInterpretarAudio(){

        VideoView video= (VideoView)findViewById(R.id.interpretarAudio);

        video.setVideoURI(Uri.parse(interpretar.getInterList().get(videoPosicion).getSrcVideo()));
        videoPosicion++;

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
    }


    public void enlaceVideo(){

        try{
            datos d = new datos();
            interpretar= d.execute().get();
            makeInterpretarAudio();

        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }


    }
    public void grabarAudio(View view){

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            Toast.makeText(this,R.string.no_micro,Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if(intent.resolveActivity(getPackageManager())!=null){

                startActivityForResult(intent,AUDIO_REQUEST_CODE);
            }

            else
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();

        }
    }

    public void siguienteInterpretarA(View view) {

        if(videoPosicion==interpretar.getInterList().size()){
            Intent intent=new Intent(this,PrincipalActivity.class);
            startActivity(intent);
        }else{
            findViewById(R.id.reproducirAudio).setVisibility(View.INVISIBLE);
            findViewById(R.id.button_siguienteInterpretarA).setVisibility(View.INVISIBLE);
            LinearLayout layout=(LinearLayout)findViewById(R.id.audio);
            layout.removeAllViews();
            makeInterpretarAudio();
        }

    }



    public void reproducir(Intent data){
        View audio=new View(this);
        Uri url = data.getData();


        final AudioPlayer audioPlayer=new AudioPlayer(audio);

        try {
            audioPlayer.setAudioUri(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout layout=(LinearLayout)findViewById(R.id.audio);
        layout.addView(audio);

        Button c=(Button)findViewById(R.id.button_siguienteInterpretarA);
        c.setVisibility(View.VISIBLE);
        c.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                audioPlayer.quitar();
                siguienteInterpretarA(view);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == AUDIO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                findViewById(R.id.reproducirAudio).setVisibility(View.VISIBLE);

                Toast.makeText(this, "Audio saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                Button b= (Button)findViewById(R.id.reproducirAudio);


                b.setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        reproducir(data);
                    }
                });



            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record audio",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


}
