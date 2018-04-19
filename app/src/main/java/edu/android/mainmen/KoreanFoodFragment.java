package edu.android.mainmen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.android.mainmen.Controller.kindsOfFoodDao;
import edu.android.mainmen.Model.kindsOfFood;


public class KoreanFoodFragment extends Fragment {

    private RecyclerView recycler;
    private List<kindsOfFood> dataset;
    private int index;
    public KoreanFoodFragment() {

    }

    public static KoreanFoodFragment newInstance(int index) {
        KoreanFoodFragment fm = new KoreanFoodFragment();
        fm.setIndex(index);
        return fm;
    }

 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_korean_food, container, false);
        recycler = view.findViewById(R.id.recyclerViewKorean);
        dataset = kindsOfFoodDao.getInstance().getKoreanFood();
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        kindOfFoodAdapter adapter = new kindOfFoodAdapter();
        recycler.setAdapter(adapter);

        return view;
    }// end onCreateView

    public void setIndex(int index) {
        this.index = index;
    }


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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView foodName;

            public ViewHolder(View itemView) {
                super(itemView);
                foodName = itemView.findViewById(R.id.itemMenuText);

            }
        }
    }

}// end KoreanFoodFragment
