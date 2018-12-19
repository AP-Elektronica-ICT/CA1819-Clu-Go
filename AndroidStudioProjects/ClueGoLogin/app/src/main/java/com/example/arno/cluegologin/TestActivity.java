package com.example.arno.cluegologin;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ArrayList<Bitmap> noice = getIntent().getExtras().getParcelableArrayList("imagechunks");
        ArrayList<BitmapDrawable> drawables = makeDrawables(noice);

        ImageView image = (ImageView) findViewById(R.id.testimage);
        ImageView image2 = (ImageView) findViewById(R.id.testimage1);
        ImageView image3 = (ImageView) findViewById(R.id.testimage2);
        ImageView image4 = (ImageView) findViewById(R.id.testimage3);
        ImageView image5 = (ImageView) findViewById(R.id.testimage4);
        ImageView image6 = (ImageView) findViewById(R.id.testimage5);
        ImageView image7 = (ImageView) findViewById(R.id.testimage6);
        ImageView image8 = (ImageView) findViewById(R.id.testimage7);
        ImageView image9 = (ImageView) findViewById(R.id.testimage8);

        image.setBackgroundDrawable(drawables.get(0));
        image2.setBackgroundDrawable(drawables.get(1));
        image3.setBackgroundDrawable(drawables.get(2));
        image4.setBackgroundDrawable(drawables.get(3));
        image5.setBackgroundDrawable(drawables.get(4));
        image6.setBackgroundDrawable(drawables.get(5));
        image7.setBackgroundDrawable(drawables.get(6));
        image8.setBackgroundDrawable(drawables.get(7));
        image9.setBackgroundDrawable(drawables.get(8));
    }
    public ArrayList<BitmapDrawable> makeDrawables(ArrayList<Bitmap> noice){
        ArrayList<BitmapDrawable> drawables = new ArrayList<BitmapDrawable>();
        for (Bitmap p: noice){
            BitmapDrawable item = new BitmapDrawable(getResources(),p);
            drawables.add(item);

        }
        return drawables;
    }
}
