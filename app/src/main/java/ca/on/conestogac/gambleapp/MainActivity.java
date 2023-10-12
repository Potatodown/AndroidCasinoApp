package ca.on.conestogac.gambleapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ca.on.conestogac.gambleapp.ImageViewScrolling.IEventEnd;
import ca.on.conestogac.gambleapp.ImageViewScrolling.ImageViewScrolling;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    Button gamblebutton;
    ImageViewScrolling image, image2,image3;
    TextView  text_money;
    int count_done = 0;
    GambleApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamblebutton = findViewById(R.id.GambleButton);
        text_money = findViewById(R.id.money);
        image = (ImageViewScrolling) findViewById(R.id.image);
        image2 = (ImageViewScrolling) findViewById(R.id.image2);
        image3 = (ImageViewScrolling) findViewById(R.id.image3);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);
        application = (GambleApplication) getApplication();
        ArrayList jeff = application.getStats();
        text_money.setText("currency: "+jeff.get(0));
        int money = Integer.parseInt(jeff.get(0).toString());
        gamblebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money > 50){
                    gamblebutton.setVisibility(View.GONE);

                    image.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    image2.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    image3.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);

                    application.RemoveMoney(-50);
                    //score - 50;
                }
            }
        });
    }

    @Override
    public void eventEnd(int result, int count) {
        application = (GambleApplication) getApplication();
        ArrayList jeff = application.getStats();
        if (count_done < 2)
            count_done++;
        else{
            gamblebutton.setVisibility(View.VISIBLE);

            count_done = 0;

            if(image.getValue() == image2.getValue() && image2.getValue() == image3.getValue()){
                application.addwin(1,500);
                text_money.setText("BIG WIN!: "+jeff.get(0));
            }
            else if (image.getValue() == image2.getValue() || image2.getValue() == image3.getValue() ||
            image.getValue() == image3.getValue()){
                application.addwin(1,150);
                text_money.setText("Win: "+jeff.get(0));
            }
            else{
                text_money.setText("currency: "+jeff.get(0));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gambleapp_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;
        switch (item.getItemId()){
            case R.id.menu_game:
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                break;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.menu_shop:
                startActivity(new Intent(getApplicationContext(),ShopActivity.class));
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }
}