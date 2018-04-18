package edu.android.mainmen.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import edu.android.mainmen.FoodMenuDBHelper;
import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class ResisterActivity extends AppCompatActivity {

    private String participants[] = new String[100];
    private StringBuffer sb;
    int version=1;
    int count=0;
    private FoodMenuDBHelper helper;
    private SQLiteDatabase database;
    private String sql;
    private Cursor cursor;

    private AutoCompleteTextView id_input = findViewById(R.id.id_input);
    private AutoCompleteTextView password_input = findViewById(R.id.password_input);
    private AutoCompleteTextView password_check_input = findViewById(R.id.password_check_input);
    private AutoCompleteTextView name_input = findViewById(R.id.name_input);
    private AutoCompleteTextView age_input = findViewById(R.id.age_input);
    private AutoCompleteTextView email_input = findViewById(R.id.email_input);
    private AutoCompleteTextView password2_input = findViewById(R.id.password2_input);
    private Button join_btn = findViewById(R.id.join_btn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        helper = new FoodMenuDBHelper(ResisterActivity.this, FoodMenuDBHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        nameList();

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId())
                {
                    case R.id.join_btn :
                        sb.setLength(0);
                        helper.insertName(database, (id_input.getText().toString()));
                        nameList();
                        break;


                }
                Intent intent = new Intent(ResisterActivity.this, WelcomeActivity.class);
                ResisterActivity.this.startActivity(intent);

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nameList() {
        sql = "select name from " + helper.tableName;
        cursor = database.rawQuery(sql, null);
        if (cursor != null)
        {
            count = cursor.getCount();
            for (int i = 0; i < count; i++)
            {
                cursor.moveToNext();
                String participant = cursor.getString(0);
                participants[i] = participant;
                sb.append(participants[i] + " ");
            }
            Toast.makeText(this, "회원가입이 완료되었습니다."+sb, Toast.LENGTH_SHORT).show();
            cursor.close();
        }
    }
}
