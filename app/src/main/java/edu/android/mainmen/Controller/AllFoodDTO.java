package edu.android.mainmen.Controller;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class AllFoodDTO {

    public String food;
    public String imageUrl;
    public String imageName;
    public String title;
    public String description;
    public String uid;
    public String userId;
    public float ratingScore;

    public String Location;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

}
