package com.example.iratxe.rendecine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by aitor on 7/01/17.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;


    public DownloadImageTask(ImageView bmImage) {
        //ImageView image=(ImageView)findViewById(R.id.imagenFrase);
        this.bmImage = bmImage;

    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream is = new java.net.URL(urldisplay).openStream();
           // InputStream is=(InputStream)new URL("http://dl.dropboxusercontent.com/s/al2ce7eox2o4hwi/300.jpg").getContent();
            mIcon11 = BitmapFactory.decodeStream(is);
            //URL url = new URL(urldisplay);
            //InputStream is = new BufferedInputStream(url.openStream());
           // mIcon11=BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}
