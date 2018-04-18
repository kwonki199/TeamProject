package edu.android.mainmen.Login;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button id_sign_in_button = findViewById(R.id.id_sign_in_button);
        Button registerBtn = findViewById(R.id.registerBtn);
        Button id_find_Btn = findViewById(R.id.id_find_Btn);
        Button password_find_Btn = findViewById(R.id.password_find_Btn);

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

        id_find_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id_find_btn = new Intent(LoginActivity.this, IdFindActivity.class);
                LoginActivity.this.startActivity(id_find_btn);
            }
        });

        password_find_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent password_find_btn = new Intent(LoginActivity.this, PasswordFindActivity.class);
                LoginActivity.this.startActivity(password_find_btn);
            }
        });



    }
    // 뒤로가기 버튼 이거
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}