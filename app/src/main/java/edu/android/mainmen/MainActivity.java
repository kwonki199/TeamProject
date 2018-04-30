package edu.android.mainmen;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.security.MessageDigest;

import edu.android.mainmen.Adapter.SectionsPageAdapter;
import edu.android.mainmen.DrawerMenu.MyWritingActivity;
import edu.android.mainmen.DrawerMenu.RouletteActivity;
import edu.android.mainmen.DrawerMenu.LoginActivity;
import edu.android.mainmen.ReviewFragment.ReviewBossamFragment;
import edu.android.mainmen.ReviewFragment.ReviewChikenFragment;
import edu.android.mainmen.ReviewFragment.ReviewFastFoodFragment;
import edu.android.mainmen.ReviewFragment.ReviewSnackBarFragment;
import edu.android.mainmen.Search.SearchActivity;
import edu.android.mainmen.Upload.FirebaseUploadActivity;
import edu.android.mainmen.ReviewFragment.ReviewChinaFragment;
import edu.android.mainmen.ReviewFragment.ReviewJapanFragment;
import edu.android.mainmen.ReviewFragment.ReviewKoreanFragment;
import edu.android.mainmen.ReviewFragment.ReviewWesternFragment;
import edu.android.mainmen.ReviewFragment.ReadReviewFragment;

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
    private FloatingActionButton WriteReviewButton;
    //    private TextView header_name;
    private TextView header_email;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    private long lastTimeBackPressed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertNoticeButton();
        getAppKeyHash(); // hash 키 불러오기

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        //액션바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        // drawer
        navigationView = findViewById(R.id.nav_view);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        header_email = (TextView) view.findViewById(R.id.header_user_Email);

//       // drawer에 사용자 아이디 나타냄
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            header_email.setText(auth.getCurrentUser().getEmail());
        } else {
            header_email.setText("로그인이 되어있지 않습니다.");
        }

        // 업로드 버튼
        WriteReviewButton = findViewById(R.id.fab);
        WriteReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, FirebaseUploadActivity.class);
                    startActivity(intent);
                }else{
                    alertLoginButtons();
//                Toast.makeText(this, "로그인이 필요합니다~ㅎㅎㅎ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        email = findViewById(R.id.alert_username);
        password = findViewById(R.id.alert_password);

    }

    /* ↓ Back 버튼 누를 시 앱 종료 기능 */
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
        TextView tv = findViewById(R.id.info);

        int id = item.getItemId();


        if (id == R.id.nav_membershipInformation) { // 로그인
            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "로그인중입니다~~~ ㅎㅎㅎ", Toast.LENGTH_SHORT).show();
                header_email.setText(auth.getCurrentUser().getEmail());
            }

        } else if (id == R.id.nav_mywritings) { // 리뷰 작성
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
            Intent intent = new Intent(MainActivity.this, FirebaseUploadActivity.class);
            startActivity(intent);
            }else{
                alertLoginButtons();
//                Toast.makeText(this, "로그인이 필요합니다~ㅎㅎㅎ", Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.nav_writings) { // 내가 쓴글
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, MyWritingActivity.class);
                startActivity(intent);
            }else{
                alertLoginLayout();
//                Toast.makeText(this, "로그인이 필요합니다~ㅎㅎㅎ", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_bookmark) { // 즐겨찾기

            // 아직 즐겨찾기 구현안됨

        } else if (id == R.id.nav_logout) { // 로그아웃
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                auth.signOut();
                header_email.setText("로그인이 되어있지 않습니다.");
                Toast.makeText(this, "로그아웃되었습니다.~~~", Toast.LENGTH_SHORT).show();
            }else {

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
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FoodListFragment(), "홈");
        adapter.addFragment(new ReadReviewFragment(), "리뷰");
        adapter.addFragment(new ReviewKoreanFragment(), "한식");
        adapter.addFragment(new ReviewChinaFragment(), "중식");
        adapter.addFragment(new ReviewWesternFragment(), "피자/양식");
        adapter.addFragment(new ReviewJapanFragment(), "일식");
        adapter.addFragment(new ReviewChikenFragment(), "치킨");
        adapter.addFragment(new ReviewSnackBarFragment(), "분식");
        adapter.addFragment(new ReviewFastFoodFragment(), "패스트푸드");
        adapter.addFragment(new ReviewBossamFragment(), "족발/보쌈");

        viewPager.setAdapter(adapter);
    }

    // 탭과 프래그먼트 연동
    @Override
    public void onHomeSelected(int position) {
        Log.i(TAG, "position=" + position);
        mViewPager.setCurrentItem(position + 1);
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

    public void alertLoginLayout(){

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
                        loginUser(email.getText().toString(),password.getText().toString());
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

}// end MainActivity
