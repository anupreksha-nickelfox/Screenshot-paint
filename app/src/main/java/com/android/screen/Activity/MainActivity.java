package com.android.screen.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.screen.EditImageActivity;
import com.android.screen.R;
import com.android.screen.databinding.ActivityMainBinding;
import com.android.screen.utils.ScreenshotManager;

import java.io.ByteArrayOutputStream;

import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;

public class MainActivity extends AppCompatActivity {

    private View main;
    ActivityMainBinding binding;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        main = getWindow().getDecorView().getRootView();
    }

    public void CaptureScreen(View v){
        //Bitmap b = ScreenshotManager.takescreenshotOfRootView(main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
        },500);


    }

}
