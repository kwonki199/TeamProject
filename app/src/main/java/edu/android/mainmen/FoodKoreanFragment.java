package edu.android.mainmen;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.android.mainmen.Controller.kindsOfFoodDao;
import edu.android.mainmen.Model.kindsOfFood;


public class FoodKoreanFragment extends Fragment {

    private RecyclerView recycler;
    private itemSelectedCallback callback;
    private List<kindsOfFood> dataset;
    private int index;

    interface itemSelectedCallback{
        void onItemSelected(int position);
    }



    public FoodKoreanFragment() {
    }

    public static FoodKoreanFragment newInstance(int index) {
        FoodKoreanFragment fm = new FoodKoreanFragment();
        fm.setIndex(index);
        return fm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof itemSelectedCallback) {
            callback = (itemSelectedCallback) context;
            // 메인액티비티에 implements FoodKoreanFragment.itemSelectedCallback 를 구현
        } else {
            new RuntimeException("반드시 ContactSelectedCallback을 구현해야 함");
        }
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            kindsOfFood food = dataset.get(position);
            holder.foodName.setText(food.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemSelected(position);

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

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}// end FoodKoreanFragment
