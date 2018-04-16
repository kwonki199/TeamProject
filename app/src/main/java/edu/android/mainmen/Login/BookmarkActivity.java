package edu.android.mainmen.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.android.mainmen.R;

public class BookmarkActivity extends AppCompatActivity{

    class BookmarkAdapter extends ArrayAdapter<BookmarkCreator> {
        private Context context;
        private List<BookmarkCreator> dataset;

        public BookmarkAdapter(@NonNull Context context, List<BookmarkCreator> objects) {
            super(context, -1, objects);
            this.context = context;
            this.dataset = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // 리스트 아이템 하나에 대한 뷰를 생성하고, 세팅
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.bookmark_list_item, parent, false);

            BookmarkCreator b = dataset.get(position);

            ImageView iv = view.findViewById(R.id.img_store);
            iv.setImageResource(b.getPhotoId());

            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(b.getTv_address());

            TextView tv_store_name = view.findViewById(R.id.tv_store_name);
            tv_store_name.setText(b.getTv_store_name());

            TextView tv_distance = view.findViewById(R.id.tv_distance);
            tv_distance.setText(b.getTv_distance());
            return view;
        }

    }
    private List<BookmarkCreator> data;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_main);

        data = BookmarkCreatorLab.getInstance().getBookmarkCreatorList();
        listView = findViewById(R.id.listView);

        BookmarkAdapter adapter = new BookmarkAdapter(this, data);
        listView.setAdapter(adapter);
    }
}
