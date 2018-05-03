package edu.android.mainmen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Field;
import java.security.MessageDigest;

import edu.android.mainmen.Adapter.SectionsBannerPageAdapter;
import edu.android.mainmen.Adapter.SectionsPageAdapter;
import edu.android.mainmen.Banner.Banner2Fragment;
import edu.android.mainmen.Banner.Banner3Fragment;
import edu.android.mainmen.Banner.Banner4Fragment;
import edu.android.mainmen.Banner.Banner1Fragment;
import edu.android.mainmen.Banner.Banner5Fragment;
import edu.android.mainmen.Banner.Banner6Fragment;
import edu.android.mainmen.DrawerMenu.MyWritingActivity;
import edu.android.mainmen.DrawerMenu.RouletteActivity;
import edu.android.mainmen.DrawerMenu.LoginActivity;
import edu.android.mainmen.Review.ReviewBossamFragment;
import edu.android.mainmen.Review.ReviewChikenFragment;
import edu.android.mainmen.Review.ReviewFastFoodFragment;
import edu.android.mainmen.Review.ReviewSnackBarFragment;
import edu.android.mainmen.Search.SearchActivity;
import edu.android.mainmen.Upload.FirebaseUploadActivity;
import edu.android.mainmen.Review.ReviewChinaFragment;
import edu.android.mainmen.Review.ReviewJapanFragment;
import edu.android.mainmen.Review.ReviewKoreanFragment;
import edu.android.mainmen.Review.ReviewWesternFragment;
import edu.android.mainmen.Review.ReviewFragment;

// 미해결사항 해결하시면 미해결 -> 해결로 바꿔주세요.
//TODO: Tabbed 기능 추가 홈화면 터치 이외에 탭이동으로도 보기 쉽게 구현 - 해결
//TODO: 탭에 Horizental ScrollView 미적용 탭을 옆으로 스크롤 아직 불가능. - 해결
//TODO: 현재 탭이동시 fragment 1개만 연동중 여러개의 fragment 작성 필요 - 해결
//TODO: 클릭시 세부메뉴(한식 > 김치찌개 ) 넘어가게. - 해결
//TODO: ViewPager position 정보를 fragment 에서 가져와야하는데 잘 모르겠음 ㅠ - 해결
//TODO: 파이어베이스 연동완료
//TODO: 로그인/로그아웃 연동완료
//TODO: 글쓰기 업로드 연동중

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FoodListFragment.HomeSelectedCallback {


    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    //Tabbed
    private ViewPager mViewPager;
    private SectionsPageAdapter mSectionsPageAdapter;
    //    private TextView header_name;
    private TextView header_email;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    private long lastTimeBackPressed = 0;

    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SectionsBannerPageAdapter sectionsBannerPageAdapter;
    private ViewPager mViewPager2;
    private AppBarLayout appBarLayout;
    private FirebaseUser user;
    private Button headerSignin, headerSignout;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertNoticeButton();
        getAppKeyHash(); // hash 키 불러오기

        context = this;


        //파이어베이스
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // 바텀네비게이션
        navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);


        //액션바
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //뷰페이저
        appBarLayout = findViewById(R.id.appBarLayout);
        tabLayout = findViewById(R.id.tabs);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager(), appBarLayout);
        mViewPager = findViewById(R.id.container);
        appBarLayout.setVisibility(View.GONE);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    appBarLayout.setVisibility(View.GONE);
                } else {
                    appBarLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);


        //베너 뷰페이저
        sectionsBannerPageAdapter = new SectionsBannerPageAdapter(getSupportFragmentManager());
        mViewPager2 = findViewById(R.id.containerBanner);
        setupBannerViewPager(mViewPager2);


        // 탭과 옆으로 드로잉할때 연결시키기.
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        // drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {

        }


        //헤더에 사용자 아이디 나타냄
        email = findViewById(R.id.alert_username);
        password = findViewById(R.id.alert_password);
        header_email = (TextView) view.findViewById(R.id.header_user_Email);
        FirebaseUser user1 = auth.getCurrentUser();
        if (user1 != null) {
            header_email.setText(auth.getCurrentUser().getEmail());
        } else {
            header_email.setText("로그인이 되어있지 않습니다.");
        }

        if(user != null){
            hideItem();

        }else{

        }


    }// end onCrete


    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > lastTimeBackPressed + 2000) {
            lastTimeBackPressed = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 맛뷰를 빠져나갑니다.", Toast.LENGTH_SHORT).show();
            return;

        } else { // back 키 2번 누르면 앱 종료
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 액션바 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

    }


    // DrawerMenu 세팅
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        FirebaseUser user = auth.getCurrentUser();



        if(user == null){



        }

        if (id == R.id.nav_membershipInformation) { // 로그인
            if (user == null) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "로그인상태입니다.", Toast.LENGTH_SHORT).show();
                header_email.setText(auth.getCurrentUser().getEmail());


            }
        } else if (id == R.id.nav_mywritings) { // 리뷰 작성
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, FirebaseUploadActivity.class);
                startActivity(intent);
                header_email.setText(auth.getCurrentUser().getEmail());
            } else {
                alertLoginButtons();
            }

        } else if (id == R.id.nav_writings) { // 내가 쓴글
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, MyWritingActivity.class);
                startActivity(intent);
            } else {
                alertLoginLayout();
            }

