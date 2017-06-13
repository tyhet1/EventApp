package edu.monash.fit3027.eventappfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

//https://android--code.blogspot.com.au/2015/08/android-imageview-set-image-from-url.html
class ImageDownloader extends AsyncTask<String, Void, Bitmap>{
    ImageView image;
    public ImageDownloader(ImageView eventImageView) {
        this.image = eventImageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap mIcon = null;
        try{
            InputStream inputStream = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result){
        image.setImageBitmap(result);
    }
}
