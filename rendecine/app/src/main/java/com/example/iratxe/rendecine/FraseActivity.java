package com.example.iratxe.rendecine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.iratxe.rendecine.model.Frases;
import com.example.iratxe.rendecine.model.Usuarios;
import com.example.iratxe.rendecine.model.datos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by aitor on 4/01/17.
 */
public class FraseActivity extends AppCompatActivity {

    public static String[] preguntasImagen={"La vida es bella","Peter Pan","300","0","El señor de los anillos","El libro de la selva","Siete almas","1"};
    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    ImageView image1;
    Frases frases = new Frases();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frase);
        image1=(ImageView)findViewById(R.id.imagenFrase);

        cargarDatos();

    }

   public void cargarDatos(){

       //Llamada al servidor para que coja la lista con las imagenes y opciones del test.
       new AsyncTask<Void,Void,Void>() {
           @Override
           protected Void doInBackground(Void... voids) {
               try{

                   JSONObject json = rest.getJSON(String.format("requestFrase"));

                   //Se coge las diferentes opciones y los datos necesario
                   JSONArray array = json.getJSONArray("frase");
                   for(int i=0;i<array.length();i++) {
                       JSONObject itemJSON = array.getJSONObject(i);

                       Frases.Frase frase = new Frases.Frase();

                       frase.setSrcImg(itemJSON.getString("srcImg"));
                       frase.setOpc1(itemJSON.getString("opc1"));
                       frase.setOpc2(itemJSON.getString("opc2"));
                       frase.setOpc3(itemJSON.getString("opc3"));
                       frase.setSol(itemJSON.getString("sol"));

                       frases.getFraseList().add(frase);

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
               mostrarDatos();
               super.onPostExecute(aVoid);
           }
       }.execute();


    }
    int posFrase=0;
    public void mostrarDatos(){
        try{


            DownloadImageTask d=new DownloadImageTask();
            image1.setImageBitmap(d.execute(frases.getFraseList().get(posFrase).getSrcImg()).get());

            RadioGroup group = (RadioGroup) findViewById(R.id.frase_choices);

            RadioButton radio = new RadioButton(this);
            RadioButton radio1 = new RadioButton(this);
            RadioButton radio2 = new RadioButton(this);

            int id=0;
            radio.setText(frases.getFraseList().get(posFrase).getOpc1());
            radio.setId(id);

            radio1.setText(frases.getFraseList().get(posFrase).getOpc2());
            radio1.setId(id+1);

            radio2.setText(frases.getFraseList().get(posFrase).getOpc3());
            radio2.setId(id+2);


            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_corregirFrase).setVisibility(View.VISIBLE);
                }
            });

            radio1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_corregirFrase).setVisibility(View.VISIBLE);
                }
            });

            radio2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_corregirFrase).setVisibility(View.VISIBLE);
                }
            });

            group.addView(radio);
            group.addView(radio1);
            group.addView(radio2);

            posFrase++;

        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }

    public int comprobarCorrecta(){

        int correcta=0;

        if(frases.getFraseList().get(posFrase-1).getOpc1().equals(frases.getFraseList().get(posFrase-1).getSol())){
            correcta=0;
        }else if (frases.getFraseList().get(posFrase-1).getOpc2().equals(frases.getFraseList().get(posFrase-1).getSol())){
            correcta=1;
        }else{
            correcta=2;
        }
        return correcta;
    }

    public void corregirImagen(View view){

        int posicionCorrecta=comprobarCorrecta();

        RadioGroup group = (RadioGroup)findViewById(R.id.frase_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        findViewById(R.id.button_siguienteFrase).setVisibility(View.VISIBLE);

        group.getChildAt(posicionCorrecta).setBackgroundColor(Color.GREEN);

        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
            int select= group.getCheckedRadioButtonId();

            if(select!=posicionCorrecta){
                group.getChildAt(select).setBackgroundColor(Color.RED);
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }

    }

    public void siguienteImagen(View view){

        RadioGroup group = (RadioGroup) findViewById(R.id.frase_choices);
        group.removeAllViews();

        if(posFrase==frases.getFraseList().size()){
            Toast.makeText(this,
                    "Juego acabado!",
                    Toast.LENGTH_SHORT);
            irPrincipal();
        }else{
            findViewById(R.id.button_siguienteFrase).setVisibility(View.INVISIBLE);
            mostrarDatos();

        }
    }

    public void irPrincipal(){
        Intent intent=new Intent(this,PrincipalActivity.class);
        startActivity(intent);

    }
}
