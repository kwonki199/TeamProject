package edu.android.mainmen;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

public class FoodListFragment extends Fragment {

    private RecyclerView recycler;
    private List<kindsOfFood> dataset;


    interface HomeSelectedCallback{
        void onHomeSelected(int position);  // 포지션 정보 매개변수주기
    }

    private HomeSelectedCallback callback;

    public FoodListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeSelectedCallback) {
            callback = (HomeSelectedCallback) context;
        }else{
            new RuntimeException("콜백구현하세요. - 동희");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_food, container, false);
        recycler = view.findViewById(R.id.recyclerView);
        dataset = kindsOfFoodDao.getInstance().getFoodList();
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            kindsOfFood food = dataset.get(position);
            kindsOfFoodDao dao = kindsOfFoodDao.getInstance();
            holder.foodName.setText(food.getName());
            holder.foodImageNo.setImageResource(dao.ImageSource(position));    /////////////////////

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onHomeSelected(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView foodName;
            public ImageView foodImageNo; /////////////////////////


            public ViewHolder(View itemView) {
                super(itemView);
                foodName = itemView.findViewById(R.id.itemMenuText);
                foodImageNo = itemView.findViewById(R.id.itemMenuImage); ///////////////

            }
        }
    }

}// end FoodListFragment
