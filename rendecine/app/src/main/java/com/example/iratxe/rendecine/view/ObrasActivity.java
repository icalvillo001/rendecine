package com.example.iratxe.rendecine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.iratxe.rendecine.R;

/**
 * Created by aitor on 3/01/17.
 */
public class ObrasActivity  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);
    }

    public void interpretarEscena(View view){
        Intent intent=new Intent(this,InterpretarescenaActivity.class);
        startActivity(intent);

    }

    public void interpretarAudio(View view){
        Intent intent=new Intent(this,InterpretaraudioActivity.class);
        startActivity(intent);
    }
}
