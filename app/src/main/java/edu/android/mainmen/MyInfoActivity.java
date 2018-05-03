package edu.android.mainmen;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.Model.User;

public class MyInfoActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private List<User> allUserDTOS = new ArrayList<>();
    private String userName;
    private RecyclerView recyclerView;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        database= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.MyInforecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyInfoAdapter adapter = new MyInfoAdapter();
        recyclerView.setAdapter(adapter);

        
        database.getReference().child("users").orderByChild("uid").equalTo(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allUserDTOS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    allUserDTOS.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    class MyInfoAdapter extends RecyclerView.Adapter<MyInfoAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MyInfoActivity.this);
            View view = inflater.inflate(R.layout.my_info_item_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textEmail.setText(auth.getCurrentUser().getEmail());
            holder.textUserName.setText(allUserDTOS.get(position).getUsername());
            holder.textUserAge.setText(allUserDTOS.get(position).getUserage());
            holder.textUserSex.setText(allUserDTOS.get(position).getUsersex());

            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            holder.couponBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyInfoActivity.this, "쿠폰 버튼", Toast.LENGTH_SHORT).show();
                }
            });

            holder.managementReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyInfoActivity.this, "리뷰 관리 버튼", Toast.LENGTH_SHORT).show();
                }
            });
            

            holder.noticeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyInfoActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });

            holder.workBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyInfoActivity.this, WorkLogActivity.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return allUserDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textEmail;
            TextView textUserName;
            TextView textUserSex;
            TextView textUserAge;
            ImageView couponBtn;
            ImageView managementReviewBtn;
            Button noticeBtn;
            Button cancelBtn;
            Button workBtn;


            public ViewHolder(View itemView) {
                super(itemView);
                textEmail = itemView.findViewById(R.id.textEmail);
                textUserName = itemView.findViewById(R.id.textUserName);
                textUserSex = itemView.findViewById(R.id.textUserSex);
                textUserAge = itemView.findViewById(R.id.textUserAge);
                couponBtn = itemView.findViewById(R.id.CouponBtn);
                managementReviewBtn = itemView.findViewById(R.id.managementReviewBtn);
                noticeBtn = itemView.findViewById(R.id.noticeBtn);
                cancelBtn = itemView.findViewById(R.id.myInfo_cancelBtn);
                workBtn = itemView.findViewById(R.id.workBtn);
            }
        }
    }
}
