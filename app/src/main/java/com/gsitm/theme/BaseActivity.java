package com.gsitm.theme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    //잠금화면을 제외한 액티비티에서 onResume 호출시 잠금 되어있다면 잠금화면 호출
    @Override
    protected void onResume() {
        super.onResume();
        if(!Util.getInstance().getScreenState(getApplicationContext())){
            startActivity(new Intent(this, ScreenLockActivity.class));
        }


    }
}
