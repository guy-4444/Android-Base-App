package com.guy.baseapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_ScreenShot extends AppCompatActivity {

    private HorizontalScrollView scroll;
    private LinearLayout lay;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);

        scroll = findViewById(R.id.scroll);
        lay = findViewById(R.id.lay);
        image = findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

    }

    private void start() {
        View v = lay;
        //Define a bitmap with height and width of the View
        Bitmap viewBitmap = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Bitmap.Config.RGB_565);

        Canvas viewCanvas = new Canvas(viewBitmap);



        //get background of canvas
        Drawable backgroundDrawable = v.getBackground();

        if(backgroundDrawable!=null){
            //draw the background on canvas;
            backgroundDrawable.draw(viewCanvas);
        }
        else{
            viewCanvas.drawColor(Color.BLACK);
            //draw on canvas
            v.draw(viewCanvas);
        }

        //image.setImageDrawable(viewBitmap);
        image.setImageBitmap(viewBitmap);
    }
}