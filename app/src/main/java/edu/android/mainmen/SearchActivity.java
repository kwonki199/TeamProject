package edu.android.mainmen;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.Model.User;

public class SearchActivity extends AppCompatActivity {

    private TextView heartTextview,manTextview,age10Textview;
    public static String fuck;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private List<User> users = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();



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
        age10Textview = findViewById(R.id.search_10age_textView);

        heartTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        database.getReference().child("users").orderByChild("usersex").equalTo("여성").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                users.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    String uid = snapshot.getKey();
                    users.add(user);
                    uidLists.add(uid);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
