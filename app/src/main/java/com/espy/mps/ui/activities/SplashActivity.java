package com.espy.mps.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.espy.mps.R;
import com.espy.mps.preferences.AppPreference;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        new Handler().postDelayed(() -> {

            if (AppPreference.isLoggedIn(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    SplashActivity.this.finish();
            }else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
