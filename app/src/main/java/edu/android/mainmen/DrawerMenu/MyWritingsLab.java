package edu.android.mainmen.DrawerMenu;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.R;

public class MyWritingsLab { private static final int[] IMAGE_IDS = {
        R.drawable.pr1, R.drawable.pr2, R.drawable.pr3
};

    private List<MyWritings> MyWritingsList;
    private static MyWritingsLab instance = null;

    private MyWritingsLab() {
        MyWritingsList = new ArrayList<>();
        makeDummyData();
    }

    private void makeDummyData() {
        for (int i = 0; i < 100; i++) {
            MyWritings m = new MyWritings("Store Name" + i,
                    "distance", "address",
                    IMAGE_IDS[i % IMAGE_IDS.length]);
            MyWritingsList.add(m);
        }
    }
    public static MyWritingsLab getInstance() {
        if (instance == null) {
            instance = new MyWritingsLab();
        }
        return instance;
    }
    public List<MyWritings> getMyWritingsList() {
        return MyWritingsList;
    }
}