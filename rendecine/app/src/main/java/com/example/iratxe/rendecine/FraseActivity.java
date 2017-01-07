package com.example.iratxe.rendecine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by aitor on 4/01/17.
 */
public class FraseActivity extends AppCompatActivity {

    public static String[] preguntasImagen={"La vida es bella","Peter Pan","300","0","El señor de los anillos","El libro de la selva","Siete almas","1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frase);
        cargarImagen();
        makeTestImagen();
    }

   public void cargarImagen(){

       Drawable drawable=this.getResources().getDrawable(R.drawable.castellano);
        ImageView image=(ImageView)findViewById(R.id.imagenFrase);

       image.setImageDrawable(drawable);

    }
    int posFrase=0;

    public void makeTestImagen(){

        RadioGroup group = (RadioGroup) findViewById(R.id.frase_choices);
        int id=0;
        for (int a=posFrase; a<posFrase+3; a++) {
            RadioButton radio = new RadioButton(this);
            radio.setId(id);
            id=id+1;
            radio.setText(preguntasImagen[a]);
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.button_corregirFrase).setVisibility(View.VISIBLE);
                }
            });
            group.addView(radio);


        }
        posFrase=posFrase+4;
    }

    int posicionCorrecta=3;

    public void corregirImagen(View view){

        RadioGroup group = (RadioGroup)findViewById(R.id.frase_choices);
        int choices = group.getChildCount();
        for(int i=0;i<choices;i++)
            group.getChildAt(i).setEnabled(false);

        findViewById(R.id.button_siguienteFrase).setVisibility(View.VISIBLE);

        int f= Integer.parseInt(preguntasImagen[posicionCorrecta]);
        posicionCorrecta=posicionCorrecta+4;
        group.getChildAt(f).setBackgroundColor(Color.GREEN);

        if( group.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,
                    "Selecciona una opcion",
                    Toast.LENGTH_SHORT);
        }else{
            int select= group.getCheckedRadioButtonId();
            if(select!=f){
                group.getChildAt(select).setBackgroundColor(Color.RED);
            }else{
                Toast.makeText(this,
                        "Respuesta correcta",
                        Toast.LENGTH_SHORT);
            }
        }

    }

    public void siguienteImagen(View view){

        RadioGroup group = (RadioGroup) findViewById(R.id.frase_choices);
        group.removeAllViews();



        if((posFrase)==preguntasImagen.length){
            irPrincipal();
        }else{
            findViewById(R.id.button_siguienteFrase).setVisibility(View.INVISIBLE);
            cargarImagen();
            makeTestImagen();
        }
    }

    public void irPrincipal(){
        Intent intent=new Intent(this,PrincipalActivity.class);
        startActivity(intent);

    }
}
