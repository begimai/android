package com.example.amantaeva.begimai.dice;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Integer[] imgs = {  R.drawable.ic_dice_1,
                        R.drawable.ic_dice_2,
                        R.drawable.ic_dice_3,
                        R.drawable.ic_dice_4,
                        R.drawable.ic_dice_5,
                        R.drawable.ic_dice_6};
    private ImageView img1;
    private  ImageView img2;

    private int numForFirstImg;
    private int numForSecondImg;

    final Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = (ImageView)findViewById(R.id.imageView1);
        img2 = (ImageView)findViewById(R.id.imageView2);


        if(savedInstanceState != null)
        {
            img1.setImageResource(imgs[savedInstanceState.getInt("firstDie")]);
            img2.setImageResource(imgs[savedInstanceState.getInt("secondDie")]);
        }
        else {
            generateRandoms();
        }
    }

    public void roll(View view){
        generateRandoms();

        img1.setImageResource(imgs[numForFirstImg]);
        img2.setImageResource(imgs[numForSecondImg]);
    }

    public void generateRandoms() {
        numForFirstImg = rand.nextInt(6);
        numForSecondImg = rand.nextInt(6);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("firstDie", numForFirstImg);
        outState.putInt("secondDie", numForSecondImg);
    }
}
