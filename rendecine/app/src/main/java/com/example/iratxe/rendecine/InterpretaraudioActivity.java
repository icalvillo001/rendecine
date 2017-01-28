package com.example.iratxe.rendecine;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iratxe.rendecine.model.Interpretar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by aitor on 5/01/17.
 */
public class InterpretaraudioActivity extends AppCompatActivity {

    public static final int AUDIO_REQUEST_CODE=1;

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    Interpretar interpretar = new Interpretar();

    String [] videoURL= {"http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpretaraudio);
        //makeInterpretarAudio();
        enlaceVideo();

    }
    int videoPosicion=0;
    public void makeInterpretarAudio(){

        VideoView video= (VideoView)findViewById(R.id.interpretarAudio);

        //video.setVideoURI(Uri.parse(videoURL[videoPosicion]));
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

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{


                    //Se hace la peticion al servidor
                    JSONObject json = rest.getJSON(String.format("requestInter"));

                    //Se coge las diferentes opciones y los datos necesario
                    JSONArray array = json.getJSONArray("interpretacion");
                    for(int i=0;i<array.length();i++) {
                        JSONObject itemJSON = array.getJSONObject(i);

                        Interpretar.inter inter= new Interpretar.inter();

                        inter.setSrcVideo(itemJSON.getString("srcVideo"));

                        //Se añaden los videos a la lista que se utiliza en el metodo makeVideo para visualizar
                        interpretar.getInterList().add(inter);
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
                makeInterpretarAudio();
                super.onPostExecute(aVoid);
            }
        }.execute();


    }
    public void grabarAudio(View view){

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            Toast.makeText(this,R.string.no_micro,Toast.LENGTH_SHORT).show();
        else{
            //Defino el nombre del video y el path a guardar
            File mediaFile =
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myrecord.mp4");

            //Lanzo vel intent
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

            if(intent.resolveActivity(getPackageManager())!=null) {
                Uri audioUri = Uri.fromFile(mediaFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, audioUri);
                startActivityForResult(intent, AUDIO_REQUEST_CODE);
            }else
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();
        }
    }

    public void siguienteInterpretarA(View view) {

        if(videoPosicion==videoURL.length){
            Intent intent=new Intent(this,PrincipalActivity.class);
            startActivity(intent);
        }else{
            makeInterpretarAudio();
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUDIO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


}
