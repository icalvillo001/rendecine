package com.example.iratxe.rendecine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 28/01/17.
 */
public class EscribirActivity extends AppCompatActivity {

    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir);
    }

    public void enviarMensaje(View view) {

        //Se coge los datos introducidos por el usuario
        final String asunto = ((EditText) findViewById(R.id.asuntoUsuario)).getText().toString();
        final String mensaje = ((EditText) findViewById(R.id.mensajeUsuario)).getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    //Se crea el objeto json que se envia al servidor
                    JSONObject json2 = new JSONObject();


                    //Se coge el nombre de usuario a insertar en el mensaje de la base de datos del introducido por el usuario
                    SharedPreferences prefs =
                            getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);

                    String usuario= prefs.getString("nombreUsuario","null");


                    json2.put("usuario",usuario);
                    json2.put("mensaje", mensaje);
                    json2.put("asunto", asunto);
                    //Se coge el nombre de la pelicula para relacionarla con el mensaje del usuario
                    //Este nombre se pasa a esta actividad con el Bundle
                    Bundle extras = getIntent().getExtras();

                    //Se envia el mensaje al servidor para que lo guarde en la base de datos
                    rest.postJson(json2,String.format("addMensaje?nombre=%s",extras.getString("nombre")));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                Intent intent = new Intent(EscribirActivity.this, peliculasActivity.class);
                startActivity(intent);

                super.onPostExecute(aVoid);
            }


        }.execute();
    }
}
