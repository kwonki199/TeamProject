package edu.android.mainmen.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView LoginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button id_sign_in_button = findViewById(R.id.id_sign_in_button);
        EditText registerBtn = findViewById(R.id.registerBtn);

        LoginId = findViewById(R.id.loginId);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, ResisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        id_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_in = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(sign_in);
            }
        });







    }
}