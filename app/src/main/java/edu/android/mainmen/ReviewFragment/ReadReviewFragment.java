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
import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

import static edu.android.mainmen.Upload.FirebaseUploadActivity.*;


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
        final MyAdapter reviewRecyclerViewAdapter = new MyAdapter(allFoodDTOS, auth, database, storage, uidLists);
        recyclerView.setAdapter(reviewRecyclerViewAdapter);


        database.getReference().child(FOOD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allFoodDTOS.clear();
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                    String uidKey = snapshot.getKey();
                    allFoodDTOS.add(allFoodDTO);
                    uidLists.add(uidKey);
                }
                reviewRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
