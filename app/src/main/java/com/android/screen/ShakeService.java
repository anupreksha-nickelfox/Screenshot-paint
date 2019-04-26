package com.android.screen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import com.android.screen.utils.ScreenshotManager;

import java.io.ByteArrayOutputStream;

import static com.android.screen.App.getActiveActivity;
import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;

public class ShakeService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    Bitmap b;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    public void onCreate() {
        super.onCreate();
    }

    private final SimpleActivityLifecycleCallback simpleActivityLifecycleCallback = new SimpleActivityLifecycleCallback() {
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

       // Activity currentActivity = ((ScreenApplication)getApplicationContext()).getCurrentActivity();
       //Activity currentActivity = getRunningActivity();

        if (mAccel > 11) {
            captureScreen(getActiveActivity());
        }
    }

    public void captureScreen(final Activity activity){
        View main =activity.getWindow().getDecorView().getRootView();
        b = ScreenshotManager.screenShot(main);
        Intent intent = new Intent(this, EditImageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra(SCREENSHOT_BITMAP,bytes);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"shaked",Toast.LENGTH_LONG).show();

    }
}
