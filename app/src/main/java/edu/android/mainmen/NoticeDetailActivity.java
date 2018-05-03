package edu.android.mainmen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeDetailActivity extends AppCompatActivity {
    private String title;
    private String text;
    private TextView titleView;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra(NoticeActivity.TITLE);
        text = intent.getStringExtra(NoticeActivity.TEXT);

        titleView = findViewById(R.id.titleView);
        textView = findViewById(R.id.textView);

        titleView.setText(title);
        textView.setText(text);

    }
}
