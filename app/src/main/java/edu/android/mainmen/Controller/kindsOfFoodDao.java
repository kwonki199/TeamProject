package edu.android.mainmen.Controller;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.Model.kindsOfFood;
import edu.android.mainmen.R;

public class kindsOfFoodDao {
    //    private static final int[] IMAGE_IDS = {
//            R.drawable.p1, R.drawable.p2, R.drawable.p3,
//            R.drawable.taeri1,R.drawable.taeri2,R.drawable.teari3
//    };
    private static final String[] ALL_MENU = {
            "전체리뷰","한식", "중식", "양식", "일식","치킨","피자","분식","패스트푸드","족발,보쌈"
    };
    private static final String[] KOREAN_FOOD = {
            "된장찌개" ,"두부김치","삼겹살", "비빕밥","제육볶음","보쌈","족발"
    };
    private static final String[] CHINESE_FOOD = {
            "짜장면", "짬뽕", "탕수육", "라조기", "쟁반짜장", "꿔바로우"
    };
    private static final String[] WESTERNSTYLE_FOOD = {
            "파스타","피자","스테이크"
    };
    private static final int[] ALL_MENU_IMAGE = { ////////////////////////////// 전체 메뉴 이미지 그림
            R.drawable.p1, R.drawable.f1, R.drawable.f2,
            R.drawable.f3, R.drawable.f4, R.drawable.f5,
            R.drawable.f6, R.drawable.f7, R.drawable.f8,
            R.drawable.f9,
    };
    
    private List<kindsOfFood> foodList = new ArrayList<>();
    private List<kindsOfFood> koreanFood = new ArrayList<>();
    private List<kindsOfFood> chineseFood = new ArrayList<>();
    private List<kindsOfFood> westernFood = new ArrayList<>();

    private static kindsOfFoodDao instance = null;

    public static kindsOfFoodDao getInstance() {
        if (instance == null) {
            instance = new kindsOfFoodDao();
        }
        return instance;
    }

    private kindsOfFoodDao(){
        for(int i=0 ; i<ALL_MENU.length ; i++) {
            kindsOfFood food = new kindsOfFood(ALL_MENU[i]);
            foodList.add(food);
        }
        for(int i=0; i<KOREAN_FOOD.length ; i++) {
            kindsOfFood food=new kindsOfFood(KOREAN_FOOD[i]);
            koreanFood.add(food);
        }
        for(int i=0;i<CHINESE_FOOD.length;i++) {
            kindsOfFood food = new kindsOfFood(CHINESE_FOOD[i]);
            chineseFood.add(food);
        }
        for (int i=0;i<WESTERNSTYLE_FOOD.length;i++) {
            kindsOfFood food = new kindsOfFood(WESTERNSTYLE_FOOD[i]);
            westernFood.add(food);
        }

    }

    public List<kindsOfFood> getFoodList() {
        return foodList;
    }

    public List<kindsOfFood> getKoreanFood(){
        return koreanFood;
    }

    public List<kindsOfFood> getChineseFood() {
        return chineseFood;
    }

    public List<kindsOfFood> getWesternFood() {
        return westernFood;
    }

    ////////////////////////////////////////////
    public int ImageSource(int position) {
        return ALL_MENU_IMAGE[position];
    }
}
