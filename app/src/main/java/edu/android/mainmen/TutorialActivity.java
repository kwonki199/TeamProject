package edu.android.mainmen;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TutorialActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        hideActionbar();
        btn = findViewById(R.id.btn_tutorial);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(TutorialActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);



    }// onCreate


    private void hideActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
