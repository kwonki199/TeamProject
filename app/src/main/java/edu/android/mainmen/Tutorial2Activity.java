package edu.android.mainmen;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import edu.android.mainmen.Adapter.Tutorial2PageAdapter;


public class Tutorial2Activity extends AppCompatActivity {

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS =1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 10000; // time in milliseconds between successive task execution

    private ViewPager viewPager;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial2);
        hideActionbar();

        viewPager = findViewById(R.id.viewPagerTutorial2);

        Tutorial2PageAdapter viewPagerAdapter = new Tutorial2PageAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
//        if(viewPager.getCurrentItem()==0){
//
//            viewPager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Tutorial2Activity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            });
//        }



        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 5) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        btn = findViewById(R.id.btn_tutorial);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void hideActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
