package edu.android.mainmen.Controller;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Model.kindsOfFood;
import edu.android.mainmen.R;

public class kindsOfFoodDao {
    private static final int[] IMAGE_IDS = {
            R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.taeri1,R.drawable.taeri2,R.drawable.teari3

    };
    private static final String[] ALL_MENU = {
            "한식", "중식", "양식"
    };
    private static final String[] KOREAN_FOOD = {
            "김치찌개", "삼겹살", "비빕밥","제육볶음","보쌈","족발"
    };


    private List<kindsOfFood> foodList = new ArrayList<>();
    private List<kindsOfFood> koreaFood = new ArrayList<>();
    private static kindsOfFoodDao instance = null;

    public static kindsOfFoodDao getInstance() {
        if (instance == null) {
            instance = new kindsOfFoodDao();
        }
        return instance;
    }

    private kindsOfFoodDao(){
        for(int i=0 ; i<ALL_MENU.length ; i++) {
            kindsOfFood kindsOfFood = new kindsOfFood(ALL_MENU[i], IMAGE_IDS[i]);
            foodList.add(kindsOfFood);
        }
        for(int i=0; i<KOREAN_FOOD.length ; i++) {
            kindsOfFood kindsOfFood=new kindsOfFood(KOREAN_FOOD[i],IMAGE_IDS[i]);
        }
    }




    public List<kindsOfFood> getKoreaFood(){
        return koreaFood;
    }

    public List<kindsOfFood> getFoodList() {
        return foodList;
    }

}
