package com.gsitm.theme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

import com.lcj.forgerycheck.ForgeryCheckTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.Signature;

public class StartActivity extends AppCompatActivity {

    /**
     * 로딩 화면이 떠있는 시간(밀리초단위)
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //앱키 확인
                ForgeryCheckTest ft = new ForgeryCheckTest();
                //99e00c5fcf50cdf32a70a220babcaa3859acd89bc92ecfa791a2763f29bf57a7
                Log.d("LCJ", "앱키: " + ft.getSignaiture(getApplicationContext()));

                //화면 잠금 비번 설정 여부 체크
                Log.d("LCJ", " " + Util.getInstance().getScreenSavePwd(StartActivity.this));
                if (!Util.getInstance().getScreenSavePwd(StartActivity.this)) {
                    Intent mainIntent = new Intent(StartActivity.this, ScreenLockActivity.class);
                    mainIntent.putExtra("state", 1);
                    StartActivity.this.startActivity(mainIntent);

                } else {
                    /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                    Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                    StartActivity.this.startActivity(mainIntent);

                }
                StartActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
