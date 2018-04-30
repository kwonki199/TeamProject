package edu.android.mainmen.Search;


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
import edu.android.mainmen.Model.User;
import edu.android.mainmen.R;

import static edu.android.mainmen.Upload.FirebaseUploadActivity.FOOD;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewSearchDetailFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<AllFoodDTO> allFoodDTOList = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private List<String> uidListsDetail = new ArrayList<>();





    public ReviewSearchDetailFragment() {
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


        if (SearchActivity.heartSearch == "좋아요순") {
            database.getReference()
                    .child("Food")
                    .orderByChild("starCount")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                    firebaseData.clear();
//                    uidLists.clear();
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
        }else {

            database.getReference().child("users").orderByChild(SearchActivity.orderByChild).equalTo(SearchActivity.equalTo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    users.add(user);
                        String uid = snapshot.getKey();
                        Log.i("user아이디", uid);
                        uidListsDetail.add(uid);
                        database.getReference()
                                .child("Food")
                                .orderByChild("uid")
                                .equalTo(uid)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                    firebaseData.clear();
//                    uidLists.clear();
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
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
                    }
                    Log.e("donghee", "key=" + dataSnapshot.getKey() + ", " + dataSnapshot.getValue());


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


        return view;
    }

}
