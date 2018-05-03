package edu.android.mainmen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Adapter.MyAdapter;
import edu.android.mainmen.Controller.AllCommentDTO;
import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.DrawerMenu.CommentActivity;
import edu.android.mainmen.R;

import static edu.android.mainmen.Adapter.MyAdapter.KEY_DESC;
import static edu.android.mainmen.Adapter.MyAdapter.KEY_ID;
import static edu.android.mainmen.Adapter.MyAdapter.KEY_LIST;
import static edu.android.mainmen.Upload.FirebaseUploadActivity.FOOD;

public class DetailViewActivity2 extends AppCompatActivity {

    private TextView ID, textView, textView2, detailAdress,detailStoreName;
    private ImageView imageView;

    private RatingBar rb;


    private Context context;
    private List<AllFoodDTO> firebaseData;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private List<String> uidLists;
    private RecyclerView recyclerView;
    private List<AllCommentDTO> allCommentDTOS = new ArrayList<>();
    private String FoodKey;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view2);

        hideActionBar();

        database = FirebaseDatabase.getInstance();

        ID = findViewById(R.id.item_textView_id);
        detailAdress = findViewById(R.id.detailAdress);
        imageView = (ImageView) findViewById(R.id.item_imageView);
        textView = (TextView) findViewById(R.id.item_textView);
        textView2 = (TextView) findViewById(R.id.item_textView2);
        rb = findViewById(R.id.rb);
        detailStoreName = (TextView)findViewById(R.id.detailStoreName);

        Intent intent = getIntent();

        String Location = getIntent().getStringExtra("location");
        String id = getIntent().getStringExtra("id");
        final String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        float rating = getIntent().getFloatExtra("rating", 0);

        String imageUrl = intent.getStringExtra("images");
        String storeName = getIntent().getStringExtra("StoreName");
//        final int position = intent.getIntExtra("position", -1);

        detailStoreName.setText(storeName);

        ID.setText(id);
        textView.setText(title);
        textView2.setText(desc);
        rb.setRating(rating);
        Glide.with(this).load(imageUrl).into(imageView);

        detailAdress.setText(Location);


        FoodKey = id + title + desc;

        // 댓글 구현
        recyclerView = findViewById(R.id.commentRecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CommentAdapter adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);

        database.getReference().child("Comment").orderByChild("FoodId").equalTo(FoodKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allCommentDTOS.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AllCommentDTO allCommentDTO = snapshot.getValue(AllCommentDTO.class);
                    allCommentDTOS.add(allCommentDTO);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //////////////////////////////////////////


    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(DetailViewActivity2.this);
            View view = inflater.inflate(R.layout.comment_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textId.setText(allCommentDTOS.get(position).ID);
            holder.textComment.setText(allCommentDTOS.get(position).Comment);
//            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });
        }



        @Override
        public int getItemCount() {
            return allCommentDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textId;
            TextView textComment;
//            ImageView deleteButton;

            public ViewHolder(View itemView) {
                super(itemView);
                textId = itemView.findViewById(R.id.text_id);
                textComment = itemView.findViewById(R.id.text_comment);
//                deleteButton = itemView.findViewById(R.id.item_delete_image);

            }
        }

    }

}














