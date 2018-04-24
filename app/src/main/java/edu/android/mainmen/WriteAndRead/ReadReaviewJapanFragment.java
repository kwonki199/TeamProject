package edu.android.mainmen.WriteAndRead;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

import static edu.android.mainmen.WriteAndRead.FirebaseUploadActivity.JAPANFOOD;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadReaviewJapanFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<AllFoodDTO> firebaseData = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;


    public ReadReaviewJapanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review, container, false);
        database = FirebaseDatabase.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView_Review2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ReviewRecyclerViewAdapter boardRecyclerViewAdapter = new ReviewRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        database.getReference().child(JAPANFOOD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                firebaseData.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                    ReadReaviewJapanFragment.this.firebaseData.add(allFoodDTO);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }



    class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).textView.setText(firebaseData.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(firebaseData.get(position).description);

            Glide.with(holder.itemView.getContext()).load(firebaseData.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);
        }

        @Override
        public int getItemCount() {
            return firebaseData.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);
            }
        }
    }


}