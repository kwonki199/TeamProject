package edu.android.mainmen.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import edu.android.mainmen.MainActivity;
import edu.android.mainmen.R;

public class ChangingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chaging);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        TextView id_input = findViewById(R.id.id_input);
        AutoCompleteTextView password_input = findViewById(R.id.password_input);
        AutoCompleteTextView password_check_input = findViewById(R.id.password_check_input);
        AutoCompleteTextView name_input = findViewById(R.id.name_input);
        AutoCompleteTextView age_input = findViewById(R.id.age_input);
        AutoCompleteTextView email_input = findViewById(R.id.email_input);
        AutoCompleteTextView password2_input = findViewById(R.id.password2_input);

        Button change_btn = findViewById(R.id.change_btn);
        Button withdrawal = findViewById(R.id.withdrawal);

            change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change_btn = new Intent(ChangingActivity.this, MainActivity.class);
                ChangingActivity.this.startActivity(change_btn);


        }
    });
            withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent withdrawal = new Intent(ChangingActivity.this, MainActivity.class);
                 ChangingActivity.this.startActivity(withdrawal);
         }

    });

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
}
