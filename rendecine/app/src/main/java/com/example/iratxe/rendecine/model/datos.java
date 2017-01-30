package com.example.iratxe.rendecine.model;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 4/01/17.
 */
public class datos extends AsyncTask<Void,Void,Interpretar>{

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");


    @Override
    protected Interpretar doInBackground(Void... voids) {

        Interpretar interp = new Interpretar();

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
                interp.getInterList().add(inter);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return interp;

    }


    protected void onPostExecute(Interpretar interp) {

        super.onPostExecute(interp);

    }
      ;



}
