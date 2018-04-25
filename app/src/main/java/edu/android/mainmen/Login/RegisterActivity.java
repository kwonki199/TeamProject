package edu.android.mainmen.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.Model.User;
import edu.android.mainmen.R;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private RadioButton rbtnMan;
    private RadioButton rbtnWoman;
    private RadioGroup rbtnGroup;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        editTextEmail = findViewById(R.id.sign_up_edittext_email);
        editTextPassword = findViewById(R.id.sign_up_edittext_password);
        editTextName = findViewById(R.id.sign_up_edittext_name);
        rbtnMan = findViewById(R.id.sign_up_rbtn_man);
        rbtnWoman = findViewById(R.id.sign_up_rbtn_woman);
        rbtnGroup = findViewById(R.id.sign_up_rbtnGroup);
        signUp =  findViewById(R.id.sign_up_button);

        //회원가입 버튼
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString()==null || editTextPassword.getText().toString()==null||editTextName.getText().toString()==null){
                    Toast.makeText(RegisterActivity.this, "작성안한곳이 있습니다.", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    String sex = null;
                    if (rbtnMan.isChecked()) {
                        sex = "남성";
                    } else if(rbtnWoman.isChecked()){
                        sex = "여성";
                    }
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(),editTextName.getText().toString(),sex);
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // User is signed out
                    Toast.makeText(RegisterActivity.this, "User is signed out or current not login", Toast.LENGTH_SHORT).show();

                }
                // ...
            }
        };

    }// end onCreate


    private void createUser(final String email, final String password,final String name, final String sex) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.i("firebase-test", "onComplete successful");
                            Toast.makeText(RegisterActivity.this, "회원가입성공", Toast.LENGTH_SHORT).show();
                            loginUser(email, password);
                            User user = new User();
                            user.username=name;
                            user.usersex = sex;
                            String uid = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user);

                        } else {
                            Log.i("firebase-test", "onComplete NOT successful");
                            Toast.makeText(RegisterActivity.this, "존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
//                            loginUser(email, password);
                            return;
                        }
                    }
                });
    }// end createUser


    private void loginUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "로그인 완료", Toast.LENGTH_LONG).show();
                        } else {

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}