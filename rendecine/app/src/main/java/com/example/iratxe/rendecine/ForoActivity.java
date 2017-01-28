package com.example.iratxe.rendecine;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ForoActivity extends AppCompatActivity {

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    JSONArray array = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        mostrarForo();

    }

    public void mostrarForo(){

        Bundle extras = getIntent().getExtras();
        final String nombre= extras.getString("nombre");
        int men= extras.getInt("totalMen");

        //Se llama al servidor
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{

                    //nombre.replace(" ","%20");
                    JSONObject json = rest.getJSON(String.format("requestForo?nombre=%s",nombre));

                    //Se coge las diferentes opciones y los datos necesario
                    array = json.getJSONArray("foro");

                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                LinearLayout layout=(LinearLayout) findViewById(R.id.layoutForo);

                try {
                    for (int i = 0; i < array.length(); i++) {

                        //Se visualizan los mensajes del foro

                        //Se indica la orientacion en vertical porque si no se pone en horizontal
                        layout.setOrientation(LinearLayout.VERTICAL);

                        //Se crean los diferentes text view y se rellenan
                        JSONObject itemJSON = array.getJSONObject(i);

                        TextView usuario= new TextView(ForoActivity.this);
                        usuario.setText("___________________________________________________ \n Usuario: ");
                        usuario.setTextSize(16);
                        usuario.setTextColor(Color.BLACK);

                        TextView text0 = new TextView(ForoActivity.this);
                        text0.setTextSize(14);
                        text0.setText(itemJSON.getString("usuario"));

                        TextView asunto = new TextView(ForoActivity.this);
                        asunto.setText("Asunto: ");
                        asunto.setTextSize(16);
                        asunto.setTextColor(Color.BLACK);

                        TextView mensaje = new TextView(ForoActivity.this);
                        mensaje.setText("Mensaje: ");
                        mensaje.setTextSize(16);
                        mensaje.setTextColor(Color.BLACK);

                        TextView text = new TextView(ForoActivity.this);
                        text.setTextSize(14);
                        text.setText(itemJSON.getString("asunto"));


                        TextView text1 = new TextView(ForoActivity.this);
                        text1.setTextSize(14);
                        text1.setText(String.format("%s \n ____________________________________________________________",itemJSON.getString("mensaje")));

                        //Se añade los text al layout
                        layout.addView(usuario);
                        layout.addView(text0);
                        layout.addView(asunto);
                        layout.addView(text);
                        layout.addView(mensaje);
                        layout.addView(text1);


                    }
                    Button boton = new Button(ForoActivity.this);
                    boton.setText(R.string.escribir);
                    boton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            escribirForo(view);
                        }
                    });

                    layout.addView(boton);

                }catch (JSONException e){
                    e.printStackTrace();
                }


                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    public void escribirForo(View v){

        Intent intent = new Intent(this, EscribirActivity.class);

        //Se pasa el nombre del foro a crear
        Bundle extras = getIntent().getExtras();
        intent.putExtra("nombre",extras.getString("nombre"));
        //Se lanza la actividad
        startActivity(intent);

    }
}
