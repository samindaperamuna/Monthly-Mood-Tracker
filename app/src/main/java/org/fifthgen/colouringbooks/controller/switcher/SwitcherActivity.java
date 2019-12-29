package org.fifthgen.colouringbooks.controller.switcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.controller.paint.PaintActivity;
import org.fifthgen.colouringbooks.factory.MyDialogFactory;
import org.fifthgen.colouringbooks.util.ImageLoaderUtil;
import org.fifthgen.colouringbooks.view.ImageButtonDefine;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwitcherActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.preview)
    ImageView imagePreview;

    @BindView(R.id.color)
    ImageButtonDefine colourButton;

    @BindView(R.id.mood)
    ImageButtonDefine moodButton;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);

        ButterKnife.bind(this);

        this.colourButton.setOnClickListener(this);
        this.moodButton.setOnClickListener(this);

        this.url = getIntent().getStringExtra(MyApplication.BIGPIC);

        ImageLoaderUtil.getInstance().displayImage(url, imagePreview, ImageLoaderUtil.DetailImageOptions());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color:
                Intent intent = new Intent(this, PaintActivity.class);
                intent.putExtra(MyApplication.BIGPIC, url);
                startActivity(intent);

                break;
            case R.id.mood:
                new MyDialogFactory(this).showMoodSwitchDialog();

                break;
        }
    }
}
