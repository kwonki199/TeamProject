package edu.android.mainmen.WriteAndRead;


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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

import static edu.android.mainmen.WriteAndRead.FirebaseUploadActivity.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<AllFoodDTO> allFoodDTOS = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private FirebaseAuth auth;


    public ReadReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review, container, false);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView_Review2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ReviewRecyclerViewAdapter reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter();
        recyclerView.setAdapter(reviewRecyclerViewAdapter);


        database.getReference().child(FOOD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                allFoodDTOS.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                    ReadReviewFragment.this.allFoodDTOS.add(allFoodDTO);
                }
                reviewRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }



    //어댑터
    class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).textView.setText(allFoodDTOS.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(allFoodDTOS.get(position).description);

            Glide.with(holder.itemView.getContext()).load(allFoodDTOS.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

            ((CustomViewHolder)holder).ID.setText(allFoodDTOS.get(position).userId);

            ((CustomViewHolder)holder).deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_content(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return allFoodDTOS.size();
        }


        //글 삭제
        private void delete_content(final int position) {

            storage.getReference().child("images/").child(allFoodDTOS.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        //뷰홀더
        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;
            ImageView deleteButton;
            TextView ID;

            public CustomViewHolder(View view) {
                super(view);
                ID = view.findViewById(R.id.item_textView_id);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);
                deleteButton = (ImageView)view.findViewById(R.id.item_delete_image);
            }
        }



    }

}
