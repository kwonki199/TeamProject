package edu.android.mainmen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import edu.android.mainmen.Banner.HeartKingActivity;
import edu.android.mainmen.Banner.Post1Activity;
import edu.android.mainmen.Banner.Post2Activity;
import edu.android.mainmen.Banner.Post5Activity;
import edu.android.mainmen.Banner.Post6Activity;
import edu.android.mainmen.DrawerMenu.RouletteActivity;
import edu.android.mainmen.Search.SearchActivity;

public class EventActivity extends AppCompatActivity {

    private int[] bannerImages = {
            R.drawable.b1, R.drawable.b2, R.drawable.b3,
            R.drawable.b4, R.drawable.b5, R.drawable.b6,
    };
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerView = findViewById(R.id.eventRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);


    }

    class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(EventActivity.this);
            View view = inflater.inflate(R.layout.event_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.imageView.setImageResource(bannerImages[position]);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        Intent intent = new Intent(EventActivity.this, Post1Activity.class);
                        startActivity(intent);
                    } else if (position == 1) {
                        Intent intent = new Intent(EventActivity.this, Post2Activity.class);
                        startActivity(intent);
                    } else if (position == 2) {
                        Intent intent = new Intent(EventActivity.this, RouletteActivity.class);
                        startActivity(intent);
                    } else if (position == 3) {
                        Intent intent = new Intent(EventActivity.this, HeartKingActivity.class);
                        startActivity(intent);
                    } else if (position == 4) {
                        Intent intent = new Intent(EventActivity.this, Post5Activity.class);
                        startActivity(intent);
                    } else if (position == 5) {
                        Intent intent = new Intent(EventActivity.this, Post6Activity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return bannerImages.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.bannerImage);
            }
        }


    }


}
