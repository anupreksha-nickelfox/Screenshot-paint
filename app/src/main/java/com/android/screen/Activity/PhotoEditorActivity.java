package com.android.screen.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.screen.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

import static com.android.screen.utils.Constant.SCREENSHOT_BITMAP;

public class PhotoEditorActivity extends AppCompatActivity {
    PhotoEditor mPhotoEditor;
    Bitmap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);

        if (getIntent().hasExtra(SCREENSHOT_BITMAP)) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra(SCREENSHOT_BITMAP), 0, getIntent().
                            getByteArrayExtra(SCREENSHOT_BITMAP).length);
        }

        PhotoEditorView mPhotoEditorView = findViewById(R.id.photoEditorView);

        Drawable drawable = new BitmapDrawable(getResources(), b);
        mPhotoEditorView.getSource().setImageDrawable(drawable);

        //Use custom font using latest support library
       // Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);

//loading font from assest
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true)
                .build();

        mPhotoEditor.setBrushDrawingMode(true);
        mPhotoEditor.setBrushSize(16);
    }
}
