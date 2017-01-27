package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.iratxe.rendecine.model.Peliculas;
import com.example.iratxe.rendecine.model.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.acl.Group;



public class peliculasActivity extends AppCompatActivity {

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    //JSONArray array = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        mostrarPeliculas();
    }
    public void mostrarPeliculas(){

        //Consultamos las peliculas en el servidor
        new AsyncTask<Void,Void,JSONArray>() {
            @Override
            protected JSONArray doInBackground(Void... voids) {
                JSONArray array = new JSONArray();
                try{

                    JSONObject json = rest.getJSON(String.format("requestPeliculas"));
                    //Se coge las diferentes opciones y los datos necesario
                    array = json.getJSONArray("pelicula");

                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                return array;
            }

            @Override
            protected void onPostExecute(JSONArray array) {
                RadioGroup group = (RadioGroup)findViewById(R.id.prueba);
                try {
                    for(int i=0; i<array.length();i++) {

                        JSONObject itemJSON = array.getJSONObject(i);

                        Button button = new Button(peliculasActivity.this);
                        final String nombre = itemJSON.getString("nombre");
                        final int men= itemJSON.getInt("totalMen");
                        button.setText(nombre);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               crearForo(nombre, men);
                            }
                        });
                        group.addView(button);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }



            }
        }.execute();



    }
    public void crearForo(String nombre, int men){

        Intent intent = new Intent(this, ForoActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("totalMen",men);
        startActivity(intent);

    }
}
