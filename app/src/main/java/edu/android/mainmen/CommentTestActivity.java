package edu.android.mainmen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class CommentTestActivity extends AppCompatActivity {

    private TextView test;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_test);
        test = findViewById(R.id.testC);
        database = FirebaseDatabase.getInstance();

        String a=database.getReference().child("Food").push().getKey();

        test.setText(a);




    }
}
