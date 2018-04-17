package edu.android.mainmen.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.android.mainmen.R;

public class MyWritingsActivity extends AppCompatActivity {

    class MyWritingsAdapter extends ArrayAdapter<MyWritings> {
        private Context context;
        private List<MyWritings> dataset;

        public MyWritingsAdapter(@NonNull Context context, List<MyWritings> objects) {
            super(context, -1, objects);
            this.context = context;
            this.dataset = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // 리스트 아이템 하나에 대한 뷰를 생성하고, 세팅
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.mywritings_list_item, parent, false);

            MyWritings m = dataset.get(position);

            ImageView iv = view.findViewById(R.id.img_store);
            iv.setImageResource(m.getPhotoId());

            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(m.getTv_address());

            TextView tv_store_name = view.findViewById(R.id.tv_store_name);
            tv_store_name.setText(m.getTv_store_name());

            TextView tv_distance = view.findViewById(R.id.tv_distance);
            tv_distance.setText(m.getTv_distance());
            return view;
        }

    }
    private List<MyWritings> data;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywritings_main);

        data = MyWritingsLab.getInstance().getMyWritingsList();
        listView = findViewById(R.id.listView);

        MyWritingsActivity.MyWritingsAdapter adapter = new MyWritingsAdapter(this, data);
        listView.setAdapter(adapter);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
