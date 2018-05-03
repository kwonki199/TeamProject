package edu.android.mainmen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WorkLogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String[] workTitles = {
        "workTitle", "workTitle2"
    };

    private String[] workTexts = {
        "workTextworkTe\nxtworkTextworkTextworkTextwork\nTextworkTextworkTextworkTextworkTextworkTextworkText",
            "workTextworkTextworkTextworkTextworkTextworkTextworkTextworkTextworkTextworkTextworkTextworkText"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_log);

        recyclerView = findViewById(R.id.workLogRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WorkLogAdapter adapter = new WorkLogAdapter();
        recyclerView.setAdapter(adapter);


    }

    class WorkLogAdapter extends RecyclerView.Adapter<WorkLogAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(WorkLogActivity.this);
            View view = inflater.inflate(R.layout.work_log_board, parent, false);
            ViewHolder holder = new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.workLogTitleText.setText(workTitles[position]);
            holder.workLogText.setText(workTexts[position]);
        }

        @Override
        public int getItemCount() {
            return workTexts.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView workLogTitleText;
            TextView workLogText;

            public ViewHolder(View itemView) {
                super(itemView);
                workLogTitleText = itemView.findViewById(R.id.workLogTitleText);
                workLogText = itemView.findViewById(R.id.workLogText);

            }
        }
    }
}
