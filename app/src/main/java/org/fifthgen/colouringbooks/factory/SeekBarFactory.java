package org.fifthgen.colouringbooks.factory;

import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

import org.fifthgen.colouringbooks.R;

import java.util.Locale;

/**
 * Created by Swifty on 2015/8/8.
 */
@SuppressWarnings("unused")
public class SeekBarFactory {
    private static SeekBarFactory seekBarFactory;

    private SeekBarFactory() {
    }

    public static SeekBarFactory getInstance() {
        if (seekBarFactory == null) {
            seekBarFactory = new SeekBarFactory();
        }
        return seekBarFactory;
    }


    public SeekBar getStackSeekBar(final Context context, SeekBar seekBar, final TextView textView) {
        seekBar.setMax(27);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int truly = i + 3;
                SharedPreferencesFactory.saveInteger(context, SharedPreferencesFactory.StackSize, truly);
                textView.setText(String.format(Locale.getDefault(),
                        "%s : %d", context.getText(R.string.undostacksize), truly));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(SharedPreferencesFactory.getInteger(context, SharedPreferencesFactory.StackSize) - 3);
        return seekBar;
    }
}
