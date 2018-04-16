package edu.android.mainmen.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import edu.android.mainmen.R;

public class ResisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        AutoCompleteTextView id_input = findViewById(R.id.id_input);
        AutoCompleteTextView password_input = findViewById(R.id.password_input);
        AutoCompleteTextView password_check_input = findViewById(R.id.password_check_input);
        AutoCompleteTextView name_input = findViewById(R.id.name_input);
        AutoCompleteTextView age_input = findViewById(R.id.age_input);
        AutoCompleteTextView email_input = findViewById(R.id.email_input);

        Button join_btn = findViewById(R.id.join_btn);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_btn = new Intent(ResisterActivity.this, WelcomeActivity.class);
                ResisterActivity.this.startActivity(join_btn);
            }
        });

    }
}
