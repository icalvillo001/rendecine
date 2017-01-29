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

    protected Bitmap doInBackground(String... urls) {

        Bitmap imagen=null;
        try {
            URL url1 = new URL(urls[0]);
            imagen = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return imagen;
    }

    protected void onPostExecute(Bitmap result) {

        super.onPostExecute(result);
    }

}