//        } else if (id == R.id.nav_bookmark) { // 즐겨찾기
//
//            // 아직 즐겨찾기 구현안됨

        } else if (id == R.id.nav_logout) { // 로그아웃
            if (user != null) {
                auth.signOut();
                header_email.setText("로그인이 되어있지 않습니다.");
                Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                showItem();

            } else {
                Toast.makeText(this, "로그아웃 상태입니다", Toast.LENGTH_SHORT).show();

            }
        } else if (id == R.id.nav_coupon) {

        } else if (id == R.id.nav_game) {
            Intent intent = new Intent(MainActivity.this, RouletteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 탭+프래그먼트 세팅 뷰페이저
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager(), appBarLayout);
        adapter.addFragment(new FoodListFragment(), "홈");             // 0 포지션
        adapter.addFragment(new ReviewKoreanFragment(), "한식");
        adapter.addFragment(new ReviewChinaFragment(), "중식");
        adapter.addFragment(new ReviewWesternFragment(), "피자/양식");  // 3 포지션
        adapter.addFragment(new ReviewJapanFragment(), "일식");
        adapter.addFragment(new ReviewChikenFragment(), "치킨");
        adapter.addFragment(new ReviewSnackBarFragment(), "분식");
        adapter.addFragment(new ReviewFastFoodFragment(), "패스트푸드");
        adapter.addFragment(new ReviewBossamFragment(), "족발/보쌈");   // 9 포지션
        adapter.addFragment(new ReviewFragment(), "전체리뷰");
        viewPager.setAdapter(adapter);
    }

    // 배너 뷰페이저
    private void setupBannerViewPager(ViewPager viewPager) {
        SectionsBannerPageAdapter adapter = new SectionsBannerPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Banner1Fragment());
        adapter.addFragment(new Banner2Fragment());             // 0 포지션
        adapter.addFragment(new Banner3Fragment());
        adapter.addFragment(new Banner4Fragment());
        adapter.addFragment(new Banner5Fragment());
        adapter.addFragment(new Banner6Fragment());

        viewPager.setAdapter(adapter);
    }


    // 탭과 프래그먼트 클릭연동
    @Override
    public void onHomeSelected(int position) {
        Log.i(TAG, "position=" + position);

        mViewPager.setCurrentItem(position + 1);

        if (position == 0) {

            mViewPager.setCurrentItem(position + 9);
        } else {

            mViewPager.setCurrentItem(position);
        }

    }


    // 페이스북
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    public void alertNoticeButton() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.notice1)
                .setMessage(R.string.notice2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void alertLoginButtons() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("로그인이 필요합니다")
                .setMessage("로그인창으로 이동하시겠습니까?")
                .setIcon(R.drawable.logotest)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    private EditText email, password;

    public void alertLoginLayout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // get the layout inflater
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        // inflate and set the layout for the dialog
        // pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.alert_login_layout, null))

                // action buttons
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // your sign in code here
                        loginUser(email.getText().toString(), password.getText().toString());
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // remove the dialog from the screen
                        dialog.cancel();
                    }
                })
                .show();

    }

    private void loginUser(final String email, final String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "로그인 완료", Toast.LENGTH_LONG).show();
                        } else {
//                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 바텀네비게이션
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                //홈
                case R.id.navigation_home:

                    mViewPager.setCurrentItem(0);
                    return true;

                //전체리뷰
                case R.id.navigation_allsearch:
                    mViewPager.setCurrentItem(10);
                    return true;

                //글쓰기
                case R.id.navigation_write:
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        Intent intent1 = new Intent(MainActivity.this, FirebaseUploadActivity.class);
                        startActivity(intent1);
                    } else {
                        alertLoginButtons();
                    }
                    return true;

                //마이페이지지
                case R.id.navigation_mypage:
                    FirebaseUser user1 = auth.getCurrentUser();
                    if (user1 != null) {
                        Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "로그인 후 이용하십시오", Toast.LENGTH_SHORT).show();
                    }

                    return true;
            }
            return false;
        }
    };

    //바텀네비게이션 고정
    static class BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }




 public void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_membershipInformation).setVisible(false);
        context = this;

    }


    private void showItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_membershipInformation).setVisible(true);
    }

}// end MainActivity
