package com.example.iratxe.rendecine.model;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.iratxe.rendecine.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 4/01/17.
 */
public class datos extends AsyncTask<Void,Void,Interpretar>{

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");
    Interpretar interpretar;

    public datos (Interpretar interpretar){
        this.interpretar=interpretar;
    }


    @Override
    protected Interpretar doInBackground(Void... voids) {
        try{

            Interpretar interp = new Interpretar();
            //Se hace la peticion al servidor
            JSONObject json = rest.getJSON(String.format("requestInter"));

            //Se coge las diferentes opciones y los datos necesario
            JSONArray array = json.getJSONArray("interpretacion");
            for(int i=0;i<array.length();i++) {
                JSONObject itemJSON = array.getJSONObject(i);

                Interpretar.inter inter= new Interpretar.inter();

                inter.setSrcVideo(itemJSON.getString("srcVideo"));

                //Se añaden los videos a la lista que se utiliza en el metodo makeVideo para visualizar
                interp.getInterList().add(inter);
            }
        return interp;

        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }


    protected void onPostExecute(Interpretar interp) {
        //makeVideo();
        interpretar=interp;
    }
      ;



}
