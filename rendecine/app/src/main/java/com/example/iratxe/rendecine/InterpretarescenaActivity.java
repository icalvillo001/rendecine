package com.example.iratxe.rendecine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iratxe.rendecine.model.Interpretar;
import com.example.iratxe.rendecine.model.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by aitor on 5/01/17.
 */
public class InterpretarescenaActivity  extends AppCompatActivity{

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    Interpretar interpretar = new Interpretar();


    public static final int VIDEO_REQUEST_CODE=1;
     String [] videoURL= {"http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpretarescena);
        //makeVideo();
        enlaceVideo();
    }

    int videoPosicionEscena=0;
    public void makeVideo(){

        VideoView videoEscena=(VideoView)findViewById(R.id.escenaVideo);
        videoEscena.setVideoURI(Uri.parse(interpretar.getInterList().get(videoPosicionEscena).getSrcVideo()));
        videoPosicionEscena++;
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
        mediacontroller.setAnchorView(videoEscena);
        videoEscena.setMediaController(mediacontroller);
    }

    public void enlaceVideo(){

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{

                    JSONObject json = rest.getJSON(String.format("requestInter"));

                    //Se coge las diferentes opciones y los datos necesario
                    JSONArray array = json.getJSONArray("interpretacion");
                    for(int i=0;i<array.length();i++) {
                        JSONObject itemJSON = array.getJSONObject(i);

                        Interpretar.inter inter= new Interpretar.inter();

                        inter.setSrcVideo(itemJSON.getString("srcVideo"));

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
                makeVideo();
                super.onPostExecute(aVoid);
            }
        }.execute();


    }

    public void grabarVideo(View view){

        //Se verifica que hay una camara en el dispositivo
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,R.string.no_camera,Toast.LENGTH_SHORT).show();
        else{
            //Con el intent se llamara a la aplicacion externa de camara
            //El videose guardara en un path por defecto
            //con nombre tambien por defecto
            //Quiero que se guarde en un path concreto con un nombre concreto para luego poder reproducir el video

            //Defino el nombre del video y el path a guardar
            File mediaFile =
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myvideo.mp4");

            //Lanzo el intent
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if(intent.resolveActivity(getPackageManager())!=null) {
                //Si hay camara, se pasa el path y nombre de archivo
                Uri videoUri = Uri.fromFile(mediaFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);

            }else
                Toast.makeText(this,R.string.no_app,Toast.LENGTH_SHORT).show();
        }
    }
    //Cuando se haga startActivityForResult o se cancele, se llamara al siguiente metodo.Este metodo
    //Indicara si se ha grabado bien y el path/nombre del video.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_REQUEST_CODE) {
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


    public void siguienteInterpretarE(View view){

        if(videoPosicionEscena==interpretar.getInterList().size()){
            Intent intent=new Intent(this,PrincipalActivity.class);
            startActivity(intent);
        }else{
            makeVideo();
        }

    }
}
