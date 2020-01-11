package org.fifthgen.colouringbooks.controller.mood;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.model.FCDBModel;
import org.fifthgen.colouringbooks.model.bean.MoodBean;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {

    @BindView(R.id.datePicker)
    DatePicker datePicker;

    @BindView(R.id.moodEdit)
    EditText moodEdit;

    @BindView(R.id.notesEdit)
    EditText notesEdit;

    @BindView(R.id.saveButton)
    Button saveButton;

    private int moodDay;

    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        ButterKnife.bind(this);

        this.moodDay = getIntent().getIntExtra(MyApplication.MOOD_DAY, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.datePicker.setOnDateChangedListener(this);
        }

        this.saveButton.setOnClickListener(this);

        if (moodDay != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, moodDay);
            datePicker.updateDate(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            loadMood(calendar.getTime());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            saveMood();
            finish();
        }
    }

    private void loadMood(Date date) {
        MoodBean mood = FCDBModel.getInstance().loadMood(this, date);

        if (mood != null) {
            this.moodEdit.setText(mood.getMood());
            this.notesEdit.setText(mood.getNotes());
            this.isEdit = true;
            this.saveButton.setText(getString(R.string.update));
        } else {
            this.moodEdit.setText("");
            this.notesEdit.setText("");
            this.isEdit = false;
            this.saveButton.setText(getString(R.string.save));
        }
    }

    private void saveMood() {
        int day = this.datePicker.getDayOfMonth();
        int month = this.datePicker.getMonth();
        int year = this.datePicker.getYear();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        MoodBean moodBean = new MoodBean(c.getTime(), this.moodEdit.getText().toString(),
                this.notesEdit.getText().toString());

        boolean result;

        if (isEdit)
            result = FCDBModel.getInstance().updateMood(this, moodBean);
        else
            result = FCDBModel.getInstance().insertMood(this, moodBean);

        if (result)
            Toast.makeText(this, this.getString(R.string.mood_saved), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, this.getString(R.string.mood_save_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        loadMood(calendar.getTime());
    }
}
