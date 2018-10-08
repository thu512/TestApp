package com.gsitm.theme;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

//앱 백그라운드 전환 캐치를 위한 커스텀 Application
public class CustomApplication extends Application {
    private AppStatus mAppStatus = AppStatus.FOREGROUND;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }
    // Get app is foreground
    public AppStatus getAppStatus() {
        return mAppStatus;
    }

    // check if app is return foreground
    public boolean isReturnedForground() {
        return mAppStatus.ordinal() == AppStatus.RETURNED_TO_FOREGROUND.ordinal();
    }

    public enum AppStatus {
        BACKGROUND, // app is background
        RETURNED_TO_FOREGROUND, // app returned to foreground(or first launch)
        FOREGROUND; // app is foreground
    }

    public class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        // running activity count
        private int running = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (++running == 1) {
                // running activity is 1,
                // app must be returned from background just now (or first launch)
                mAppStatus = AppStatus.RETURNED_TO_FOREGROUND;
            } else if (running > 1) {
                // 2 or more running activities,
                // should be foreground already.
                mAppStatus = AppStatus.FOREGROUND;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        //액티비티가 onStop될때
        @Override
        public void onActivityStopped(Activity activity) {
            Log.d("LCJ"," "+activity.getClass().getName());
            if(activity.getClass().getName().equals("com.gsitm.theme.ScreenLockActivity")){
                activity.finish();
            }
            //더이상 종료되는 액티비티가 없을때
            if (--running == 0) {
                Util.getInstance().setScreenState(activity.getApplication(),false);
                mAppStatus = AppStatus.BACKGROUND;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }

}
