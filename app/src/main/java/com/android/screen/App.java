package com.android.screen;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

public class App extends Application {

    private Activity activeActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        setupActivityListener();
        Intent intent = new Intent(getApplicationContext(),ShakeService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startService(intent);
        //startService(new Intent(getApplicationContext(),ShakeService.class));
    }

    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
                activeActivity = activity;
            }
            @Override
            public void onActivityPaused(Activity activity) {
                activeActivity = null;
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public Activity getActiveActivity(){
        return activeActivity;
    }

}
