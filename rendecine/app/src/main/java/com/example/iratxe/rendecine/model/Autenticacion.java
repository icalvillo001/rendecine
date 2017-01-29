package com.example.iratxe.rendecine.model;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.iratxe.rendecine.InicioActivity;
import com.example.iratxe.rendecine.PrincipalActivity;
import com.example.iratxe.rendecine.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 29/01/17.
 */
public class Autenticacion extends AsyncTask<Void,Void,Void> {
    Usuarios usuarios = new Usuarios();
    String login;
    String passwd;
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    public Autenticacion(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try{

            JSONObject json = rest.getJSON(String.format("requestLogin?nombre=%s&password=%s", login,passwd));

            //Se coge las diferentes opciones y los datos necesario
            JSONArray array = json.getJSONArray("usuario");
            for(int i=0;i<array.length();i++) {
                JSONObject itemJSON = array.getJSONObject(i);

                Usuarios.Usuario usuario = new Usuarios.Usuario();

                usuario.setNombre(itemJSON.getString("nombre"));
                usuario.setPassword(itemJSON.getString("password"));

                usuarios.getUsuarios().add(usuario);
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
        super.onPostExecute(aVoid);
    }
}
