package com.example.arno.cluego.Helpers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.arno.cluego.R;
import com.example.arno.cluego.TestActivity;

import java.util.ArrayList;

public class SplitImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_image);
        ImageView image =(ImageView) findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.knife);
        splitImage(image,9);
    }

    private void splitImage(ImageView image, int chunkNumbers) {


        int rows,cols;

        int chunkHeight,chunkWidth;

        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        
        Intent intent = new Intent(SplitImage.this, TestActivity.class);
        intent.putParcelableArrayListExtra("imagechunks", chunkedImages);
        startActivity(intent);
    }
}

