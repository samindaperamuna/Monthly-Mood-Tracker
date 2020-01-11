package org.fifthgen.colouringbooks.controller.switcher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.controller.mood.MoodActivity;
import org.fifthgen.colouringbooks.controller.paint.PaintActivity;
import org.fifthgen.colouringbooks.factory.MyDialogFactory;
import org.fifthgen.colouringbooks.model.AsynImageLoader;
import org.fifthgen.colouringbooks.util.ColourTool;
import org.fifthgen.colouringbooks.util.ImageLoaderUtil;
import org.fifthgen.colouringbooks.view.ImageButtonDefine;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ClickableViewAccessibility")
public class SwitcherActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.imageMask)
    ImageView imageMask;

    @BindView(R.id.imagePreview)
    ImageView imagePreview;

    @BindView(R.id.color)
    ImageButtonDefine colourButton;

    @BindView(R.id.mood)
    ImageButtonDefine moodButton;

    private String imgPath;
    private String maskPath;

    private boolean isSavedImage;
    private int fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);

        ButterKnife.bind(this);

        this.colourButton.setOnClickListener(this);
        this.moodButton.setOnClickListener(this);

        this.imgPath = getIntent().getStringExtra(MyApplication.BIGPIC);
        this.maskPath = getIntent().getStringExtra(MyApplication.MASK);

        this.imagePreview.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.loadImages();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color:
                Intent intent = new Intent(this, PaintActivity.class);

                if (isSavedImage) {
                    intent.putExtra(MyApplication.BIGPICFROMUSER, imgPath);
                    intent.putExtra(MyApplication.BIGPICFROMUSERPAINTNAME, fileName);
                } else {
                    intent.putExtra(MyApplication.BIGPIC, imgPath);
                }

                startActivity(intent);

                break;
            case R.id.mood:
                new MyDialogFactory(this).showMoodSwitchDialog();

                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction();

        Matrix inverse = new Matrix();
        ((ImageView) v).getImageMatrix().invert(inverse);
        float[] touchPoint = new float[]{event.getX(), event.getY()};
        inverse.mapPoints(touchPoint);

        final int evX = (int) touchPoint[0];
        final int evY = (int) touchPoint[1];

        if (action == MotionEvent.ACTION_UP && evX > 0 && evY > 0) {
            v.performClick();

            int day = 0;
            int tolerance = 25;

            for (Map.Entry<Integer, String> entry : MyApplication.COLOR_MAP.entrySet()) {
                int entryColor = Color.parseColor(entry.getValue());
                int touchedColor = getHotSpotColour(evX, evY);
                if (ColourTool.closeMatch(entryColor, touchedColor, tolerance)) {
                    day = entry.getKey();
                    break;
                }
            }

            if (day > 0) {
                Log.d(this.getLocalClassName(), "Navigating to mood diary.");

                Intent intent = new Intent(this, MoodActivity.class);
                intent.putExtra(MyApplication.MOOD_DAY, day);
                startActivity(intent);
            }
        }

        return true;
    }

    private void loadImages() {
        ImageLoaderUtil.getInstance().displayImage(maskPath, imageMask, ImageLoaderUtil.DetailImageOptions());

        if (ImageLoaderUtil.hasSavedFile(imgPath)) {
            fileName = this.imgPath.hashCode();
            imgPath = "file:/" + ImageLoaderUtil.ROOT + fileName + ".png";
            isSavedImage = true;

            AsynImageLoader.showImageAsynWithoutCache(imagePreview, imgPath);
        } else {
            isSavedImage = false;

            AsynImageLoader.showImageAsynWithoutCache(imagePreview, imgPath);
        }

    }

    private int getHotSpotColour(int x, int y) {
        this.imageMask.setDrawingCacheEnabled(true);
        Bitmap mask = ((BitmapDrawable) imageMask.getDrawable()).getBitmap();

        if (mask == null) {
            Log.e(this.getLocalClassName(), "Failed to create bitmap mask.");
            return 0;
        } else {
            return mask.getPixel(x, y);
        }
    }
}
