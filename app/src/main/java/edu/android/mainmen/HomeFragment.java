package edu.android.mainmen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recycler;
    private List<kindsOfFood> dataset;



    public HomeFragment() {

    }

    // TODO: 시도중
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recycler = view.findViewById(R.id.recyclerView);
        dataset = kindsOfFoodDao.getInstance().getFoodList();
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //어댑터 설정
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        kindOfFoodAdapter adapter = new kindOfFoodAdapter();
        recycler.setAdapter(adapter);


        return view;


    }// end onCreateView

    class kindOfFoodAdapter extends RecyclerView.Adapter<kindOfFoodAdapter.ViewHolder>{
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.menu_layout, parent, false);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            kindsOfFood food = dataset.get(position);
            holder.foodName.setText(food.getName());
            holder.foodPhoto.setImageResource(food.getPhotoId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : 클릭시 세부메뉴 넘어가게. - 미해결
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView foodPhoto;
            TextView foodName;

            public ViewHolder(View itemView) {
                super(itemView);
                foodName = itemView.findViewById(R.id.itemMenuText);
                foodPhoto = itemView.findViewById(R.id.itemMenuImage);
            }
        }
    }


}// end HomeFragment
