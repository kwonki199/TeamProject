package edu.android.mainmen.DrawerMenu;


import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


import edu.android.mainmen.Controller.AllCommentDTO;
import edu.android.mainmen.R;


import static edu.android.mainmen.Adapter.MyAdapter.KEY_DESC;
import static edu.android.mainmen.Adapter.MyAdapter.KEY_ID;
import static edu.android.mainmen.Adapter.MyAdapter.KEY_LIST;


public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button commentBtn;
    private EditText commentText;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private List<AllCommentDTO> allCommentDTOS = new ArrayList<>();
    String ID;
    String title;
    String desc;
    String FoodKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        ID  = intent.getStringExtra(KEY_ID);
        title = intent.getStringExtra(KEY_LIST);
        desc = intent.getStringExtra(KEY_DESC);
        FoodKey = ID + title + desc;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CommnetAdapter adapter = new CommnetAdapter();
        recyclerView.setAdapter(adapter);
        commentBtn = findViewById(R.id.comment_button);
        commentText = findViewById(R.id.comment_text);

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

        commentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                commentUpload(commentText.getText().toString());
            }
        });

    }


    private void commentUpload(final String comment) {  // 댓글 업로드
        
        if(comment.equals(null)){
            Toast.makeText(this, "입력된 글이 없습니다.", Toast.LENGTH_SHORT).show();
        } else if (commentText.getText() != null){
            AllCommentDTO allCommentDTO = new AllCommentDTO();

            allCommentDTO.ID = auth.getCurrentUser().getEmail();
            allCommentDTO.Comment = comment;
            allCommentDTO.FoodId = FoodKey;
            database.getInstance().getReference("Comment").push().setValue(allCommentDTO);

            Toast.makeText(this, "댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
            commentText.setText(null);
        }

    }


    class CommnetAdapter extends RecyclerView.Adapter<CommnetAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(CommentActivity.this);
            View view = inflater.inflate(R.layout.comment_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textId.setText(allCommentDTOS.get(position).ID);
            holder.textComment.setText(allCommentDTOS.get(position).Comment);
        }

        @Override
        public int getItemCount() {
            return allCommentDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textId;
            TextView textComment;

            public ViewHolder(View itemView) {
                super(itemView);
                textId = itemView.findViewById(R.id.text_id);
                textComment = itemView.findViewById(R.id.text_comment);

            }
        }

    }

}
