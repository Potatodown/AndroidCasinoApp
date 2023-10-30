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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ca.on.conestogac.gambleapp.PayRollApplication;
import ca.on.conestogac.gambleapp.ImageViewScrolling.IEventEnd;
import ca.on.conestogac.gambleapp.ImageViewScrolling.ImageViewScrolling;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    Button gambleButton;
    RadioGroup betGroup;
    ImageViewScrolling image, image2,image3;
    TextView  text_money;
    TextView betMoney;
    int count_done = 0;
    int bet = 0;
    GambleApplication application;
    PayRollApplication payRollApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gambleButton = findViewById(R.id.GambleButton);
        text_money = findViewById(R.id.money);
        betMoney = findViewById(R.id.betMoney);
        image = (ImageViewScrolling) findViewById(R.id.image);
        image2 = (ImageViewScrolling) findViewById(R.id.image2);
        image3 = (ImageViewScrolling) findViewById(R.id.image3);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        betGroup = findViewById(R.id.BetAmount);
        betGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (betGroup.indexOfChild(findViewById(betGroup.getCheckedRadioButtonId()))){
                    case 0:
                        betMoney.setText("Bet: $50");
                        break;
                    case 1:
                        betMoney.setText("Bet: $100");
                        break;
                    case 2:
                        betMoney.setText("Bet: $150");
                        break;
                }
            }
        });

        application = (GambleApplication) getApplication();
        ArrayList UserData = application.getStats();
        text_money.setText("currency: $"+UserData.get(0));

        int money = Integer.parseInt(UserData.get(0).toString());
        gambleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bet = betGroup.indexOfChild(findViewById(betGroup.getCheckedRadioButtonId()));
                if(bet == 0){
                    bet = 50;
                }
                else if (bet == 1){
                    bet = 100;
                }
                else{
                    bet = 150;
                }
                if (money > bet){
                    gambleButton.setEnabled(false);
                    for(int i = 0; i < betGroup.getChildCount(); i++){
                        ((RadioButton)betGroup.getChildAt(i)).setEnabled(false);
                    }
                    image.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    image2.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    image3.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);

                    application.RemoveMoney(-bet);
                    //score - 50;
                }
            }
        });
    }

    @Override
    public void eventEnd(int result, int count) {
        application = (GambleApplication) getApplication();
        ArrayList UserData = application.getStats();
        if (count_done < 2)
            count_done++;
        else{
            gambleButton.setEnabled(true);
            for(int i = 0; i < betGroup.getChildCount(); i++){
                ((RadioButton)betGroup.getChildAt(i)).setEnabled(true);
            }
            bet = bet / 50;
            count_done = 0;
            payRollApplication = new PayRollApplication();
            int win = payRollApplication.slots(image.getValue(), image2.getValue(), image3.getValue(), bet);
            if(win > 0){
                application.addwin(1,win);
                if(win > 900){
                    text_money.setText("BIG WIN!: "+UserData.get(0));
                }
                else {
                    text_money.setText("WIN: "+UserData.get(0));
                }

            }
            else{
                text_money.setText("Currency: $"+UserData.get(0));
                application.addlose(1,0);
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
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
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