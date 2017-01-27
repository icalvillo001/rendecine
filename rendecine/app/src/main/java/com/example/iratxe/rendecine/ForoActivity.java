package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
                RadioGroup group = (RadioGroup)findViewById(R.id.prueba);
                try {
                    for(int i=0; i<array.length();i++) {

                        JSONObject itemJSON = array.getJSONObject(i);


                        TextView text = new TextView(ForoActivity.this);
                        text.setText(itemJSON.getString("mensaje"));
                        TextView text1 = new TextView(ForoActivity.this);
                        text1.setText(itemJSON.getString("asunto"));

                        RelativeLayout layout=(RelativeLayout) findViewById(R.id.layoutForo);
                        layout.addView(text);
                        layout.addView(text1);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }


                super.onPostExecute(aVoid);
            }
        }.execute();

    }

    public void escribir(View view){
       // Intent intent = new Intent(this,);
    }
}
