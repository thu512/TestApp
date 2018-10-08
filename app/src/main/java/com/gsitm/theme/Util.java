package com.gsitm.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by lcj on 2018. 9. 16..
 */

public class Util {
    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }


    //화면잠금 비밀번호 저장 여부 불러오기( true: 저장됨 / false: 안됨)
    public boolean getScreenSavePwd(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getBoolean("screenSavePwd", false);
    }

    //화면잠금 비밀번호 저장 여부 값 저장하기( true: 저장됨 / false: 안됨)
    public void setScreenSavePwd(Context context, boolean isLock){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("screenSavePwd", isLock);
        editor.apply();
    }

    //현재 화면잠금 상태 불러오기( true: 잠김 / false: 풀림)
    public boolean getScreenState(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getBoolean("screenState", false);
    }

    //현재 화면잠금 상태 저장하기( true: 잠김 / false: 풀림)
    public void setScreenState(Context context, boolean state){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("screenState", state);
        editor.apply();
    }

    //화면잠금 비밀번호 불러오기
    public String getScreenPwd(Context context){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getString("screenPwd", "");
    }

    //화면잠금 비밀번호 저장
    public void setScreenPwd(Context context, String pwd){
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("screenPwd", pwd);
        editor.apply();
    }

    //지문인증이 가능한 디바이스인지. API 28 미만
    public static boolean isFingerprintAuthAvailable(Context context) {
        FingerprintManagerCompat mFingerprintManager;
        mFingerprintManager = FingerprintManagerCompat.from(context);
        if (mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints()) {
            return true;
        } else {
            return false;
        }
    }

    //지문인증이 가능한 디바이스인지. API 28 이상
    public boolean isSupportBiometricPrompt(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        return false;
    }

    //API 28미만 지문인증 -> FingerprintManagerCompat 가져오기
    public static FingerprintManagerCompat getFingerprintManagerCompat(Context context){
        FingerprintManagerCompat mFingerprintManager;
        mFingerprintManager = FingerprintManagerCompat.from(context);
        return mFingerprintManager;
    }
}
