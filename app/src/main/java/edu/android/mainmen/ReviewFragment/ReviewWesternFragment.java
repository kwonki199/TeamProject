package edu.android.mainmen.ReviewFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

import static edu.android.mainmen.Upload.FirebaseUploadActivity.FOOD;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewWesternFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<AllFoodDTO> firebaseData = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;



    public ReviewWesternFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView_Review2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ReviewRecyclerViewAdapter boardRecyclerViewAdapter = new ReviewRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);



        database.getReference().child(FOOD).orderByChild("food").equalTo("western").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                firebaseData.clear();
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                    firebaseData.add(allFoodDTO);
                    String uidKey = snapshot.getKey();
                    uidLists.add(uidKey);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    // 어댑터
    class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).ID.setText(firebaseData.get(position).userId);
            ((CustomViewHolder)holder).textView.setText(firebaseData.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(firebaseData.get(position).description);

            Glide.with(holder.itemView.getContext()).load(firebaseData.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

            //좋아요 버튼
            ((CustomViewHolder)holder).starButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStarClicked(database.getReference().child(FOOD).child(uidLists.get(position)));
                }
            });

            if (firebaseData.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.ic_heart2);

            }else {
                ((CustomViewHolder)holder).starButton.setImageResource(R.drawable.ic_heart1);
            }


            ((CustomViewHolder)holder).deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_content(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return firebaseData.size();
        }

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


        private void delete_content(final int position) {

            storage.getReference().child("images/").child(firebaseData.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    database.getReference().child(FOOD).child(uidLists.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(), "삭제가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;
            TextView ID;
            ImageView deleteButton;
            ImageView starButton;

            public CustomViewHolder(View view) {
                super(view);
                ID = (TextView) view.findViewById(R.id.item_textView_id);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);
                deleteButton = view.findViewById(R.id.item_delete_image);
                starButton = view.findViewById(R.id.item_heart_image);
            }
        }
    }


}
