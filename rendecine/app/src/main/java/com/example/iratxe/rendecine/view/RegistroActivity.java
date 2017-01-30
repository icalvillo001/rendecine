package com.example.iratxe.rendecine.view;

import android.content.Intent;
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
public class RegistroActivity extends AppCompatActivity {


    //Usuarios usuarios = new Usuarios();
    RestClient rest = new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void login(View view) {


        String login = ((EditText) findViewById(R.id.nombreInicioUsu)).getText().toString();
        String passwd = ((EditText) findViewById(R.id.contraInicioUsu)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailInicioUsu)).getText().toString();

        if (login != null && passwd != null && email != null) {
            //Primero se va a verificar si el nombre de usuario existe o no en la base de datos
            //Si no existe se añadira, si existe solicitara de nuevo un nombre
            authenticate(login,passwd,email);

        } else {
            Toast.makeText(
                    this,
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public Usuarios consultarDatos(String login , String passwd) {
        Usuarios usuarios = new Usuarios();

        try {

            //Se consulta en la base de datos si existe o no el usuario
            JSONObject json = rest.getJSON(String.format("requestLogin?nombre=%s&password=%s", login, passwd));

            //Se guardan los datos en una variable de tipo usuarios
            JSONArray array = json.getJSONArray("usuario");
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemJSON = array.getJSONObject(i);

                Usuarios.Usuario usuario = new Usuarios.Usuario();

                usuario.setNombre(itemJSON.getString("nombre"));
                usuario.setPassword(itemJSON.getString("password"));

                usuarios.getUsuarios().add(usuario);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    public boolean guardarDatos(String login, String passwd, String email, Usuarios usuarios) {
        boolean comp = false;

        //Se comprueba si existe o no el usuario
        if(usuarios.getUsuarios().size()==0){
            //No existe en la base de datos y se añade el usuario
            try {

                JSONObject json2 = new JSONObject();
                json2.put("nombre", login);
                json2.put("password", passwd);
                json2.put("email", email);
                rest.postJson(json2, "addUser");
                comp = true;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            for(int i=0;i<usuarios.getUsuarios().size();i++){
                if (login.equals(usuarios.getUsuarios().get(i).getNombre())) {
                    //Ya existe en la base de datos y le solicita que vuelva a intorudcir nombre
                    comp = false;

                } else {
                    comp=true;
                }
            }

        }
       return comp;
    }

    public void authenticate(final String login, final String passwd, final String email) {


        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean comp = false;
                Usuarios usuarios;
                //Se consulta el nombre
                usuarios=consultarDatos(login, passwd);
                //Se guarda el nombre si no existe y devuelve true
                //No se guarda si existe y devuelve false
                comp = guardarDatos(login, passwd, email,usuarios);
                return comp;
            }


            protected void onPostExecute(Boolean comp) {
                if (comp){
                    //Si es true que se cambie de actividad a inicio
                    Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
                    startActivity(intent);


                } else {
                    //Si es false se indica que introduzca otro nombre
                    Toast.makeText(
                            RegistroActivity.this,
                            "El nombre de usuario ya existe",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }

        }.execute();


    }

    public void inicioLogin(View view){
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
}
