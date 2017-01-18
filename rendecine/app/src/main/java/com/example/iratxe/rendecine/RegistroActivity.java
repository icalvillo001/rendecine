package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iratxe.rendecine.model.Usuario;
import com.example.iratxe.rendecine.model.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by aitor on 3/01/17.
 */
public class RegistroActivity extends AppCompatActivity {

    String login;
    String passwd;
    String email;

    RestClient rest= new RestClient("http://u017633.ehu.eus:28080/rendecineBD/rest/Rendecine");

    Usuarios usuarios = new Usuarios();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void login(View view){


        login=((EditText)findViewById(R.id.nombreInicioUsu)).getText().toString();
        passwd=((EditText)findViewById(R.id.contraInicioUsu)).getText().toString();
        email=((EditText)findViewById(R.id.emailInicioUsu)).getText().toString();

        if(login!=null && passwd !=null && email!=null) {

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

        //Se comprueba en el servidor que existe ese usuario
        new AsyncTask<Void,Void,Void>() {
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
                for(int i=0; i<usuarios.getUsuarios().size();i++)
                {
                    if(login.equals(usuarios.getUsuarios().get(i).getNombre())){

                        Toast.makeText(
                                RegistroActivity.this,
                                "Acceso correcto",
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent=new Intent(RegistroActivity.this,PrincipalActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(
                                RegistroActivity.this,
                                "Login incorrecto",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }


                super.onPostExecute(aVoid);
            }
        }.execute();


    }
    public void inicioLogin(View view){
        Intent intent = new Intent(this,InicioActivity.class);
        startActivity(intent);


    }
}
