package com.android.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ScreenBaseActivity extends AppCompatActivity {
    /*protected ScreenApplication mMyApp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (ScreenApplication) this.getApplicationContext();
        Intent intent = new Intent(this,ShakeService.class);
        startService(intent);
    }

    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currActivity = mMyApp.getCurrentActivity();
        /*if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }*/
}
