package com.example.iratxe.rendecine.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iratxe.rendecine.R;
import com.example.iratxe.rendecine.model.RestClient;
import com.example.iratxe.rendecine.model.Usuarios;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 3/01/17.
 */
public class InicioActivity  extends AppCompatActivity {



    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
    public void login(View view){

        String login=((EditText)findViewById(R.id.nombreInicioUsu2)).getText().toString();
        String passwd=((EditText)findViewById(R.id.contraInicioUsu2)).getText().toString();

        if(login!=null && passwd !=null) {
            authenticate(login, passwd);

        }else {
            Toast.makeText(
                    this,
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void authenticate(final String login,final String passwd) {

        new AsyncTask<Void,Void,Usuarios>() {
            @Override
            protected Usuarios doInBackground(Void... voids) {
                Usuarios usuarios = new Usuarios();
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

                return usuarios;
            }

            @Override
            protected void onPostExecute(Usuarios usuarios) {
                for(int i=0; i<usuarios.getUsuarios().size();i++)
                {
                    if(login.equals(usuarios.getUsuarios().get(i).getNombre())){

                        Toast.makeText(
                                InicioActivity.this,
                                "Acceso correcto",
                                Toast.LENGTH_SHORT
                        ).show();

                        //Se guarda el nombre de usuario en shared preferences ya que se utilizara para el
                        //registro de mensajes en el foro
                        SharedPreferences datos =
                                getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = datos.edit();
                        editor.putString("nombreUsuario", login);
                        editor.commit();

                        Intent intent=new Intent(InicioActivity.this,PrincipalActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(
                                InicioActivity.this,
                                "Login incorrecto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }



            }
        }.execute();
    }
}
