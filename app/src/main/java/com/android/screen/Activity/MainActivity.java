package com.android.screen.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.screen.R;
import com.android.screen.databinding.ActivityMainBinding;
import com.android.screen.utils.Screenshot;

import java.io.ByteArrayOutputStream;

import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;

public class MainActivity extends AppCompatActivity {

    private View main;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        main = getWindow().getDecorView().getRootView();
    }

    public void onCaptureScreen(View v){
        Bitmap b = Screenshot.takescreenshotOfRootView(main);
        binding.imageView.setImageBitmap(b);
        Intent intent = new Intent(this, DrawingActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra(SCREENSHOT_BITMAP,bytes);
        startActivity(intent);
        finish();
    }
}
