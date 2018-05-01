package edu.android.mainmen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Comment;

import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.DrawerMenu.CommentActivity;
import edu.android.mainmen.R;
import edu.android.mainmen.Search.SearchActivity;
import edu.android.mainmen.ReviewFragment.DetailViewActivity2;

import static edu.android.mainmen.Upload.FirebaseUploadActivity.FOOD;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<AllFoodDTO> allFoodDTOList;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private List<String> uidLists;
    private int selectedPosition;
    public final static String KEY_LIST = "FIREBASEDATA";
    public final static String KEY_ID =  "ID";
    public final static String KEY_DESC = "DESC";


    public MyAdapter(Context context, List<AllFoodDTO> allFoodDTOList, FirebaseAuth auth, FirebaseDatabase database, FirebaseStorage storage, List<String> uidLists) {
        this.context = context;
        this.allFoodDTOList = allFoodDTOList;
        this.auth = auth;
        this.database = database;
        this.storage = storage;
        this.uidLists = uidLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((CustomViewHolder)holder).textView.setText(allFoodDTOList.get(position).title);
        ((CustomViewHolder)holder).textView2.setText(allFoodDTOList.get(position).description);
        ((CustomViewHolder)holder).rb.setRating(allFoodDTOList.get(position).ratingScore);
        Glide.with(holder.itemView.getContext()).load(allFoodDTOList.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

        ((CustomViewHolder)holder).ID.setText(allFoodDTOList.get(position).userId);
        ((CustomViewHolder)holder).heartCount.setText(allFoodDTOList.get(position).starCount+"명이 좋아합니다.");
        //좋아요 버튼
        ((CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener() {
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
        if(user!=null) {
            if (allFoodDTOList.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                ((CustomViewHolder) holder).starButton.setImageResource(R.drawable.ic_heart2);

            } else {
                ((CustomViewHolder) holder).starButton.setImageResource(R.drawable.ic_heart1);
            }
        }

        ((CustomViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailViewActivity2.class);
                intent.putExtra("location", allFoodDTOList.get(position).Location);
                intent.putExtra("id", allFoodDTOList.get(position).userId);
                intent.putExtra("title", allFoodDTOList.get(position).title);
                intent.putExtra("desc", allFoodDTOList.get(position).description);
                intent.putExtra("rating", allFoodDTOList.get(position).ratingScore);
                intent.putExtra("images", allFoodDTOList.get(position).imageUrl);
                intent.putExtra("heartCount",allFoodDTOList.get(position).starCount);
                intent.putExtra("position",position);

                context.startActivity(intent);


            }
        });


        // 삭제버튼
        ((CustomViewHolder)holder).deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_content(position);
            }
        });

        // 이미지버튼

        ((CustomViewHolder)holder).commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPosition = position;
                String ID = allFoodDTOList.get(position).userId;
                String title = allFoodDTOList.get(position).title;
                String desc = allFoodDTOList.get(position).description;

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra(KEY_ID, ID);
                intent.putExtra(KEY_LIST, title);
                intent.putExtra(KEY_DESC, desc);
                context.startActivity(intent);
            }
        });
    }

    //글 삭제
    private void delete_content(final int position) {

        storage.getReference().child("images/").child(allFoodDTOList.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    firebaseData.starCount = firebaseData.starCount + 1;
                    firebaseData.stars.remove(auth.getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    firebaseData.starCount = firebaseData.starCount - 1;
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


    @Override
    public int getItemCount() {
        return allFoodDTOList.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView ID, textView, textView2;
        ImageView imageView;
        ImageView deleteButton;
        ImageView starButton;
        TextView heartCount;
        RatingBar rb;
        ImageView commentButton;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.item_textView_id);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageView);
            textView = (TextView) itemView.findViewById(R.id.item_textView);
            textView2 = (TextView) itemView.findViewById(R.id.item_textView2);
            deleteButton = (ImageView)itemView.findViewById(R.id.item_delete_image);
            starButton = itemView.findViewById(R.id.item_heart_image);
            heartCount = itemView.findViewById(R.id.item_heart_count);
            rb = itemView.findViewById(R.id.rb);
            commentButton = itemView.findViewById(R.id.comment_image);

        }
    }
}
