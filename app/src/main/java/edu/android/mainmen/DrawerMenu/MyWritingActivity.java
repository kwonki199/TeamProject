package edu.android.mainmen.DrawerMenu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.android.mainmen.R;

public class MyWritingActivity extends AppCompatActivity {

    Button cancelBtn;

    //TODO:  fragment 끼워서 내가 작성한글 보기 구현 완료.!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_writing);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }

        cancelBtn = findViewById(R.id.xbtn_cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
