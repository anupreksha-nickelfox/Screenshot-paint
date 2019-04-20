package com.android.screen.Activity;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.screen.databinding.ActivityDrawingBinding;
import com.android.screen.databinding.ActivityMainBinding;
import com.android.screen.utils.Permissions;
import com.android.screen.R;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.screen.utils.Constant.DATE_PATTERN;
import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;
import static com.android.screen.utils.Constant.TOAST_MESSAGE;

public class DrawingActivity extends AppCompatActivity implements View.OnTouchListener {
    ActivityDrawingBinding binding;
    Bitmap b;
    Bitmap mutableBitmap;
    Bitmap resizedBitmap;
    Canvas canvas;
    Paint paint;
    float downx = 0, downy = 0, upx = 0, upy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing);

        if (getIntent().hasExtra(SCREENSHOT_BITMAP)) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra(SCREENSHOT_BITMAP), 0, getIntent().
                            getByteArrayExtra(SCREENSHOT_BITMAP).length);
        }

        Permissions.checkPermissions(DrawingActivity.this);

        mutableBitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        resizedBitmap = Bitmap.createScaledBitmap(mutableBitmap,
                binding.imageViewDraw.getLayoutParams().width,binding.imageViewDraw.getLayoutParams().height,false);
        binding.imageViewDraw.setImageBitmap(resizedBitmap);
        canvas = new Canvas(resizedBitmap);
        paint = new Paint();

        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);

        binding.imageViewDraw.setOnTouchListener(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            return;
        }
    }


    public void onSave(View view) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, new SimpleDateFormat(DATE_PATTERN).format(new Date()));

        Uri imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
            OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, imageFileOS);
            Snackbar snackbar = Snackbar.make(view, TOAST_MESSAGE, Snackbar.LENGTH_LONG);
            snackbar.show();

        } catch (Exception e) {
            Log.v("EXCEPTION", e.getMessage());
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                binding.imageViewDraw.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                binding.imageViewDraw.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }
}
