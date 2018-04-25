package edu.android.mainmen.WriteAndRead;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

public class ReadReviewActivity extends AppCompatActivity {
    private static final String EXTRA_CAONTACT_INDEX="selected_contact_index";

    private RecyclerView recyclerView;
    private List<AllFoodDTO> firebaseData = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;

    ///////////////////////
    private FirebaseStorage storage;
    ///////////////////////

    public static Intent newIntent(Context context, int index){
        Intent intent=new Intent(context,ReadReviewActivity.class);
        intent.putExtra(EXTRA_CAONTACT_INDEX,index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Review);
        ////////////////////////
        storage = FirebaseStorage.getInstance();
        ////////////////////////


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        database.getReference().child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                firebaseData.clear();;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AllFoodDTO allFoodDTO = snapshot.getValue(AllFoodDTO.class);
                    ReadReviewActivity.this.firebaseData.add(allFoodDTO);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        // MainActivity가 보낸 Intent를 찾아서,
//        // 인텐트에 포함된 Extra 데이터(position 정보)를 찾음
//        Intent intent=getIntent();
//        int index=intent.getIntExtra(EXTRA_CAONTACT_INDEX,0);
//        // fragment에 주려고 만든다.
//
//        //ContactDetailActivity에 attach된 ContactDetailFragment에게
//        // index 정보를 전달
//        FragmentManager fm=getSupportFragmentManager();
//        ReadReviewFragment fragment = (ReadReviewFragment) fm.findFragmentById(R.id.fragment_review);
//        fragment.setIndex(index);
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).textView.setText(firebaseData.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(firebaseData.get(position).description);

            Glide.with(holder.itemView.getContext()).load(firebaseData.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

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


        //////////////////////////////////////////

        private void delete_content(int position) {

            storage.getReference().child("images").child(firebaseData.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ReadReviewActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ReadReviewActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //////////////////////////////////////////

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;
            ImageView deleteButton;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);

                deleteButton = (ImageView)view.findViewById(R.id.item_delete_image);
            }
        }
    }
}
