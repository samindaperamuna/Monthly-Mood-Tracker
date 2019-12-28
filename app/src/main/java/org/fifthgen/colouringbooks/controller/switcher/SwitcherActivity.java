package org.fifthgen.colouringbooks.controller.switcher;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;

public class SwitcherActivity extends AppCompatActivity {

    private ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);
        imagePreview = findViewById(R.id.preview);

        String imgPath = getIntent().getStringExtra(MyApplication.BIGPIC);
        imagePreview.setImageURI(Uri.parse(imgPath));
    }
}
