package edu.android.mainmen;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    private static final String KEY_MSG = "key_current_progress";



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Message에서 데이터를 읽어서 ProgressBar와 TextView를 업데이트
            Bundle data = msg.getData();
            int progress = data.getInt(KEY_MSG);
            progressBar.setProgress(progress);
            textView.setText(progress + "%");
        }
    };

    private ProgressBar progressBar;
    private TextView textView;
    private Thread progThread;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        hideActionbar();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(TutorialActivity.this,
                        Tutorial2Activity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);

        int[] imgs = {R.drawable.ic_h1, R.drawable.ic_h2,R.drawable.ic_h3,
                R.drawable.ic_h4, R.drawable.ic_h5,R.drawable.ic_h6,
                R.drawable.ic_h7, R.drawable.ic_h8,R.drawable.ic_h9,
                R.drawable.ic_h10};

        imageView = findViewById(R.id.imageView21);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        startProgress(textView);


    }// onCreate


    private void hideActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void startProgress(View view) {
        progThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int progress = 0;
                    while (progress <= 100) {
                        // Handler의 Message를 사용해서 progress 정보를 보냄
                        // progress를 증가 -> 잠깐 대기
                        Message msg = handler.obtainMessage();
                        Bundle data = new Bundle();
                        data.putInt(KEY_MSG, progress);
                        msg.setData(data);
                        handler.sendMessage(msg);

                        progress += 2;

                        synchronized (this) {
                            wait(100); // 100 ms = 0.1초


                        }
                    } // end while
                } catch (InterruptedException e) {
                    // TODO: 쓰레드를 interrupt해서 종료시킬 때


                }
            } // end run()
        });
        progThread.start();
    }


}
