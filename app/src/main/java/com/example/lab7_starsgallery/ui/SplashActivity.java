package com.example.lab7_starsgallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab7_starsgallery.R;

public class SplashActivity extends AppCompatActivity {

    private TextView logoStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoStar = findViewById(R.id.splashLogo);

        logoStar.animate().rotation(360f).setDuration(1500);
        logoStar.animate().scaleX(0.3f).scaleY(0.3f).setDuration(3000);
        logoStar.animate().translationYBy(800f).setDuration(2500);
        logoStar.animate().alpha(0f).setDuration(5000);

        new Thread(() -> {
            try {
                Thread.sleep(4500);
                startActivity(new Intent(SplashActivity.this, ListActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
