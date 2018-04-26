package edu.android.mainmen.DrawerMenu;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.R;

public class BookmarkCreatorLab {
    private static final int[] IMAGE_IDS = {
            R.drawable.pr1, R.drawable.pr2, R.drawable.pr3
    };

    private List<BookmarkCreator> bookmarkCreatorList;
    private static BookmarkCreatorLab instance = null;

    private BookmarkCreatorLab() {
        bookmarkCreatorList = new ArrayList<>();
        makeDummyData();
    }

    private void makeDummyData() {
        for (int i = 0; i < 100; i++) {
            BookmarkCreator b = new BookmarkCreator("Store Name" + i,
                    "distance", "address",
                    IMAGE_IDS[i % IMAGE_IDS.length]);
            bookmarkCreatorList.add(b);
        }
    }
    public static BookmarkCreatorLab getInstance() {
        if (instance == null) {
            instance = new BookmarkCreatorLab();
        }
        return instance;
    }
    public List<BookmarkCreator> getBookmarkCreatorList() {
        return bookmarkCreatorList;
    }
}
