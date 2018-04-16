package edu.android.mainmen.Controller;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Model.kindsOfFood;
import edu.android.mainmen.R;

public class kindsOfFoodDao {
    private static final int[] IMAGE_IDS = {
            R.drawable.p1, R.drawable.p2, R.drawable.p3
    };
    private static final String[] ALL_MENU = {
            "한식", "중식", "양식"
    };
    private List<kindsOfFood> foodList = new ArrayList<>();
    private static kindsOfFoodDao instance = null;

    public static kindsOfFoodDao getInstance() {
        if (instance == null) {
            instance = new kindsOfFoodDao();
        }
        return instance;
    }

    private kindsOfFoodDao(){
        for(int i=0 ; i<3 ; i++) {
            kindsOfFood kindsOfFood = new kindsOfFood(ALL_MENU[i], IMAGE_IDS[i]);
            foodList.add(kindsOfFood);
        }
    }


    public List<kindsOfFood> getFoodList() {
        return foodList;
    }

}
