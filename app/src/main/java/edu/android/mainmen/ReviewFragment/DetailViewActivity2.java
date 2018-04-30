package edu.android.mainmen.ReviewFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

import static edu.android.mainmen.Upload.FirebaseUploadActivity.FOOD;

public class DetailViewActivity2 extends AppCompatActivity {

    TextView ID, textView, textView2, detailAdress;
    ImageView imageView;
    ImageView deleteButton;
    ImageView starButton;
    TextView heartCount;
    RatingBar rb;


    private Context context;
    private List<AllFoodDTO> firebaseData;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private List<String> uidLists;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view2);

        ID = findViewById(R.id.item_textView_id);
        detailAdress = findViewById(R.id.detailAdress);
        imageView = (ImageView) findViewById(R.id.item_imageView);
        textView = (TextView) findViewById(R.id.item_textView);
        textView2 = (TextView) findViewById(R.id.item_textView2);
        starButton = findViewById(R.id.item_heart_image);
        heartCount = findViewById(R.id.item_heart_count);
        rb = findViewById(R.id.rb);
        deleteButton = (ImageView) findViewById(R.id.item_delete_image);

        Intent intent =getIntent();

        String Location = getIntent().getStringExtra("location");
        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        float rating = getIntent().getFloatExtra("rating", 0);

        String image = getIntent().getStringExtra("images");
        int Star = getIntent().getIntExtra("heartCount", 0);

        detailAdress.setText(Location);
        ID.setText(id);
        textView.setText(title);
        textView2.setText(desc);
        rb.setRating(rating);
       imageView.setImageURI(Uri.parse(image));

        heartCount.setText(Star + "명이 좋아합니다.");



    }


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        textView.setText(firebaseData.get(position).title);
        textView2.setText(firebaseData.get(position).description);
        rb.setRating(firebaseData.get(position).ratingScore);
        Glide.with(holder.itemView.getContext()).load(firebaseData.get(position).imageUrl).into(imageView);

        ID.setText(firebaseData.get(position).userId);
        heartCount.setText(firebaseData.get(position).starCount + "명이 좋아합니다.");
        //좋아요 버튼
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    onStarClicked(database.getReference().child(FOOD).child(uidLists.get(position)));
                } else {
                    Toast.makeText(context, "로그인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (firebaseData.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                starButton.setImageResource(R.drawable.ic_heart2);

            } else {
                starButton.setImageResource(R.drawable.ic_heart1);
            }
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_content(position);
            }
        });

    }

    //글 삭제
    private void delete_content(final int position) {

        storage.getReference().child("images/").child(firebaseData.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                database.getReference().child(FOOD).child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();


            }
        });
    }

    // 좋아요 버튼
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                AllFoodDTO firebaseData = mutableData.getValue(AllFoodDTO.class);
                if (firebaseData == null) {
                    return Transaction.success(mutableData);
                }

                if (firebaseData.stars.containsKey(auth.getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    firebaseData.starCount = firebaseData.starCount - 1;
                    firebaseData.stars.remove(auth.getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    firebaseData.starCount = firebaseData.starCount + 1;
                    firebaseData.stars.put(auth.getCurrentUser().getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(firebaseData);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed

            }
        });
    }


    public int getItemCount() {
        return firebaseData.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView ID, textView, textView2;
        ImageView imageView;
        ImageView deleteButton;
        ImageView starButton;
        TextView heartCount;
        RatingBar rb;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.item_textView_id);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageView);
            textView = (TextView) itemView.findViewById(R.id.item_textView);
            textView2 = (TextView) itemView.findViewById(R.id.item_textView2);
            deleteButton = (ImageView) itemView.findViewById(R.id.item_delete_image);
            starButton = itemView.findViewById(R.id.item_heart_image);
            heartCount = itemView.findViewById(R.id.item_heart_count);
            rb = itemView.findViewById(R.id.rb);

        }
    }
}
