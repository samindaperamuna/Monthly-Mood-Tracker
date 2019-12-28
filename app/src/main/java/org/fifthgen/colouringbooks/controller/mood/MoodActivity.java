package org.fifthgen.colouringbooks.controller.mood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.model.FCDBModel;
import org.fifthgen.colouringbooks.model.bean.MoodBean;

import java.util.Date;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePicker date;
    private EditText mood;
    private EditText notes;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        this.date = findViewById(R.id.date);
        this.mood = findViewById(R.id.moodEdit);
        this.notes = findViewById(R.id.notes);
        this.saveButton = findViewById(R.id.save);
        this.saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            saveMood();
        }
    }

    private void loadMood() {

    }

    private void saveMood() {
        int day = this.date.getDayOfMonth();
        int month = this.date.getMonth();
        int year = this.date.getYear();

        MoodBean moodBean = new MoodBean(
                new Date(year, month, day),
                this.mood.getText().toString(),
                this.notes.getText().toString()
        );

        if (FCDBModel.getInstance().insertMood(this, moodBean)) {
            Toast.makeText(this, this.getString(R.string.mood_saved), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, this.getString(R.string.mood_save_error), Toast.LENGTH_LONG).show();
        }
    }
}
