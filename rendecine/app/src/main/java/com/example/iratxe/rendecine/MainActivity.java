package com.example.iratxe.rendecine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void castellano(View v){
        Intent intent=new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }
    public void euskera(View v){
        //Intent intent=new Intent(this,RegistroActivity.class);
        Intent intent=new Intent(this,peliculasActivity.class);
        startActivity(intent);
    }
}
