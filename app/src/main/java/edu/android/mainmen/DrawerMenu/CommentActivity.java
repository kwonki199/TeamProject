package edu.android.mainmen.DrawerMenu;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


import edu.android.mainmen.Controller.AllCommentDTO;
import edu.android.mainmen.MainActivity;
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
    private DatabaseReference databaseReference;
//    private FirebaseStorage storage;
    private List<AllCommentDTO> allCommentDTOS = new ArrayList<>();
    private String ID;
    private String title;
    private String desc;
    private String FoodKey;
    private String deleteKey;
    private List<String> deleteKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ID  = intent.getStringExtra(KEY_ID);
        title = intent.getStringExtra(KEY_LIST);
        desc = intent.getStringExtra(KEY_DESC);
        FoodKey = ID + title + desc;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        recyclerView = findViewById(R.id.commentRecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CommentAdapter adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);
        commentBtn = findViewById(R.id.comment_button);
        commentText = findViewById(R.id.comment_text);

        commentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    commentUpload(commentText.getText().toString());
                } else {
                    new AlertDialog.Builder(CommentActivity.this)
                            .setTitle("로그인이 필요합니다")
                            .setMessage("로그인창으로 이동하시겠습니까?")
                            .setIcon(R.drawable.logotest)
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @TargetApi(11)
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(CommentActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @TargetApi(11)
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

                       database.getReference().child("Comment").orderByChild("FoodId").equalTo(FoodKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        allCommentDTOS.clear();
                        deleteKeys.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AllCommentDTO allCommentDTO = snapshot.getValue(AllCommentDTO.class);
                            allCommentDTOS.add(allCommentDTO);
                            deleteKeys.add(allCommentDTO.DeleteKey);
                        }
                        adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void commentUpload(final String comment) {  // 댓글 업로드
        
        if(comment.equals(null)){
            Toast.makeText(this, "입력된 글이 없습니다.", Toast.LENGTH_SHORT).show();
        } else if (commentText.getText() != null){
            deleteKey = (FoodKey + comment + auth.getCurrentUser().getUid()).replace(".", "@")
                    .replace("#", "@").replace("$", "@").replace("[", "@")
                    .replace("]", "@"); // 경로에 들어갈 수 없는 문자 변경
            AllCommentDTO allCommentDTO = new AllCommentDTO();

            allCommentDTO.ID = auth.getCurrentUser().getEmail();
            allCommentDTO.Comment = comment;
            allCommentDTO.FoodId = FoodKey;
            allCommentDTO.DeleteKey = deleteKey;
            database.getInstance().getReference("Comment").push().setValue(allCommentDTO);

            Toast.makeText(this, "댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
            commentText.setText(null);
        }

    }


    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(CommentActivity.this);
            View view = inflater.inflate(R.layout.comment_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textId.setText(allCommentDTOS.get(position).ID);
            holder.textComment.setText(allCommentDTOS.get(position).Comment);
//            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    delete_content(position);
//
//                }
//            });
        }

        private void delete_content(final int position) {
            database.getReference().child("Comment").child("DeleteKey").child(deleteKeys.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(CommentActivity.this, "삭제 구현", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CommentActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();     
                }
            });
        }

        @Override
        public int getItemCount() {
            return allCommentDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textId;
            TextView textComment;
//            ImageView deleteButton;

            public ViewHolder(View itemView) {
                super(itemView);
                textId = itemView.findViewById(R.id.text_id);
                textComment = itemView.findViewById(R.id.text_comment);
//                deleteButton = itemView.findViewById(R.id.item_delete_image);

            }
        }

    }
    // 뒤로가기 버튼 이거
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
