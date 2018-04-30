package edu.android.mainmen.Search;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.android.mainmen.R;

public class SearchActivity extends AppCompatActivity {

    private TextView heartTextview,manTextview,womanTextview,age10Textview,age20Textview,age30Textview,age40Textview;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    public static String orderByChild;
    public static String equalTo;
    public static String heartSearch;

    private Button cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        hideActionBar();


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        heartTextview = findViewById(R.id.search_heart_textView);
        manTextview = findViewById(R.id.search_man_textView);
        womanTextview = findViewById(R.id.search_woman_textView);
        age10Textview = findViewById(R.id.search_10age_textView);
        age20Textview = findViewById(R.id.search_20age_textView);
        age30Textview = findViewById(R.id.search_30age_textView4);
        age40Textview = findViewById(R.id.search_40age_textView4);
        cancelBtn = findViewById(R.id.search_cancelBtn);


        heartTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                heartSearch = "좋아요순";
                startActivity(intent);
            }
        });

        manTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "usersex";
                equalTo = "남성";
                heartSearch = null;
                startActivity(intent);
            }
        });

        womanTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "usersex";
                equalTo = "여성";
                heartSearch = null;
                startActivity(intent);
            }
        });

        age10Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "userage";
                equalTo = "10대";
                heartSearch = null;
                startActivity(intent);
            }
        });

        age20Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "userage";
                equalTo = "20대";
                heartSearch = null;
                startActivity(intent);
            }
        });

        age30Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "userage";
                equalTo = "30대";
                heartSearch = null;
                startActivity(intent);
            }
        });
        age40Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                orderByChild = "userage";
                equalTo = "40대 이상";
                heartSearch = null;
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });



    }



    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}// end SearchActivity
