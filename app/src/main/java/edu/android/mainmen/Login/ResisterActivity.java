package edu.android.mainmen.Login;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import edu.android.mainmen.R;

public class ResisterActivity extends AppCompatActivity {

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
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
}
