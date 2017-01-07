package com.example.iratxe.rendecine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aitor on 3/01/17.
 */
public class InicioActivity  extends AppCompatActivity {
    public static String[] usu={"iratxe"};
    public static String[] con={"iratxe"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
    public void login(View view){


        Intent intent=new Intent(this,PrincipalActivity.class);
        String login=((EditText)findViewById(R.id.nombreInicioUsu2)).getText().toString();
        String passwd=((EditText)findViewById(R.id.contraInicioUsu2)).getText().toString();

        if(login!=null && passwd !=null) {

            Boolean entrar = false;
            entrar = authenticate(login, passwd);
            if (entrar == true) {
                intent.putExtra(PrincipalActivity.EXTRA_LOGIN, login);
                startActivity(intent);
            }
        }else {
            Toast.makeText(
                    this,
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    public boolean authenticate(String login,String passwd) {

        int pos = 0, a = 0, i;
        boolean resul = false;
        for (i = 0; i < usu.length; i++) {
            if (login.toString().equalsIgnoreCase(usu[i])) {
                a = 1;
                pos = i;
            }
        }
        if (a == 1) {
            if (passwd.toString().equalsIgnoreCase(con[pos])) {
                Toast.makeText(
                        this,
                        "Acceso correcto",
                        Toast.LENGTH_SHORT
                ).show();
                resul = true;

            } else {
                Toast.makeText(
                        this,
                        "Acceso incorrecto",
                        Toast.LENGTH_SHORT
                );
                resul = false;
            }
        }
        return resul;
    }
}
