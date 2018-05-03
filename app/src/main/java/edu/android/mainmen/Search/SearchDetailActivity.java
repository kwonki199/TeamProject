package edu.android.mainmen.Search;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class SearchDetailActivity extends AppCompatActivity {

    private TextView detailheadtext;

    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
//        ActionBar ab = getSupportActionBar() ;
//        ab.setTitle("세부검색") ;
        hideActionBar();
        close = findViewById(R.id.searchdetail_Btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailheadtext = findViewById(R.id.detailSearchTitleTextView);
        Intent intent = getIntent();
        detailheadtext.setText("세부검색 - " + intent.getStringExtra("a"));

    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
