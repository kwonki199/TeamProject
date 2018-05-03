package edu.android.mainmen.DrawerMenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import edu.android.mainmen.R;

public class RouletteActivity extends AppCompatActivity {

    private ImageButton btnStart;
    private TextView textView;
    private ImageView iv_roulette;

    private Random r;
    private Button closeBtn;

    int degree = 0, degree_old = 0;

    //roulette에 37 섹터가 있기 때문에 (9.72 degrees each)
    private static final float FACTOR = 4.86f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        hideActionBar();

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        textView = (TextView) findViewById(R.id.textView);
        iv_roulette = (ImageView) findViewById(R.id.iv_roulette);
        closeBtn = findViewById(R.id.rouletteCloseBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        r = new Random();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        textView.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setText(currentNumber(360 - (degree % 360)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                iv_roulette.startAnimation(rotate);
            }
        });
    }

    private String currentNumber(int degrees) {
        String text = "";

        // do this for each of the numbers
        if (degrees >= (FACTOR * 1) && degrees < (FACTOR * 3)) {
            text = "아몬드 빼빼로";
        }
        if (degrees >= (FACTOR * 3) && degrees < (FACTOR * 5)) {
            text = "아몬드 빼빼로";
        }
        if (degrees >= (FACTOR * 5) && degrees < (FACTOR * 7)) {
            text = "팔도 왕뚜껑";
        }
        if (degrees >= (FACTOR * 7) && degrees < (FACTOR * 9)) {
            text = "팔도 왕뚜껑";
        }
        if (degrees >= (FACTOR * 9) && degrees < (FACTOR * 11)) {
            text = "팔도 왕뚜껑";
        }
        if (degrees >= (FACTOR * 11) && degrees < (FACTOR * 13)) {
            text = "팔도 왕뚜껑";
        }
        if (degrees >= (FACTOR * 13) && degrees < (FACTOR * 15)) {
            text = "BHC치킨 후라이드 + 콜라";
        }
        if (degrees >= (FACTOR * 15) && degrees < (FACTOR * 17)) {
            text = "BHC치킨 후라이드 + 콜라";
        }
        if (degrees >= (FACTOR * 17) && degrees < (FACTOR * 19)) {
            text = "BHC치킨 후라이드 + 콜라";
        }
        if (degrees >= (FACTOR * 19) && degrees < (FACTOR * 21)) {
            text = "BHC치킨 후라이드 + 콜라";
        }
        if (degrees >= (FACTOR * 21) && degrees < (FACTOR * 23)) {
            text = "BHC치킨 후라이드 + 콜라";
        }
        if (degrees >= (FACTOR * 23) && degrees < (FACTOR * 25)) {
            text = "베스킨라빈스 더블 주니어";
        }
        if (degrees >= (FACTOR * 25) && degrees < (FACTOR * 27)) {
            text = "베스킨라빈스 더블 주니어";
        }
        if (degrees >= (FACTOR * 27) && degrees < (FACTOR * 29)) {
            text = "베스킨라빈스 더블 주니어";
        }
        if (degrees >= (FACTOR * 29) && degrees < (FACTOR * 31)) {
            text = "베스킨라빈스 더블 주니어";
        }
        if (degrees >= (FACTOR * 31) && degrees < (FACTOR * 33)) {
            text = "베스킨라빈스 더블 주니어";
        }
        if (degrees >= (FACTOR * 33) && degrees < (FACTOR * 35)) {
            text = "초코에몽";
        }
        if (degrees >= (FACTOR * 35) && degrees < (FACTOR * 37)) {
            text = "초코에몽";
        }
        if (degrees >= (FACTOR * 37) && degrees < (FACTOR * 39)) {
            text = "초코에몽";
        }
        if (degrees >= (FACTOR * 39) && degrees < (FACTOR * 41)) {
            text = "초코에몽";
        }
        if (degrees >= (FACTOR * 41) && degrees < (FACTOR * 43)) {
            text = "미니언즈 우유";
        }
        if (degrees >= (FACTOR * 43) && degrees < (FACTOR * 45)) {
            text = "미니언즈 우유";
        }
        if (degrees >= (FACTOR * 45) && degrees < (FACTOR * 47)) {
            text = "미니언즈 우유";
        }
        if (degrees >= (FACTOR * 47) && degrees < (FACTOR * 49)) {
            text = "미니언즈 우유";
        }
        if (degrees >= (FACTOR * 49) && degrees < (FACTOR * 51)) {
            text = "미니언즈 우유";
        }
        if (degrees >= (FACTOR * 51) && degrees < (FACTOR * 53)) {
            text = "문화상품권 5천원";
        }
        if (degrees >= (FACTOR * 53) && degrees < (FACTOR * 55)) {
            text = "문화상품권 5천원";
        }
        if (degrees >= (FACTOR * 55) && degrees < (FACTOR * 57)) {
            text = "문화상품권 5천원";
        }
        if (degrees >= (FACTOR * 57) && degrees < (FACTOR * 59)) {
            text = "문화상품권 5천원";
        }
        if (degrees >= (FACTOR * 59) && degrees < (FACTOR * 61)) {
            text = "던킨도너츠 먼치킨 10개팩";
        }
        if (degrees >= (FACTOR * 61) && degrees < (FACTOR * 63)) {
            text = "던킨도너츠 먼치킨 10개팩";
        }
        if (degrees >= (FACTOR * 63) && degrees < (FACTOR * 65)) {
            text = "던킨도너츠 먼치킨 10개팩";
        }
        if (degrees >= (FACTOR * 65) && degrees < (FACTOR * 67)) {
            text = "던킨도너츠 먼치킨 10개팩";
        }
        if (degrees >= (FACTOR * 67) && degrees < (FACTOR * 69)) {
            text = "던킨도너츠 먼치킨 10개팩";
        }
        if (degrees >= (FACTOR * 69) && degrees < (FACTOR * 71)) {
            text = "아몬드 빼빼로";
        }
        if (degrees >= (FACTOR * 71) && degrees < (FACTOR * 73)) {
            text = "아몬드 빼빼로";
        }
        if ((degrees >= (FACTOR * 73) && degrees < 360) || (degrees >=0 && degrees < (FACTOR * 1))) {
            text = "아몬드 빼빼로";
        }

        return text;
    }

    // 뒤로가기 버튼 이거
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
