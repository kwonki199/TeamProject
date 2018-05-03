package edu.android.mainmen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Controller.AllNoticeDTO;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private List<AllNoticeDTO> allNoticeDTOS = new ArrayList<>();
    public static final String TITLE = "title";
    public static final String TEXT = "text";


    private String[] subTitile = {
            "부제"
    };

    private String[] stringsTitle = {
            "제목"
    };

    private String[] stringsText = {
            "내용"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView = findViewById(R.id.noticeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoticeAdapter adapter = new NoticeAdapter();
        recyclerView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();



//        for (int i = 0; i < stringsText.length; i++) { // 공지사항 추가할때 한번 실행
////            AllNoticeDTO allNoticeDTO = new AllNoticeDTO();
////            allNoticeDTO.subtitle = subTitile[i];
////            allNoticeDTO.title = stringsTitle[i];
////            allNoticeDTO.text = stringsText[i];
////            database.getReference("Notice").push().setValue(allNoticeDTO);
////
////        }


        database.getReference().child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allNoticeDTOS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AllNoticeDTO allNoticeDTO = snapshot.getValue(AllNoticeDTO.class);
                    allNoticeDTOS.add(allNoticeDTO);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(NoticeActivity.this);
            View view = inflater.inflate(R.layout.notice_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.noticeTitleText.setText(allNoticeDTOS.get(position).getTitle());
            holder.noticeSubtitleText.setText(allNoticeDTOS.get(position).subtitle + "    ...  <더보기>");

            holder.selectedview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoticeActivity.this, NoticeDetailActivity.class);
                    intent.putExtra(TITLE, allNoticeDTOS.get(position).title);
                    intent.putExtra(TEXT, allNoticeDTOS.get(position).text);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return allNoticeDTOS.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView noticeTitleText;
            TextView noticeSubtitleText;
            LinearLayout selectedview;


            public ViewHolder(View itemView) {
                super(itemView);
                noticeTitleText = itemView.findViewById(R.id.noticeTitleText);
                noticeSubtitleText = itemView.findViewById(R.id.noticeText);
                selectedview = itemView.findViewById(R.id.selectedView);


            }
        }
    }
}
