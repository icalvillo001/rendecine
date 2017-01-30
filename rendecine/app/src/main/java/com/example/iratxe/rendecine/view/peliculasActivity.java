package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


import com.example.iratxe.rendecine.R;
import com.example.iratxe.rendecine.model.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;




public class peliculasActivity extends AppCompatActivity {

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        mostrarPeliculas();
    }

    //Metodo para consultar todas las peliculas que hay en el servidor que sera de las que haya foro
    public void mostrarPeliculas(){


        new AsyncTask<Void,Void,JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... voids) {
                JSONArray array = new JSONArray();
                try{

                    //Se envia la peticion al servidor
                    JSONObject json = rest.getJSON(String.format("requestPeliculas"));

                    //Se guarda en un array la lista de peliculas
                    array = json.getJSONArray("pelicula");

                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                //Se devuelve el array al hilo principal
                return array;
            }

            @Override
            protected void onPostExecute(JSONArray array) {

                //Se define un radiogroup donde se añadiran los botones para los distintos foros
                RadioGroup group = (RadioGroup)findViewById(R.id.prueba);
                group.setGravity(Gravity.CENTER);

                try {

                    //Se recorre el array
                    for(int i=0; i<array.length();i++) {

                        JSONObject itemJSON = array.getJSONObject(i);

                        //Se añade un boton por foro con el nombre de la pelicula que se encuentra en el array
                        Button button = new Button(peliculasActivity.this);
                        final String nombre = itemJSON.getString("nombre");
                        final int men= itemJSON.getInt("totalMen");
                        button.setText(nombre);

                        //Cuando el usuario pulse el boton se creara la pagina con los mensajes del foro de esa pelicula
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               crearForo(nombre, men);
                            }
                        });

                        //Se añaden los botones al layout para que los vea el usuario
                        group.addView(button);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }.execute();



    }

    //Metodo para crear el foro. Se cambia de actividad y se pasa el nombre de la pelicula, es decir, del foro a crear
    public void crearForo(String nombre, int men){

        Intent intent = new Intent(this, ForoActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("totalMen",men);
        startActivity(intent);

    }
}
