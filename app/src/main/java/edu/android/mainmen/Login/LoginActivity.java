package edu.android.mainmen.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView login_id;
    private EditText login_pw;
    private FirebaseAuth mAuth;


    private AutoCompleteTextView LoginId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button id_sign_in_button = findViewById(R.id.id_sign_in_button);
        Button registerBtn = findViewById(R.id.registerBtn);
        Button id_find_Btn = findViewById(R.id.id_find_Btn);
        Button password_find_Btn = findViewById(R.id.password_find_Btn);
        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);

        // 회원가입 버튼
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // 로그인 버튼
        id_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 로그인 버튼으로 로그인 시키기 - 현재 에러
                loginUser(login_id.getText().toString(), login_pw.getText().toString());
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


    }// end onCreate


    private void loginUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                        // ...
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