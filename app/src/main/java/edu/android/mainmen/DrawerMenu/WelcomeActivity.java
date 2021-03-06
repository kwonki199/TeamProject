package edu.android.mainmen.DrawerMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView welcomeMsg = findViewById(R.id.welcomeMsg);
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
