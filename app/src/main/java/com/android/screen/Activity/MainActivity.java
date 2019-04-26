package com.android.screen.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.screen.EditImageActivity;
import com.android.screen.R;
import com.android.screen.ScreenBaseActivity;
import com.android.screen.ShakeService;
import com.android.screen.databinding.ActivityMainBinding;
import com.android.screen.utils.ScreenshotManager;
import com.android.screen.utils.ShakeDetector;

import java.io.ByteArrayOutputStream;

import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private View main;
    ActivityMainBinding binding;
    Bitmap b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intent intent = new Intent(this,ShakeService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startService(intent);
        /*main = getWindow().getDecorView().getRootView();

       /* mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                Toast.makeText(MainActivity.this, "Shaked!!!", Toast.LENGTH_SHORT).show();
                captureScreen();
            }
        });*/
    }

    /*public void captureScreen(){
                b = ScreenshotManager.screenShot(main);
                binding.imageView.setImageBitmap(b);
                Intent intent = new Intent(MainActivity.this, EditImageActivity.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra(SCREENSHOT_BITMAP,bytes);
                startActivity(intent);
                finish();
    }

   /* @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }*/

}
