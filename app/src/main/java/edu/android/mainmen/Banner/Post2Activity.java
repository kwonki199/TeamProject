package edu.android.mainmen.Banner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.android.mainmen.Adapter.ScrollingPostPageAdapter;
import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class Post2Activity extends AppCompatActivity {

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_post2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPagerTae1);
        ScrollingPostPageAdapter viewPagerAdapter = new ScrollingPostPageAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post2Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
