package edu.android.mainmen.Review;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllCommentDTO;
import edu.android.mainmen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCommentFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button commentBtn;
    private EditText commentText;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<AllCommentDTO> allCommentDTOS = new ArrayList<>();
    private String ID,title,desc,FoodKey,deleteKey;
    private List<String> deleteKeys = new ArrayList<>();

    public DetailCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_comment, container, false);

        recyclerView = view.findViewById(R.id.commentRecyclerView1);

        ID  = "FIREBASEDATA";
        title = "ID";
        desc = "DESC";
        FoodKey = ID + title + desc;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        return view;
    }


    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.comment_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textId.setText(allCommentDTOS.get(position).ID);
            holder.textComment.setText(allCommentDTOS.get(position).Comment);
        }

        @Override
        public int getItemCount() {
            return allCommentDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textId;
            private TextView textComment;


            public ViewHolder(View itemView) {
                super(itemView);
                textId = itemView.findViewById(R.id.text_id);
                textComment = itemView.findViewById(R.id.text_comment);


            }
        }

    }

}
