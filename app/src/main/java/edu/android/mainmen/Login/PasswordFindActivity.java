package edu.android.mainmen.Login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import edu.android.mainmen.R;

public class PasswordFindActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_find);

        AutoCompleteTextView id_input = findViewById(R.id.id_input);
        AutoCompleteTextView password2_input = findViewById(R.id.password2_input);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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
}
