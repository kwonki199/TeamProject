package edu.android.mainmen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView welcomeMsg = findViewById(R.id.welcomeMsg);
        AutoCompleteTextView id_input = findViewById(R.id.id_input);
        TextView welcomeMain = findViewById(R.id.welcomeMain);

        welcomeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeMain = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(welcomeMain);
            }
        });

    }

}
