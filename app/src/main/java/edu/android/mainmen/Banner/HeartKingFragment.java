package edu.android.mainmen.Banner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Adapter.MyAdapter;
import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;
import edu.android.mainmen.Search.SearchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeartKingFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<AllFoodDTO> allFoodDTOList = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private List<String> uidListsDetail = new ArrayList<>();





    public HeartKingFragment() {
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
        final MyAdapter MyRecyclerViewAdapter = new MyAdapter(getActivity(),allFoodDTOList, auth, database, storage, uidLists);
        recyclerView.setAdapter(MyRecyclerViewAdapter);



            database.getReference()
                    .child("Food")
                    .orderByChild("starCount")
                    .limitToFirst(1)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            allFoodDTOList.clear();
                            uidLists.clear();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                                allFoodDTOList.add(allFoodDTO);
                                String uidKey = snapshot.getKey();
                                uidLists.add(uidKey);
                            }
                            MyRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



        return view;
    }

}
