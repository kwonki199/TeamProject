package edu.android.mainmen.Controller;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class AllFoodDTO {

    public String food;         // 한식,중식 등 메뉴종류
    public String imageUrl;     // storage 에 저장할 이미지 주소
    public String imageName;    // 삭제할때 필요한 이미지 이름
    public String title;        // 음식이름
    public String description;  // 상세 리뷰
    public String uid;          // 유저 메일
    public String userId;       // 유저 아이디
    public float ratingScore;   // 별점
    public String Location;     // 위치
    public int starCount = 0;   // 좋아요 카운트
    public Map<String, Boolean> stars = new HashMap<>();    // 좋아요 기능
    public String usex;         // 성별
    public String storename;    // 음식점이름



}
