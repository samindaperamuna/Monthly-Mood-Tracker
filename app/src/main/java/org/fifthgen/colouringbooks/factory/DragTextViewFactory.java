package org.fifthgen.colouringbooks.factory;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.TypedValue;
import android.widget.FrameLayout;

import org.fifthgen.colouringbooks.view.DragedTextView;

/**
 * Created by macpro001 on 20/8/15.
 */
@SuppressWarnings("unused")
public class DragTextViewFactory {
    private static DragTextViewFactory ourInstance;
    public int HugeSize = 30;
    public int BigTextSize = 24;
    public int MiddleTextSize = 18;
    public int SmallTextSize = 12;

    private DragTextViewFactory() {
    }

    public static DragTextViewFactory getInstance() {
        if (ourInstance == null)
            ourInstance = new DragTextViewFactory();
        return ourInstance;
    }

    public DragedTextView createUserWordTextView(Context context, String words, int color, int size) {
        DragedTextView dragedTextView = new DragedTextView(context);
        dragedTextView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        dragedTextView.setText(words);
        dragedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        dragedTextView.setTextColor(color);
        dragedTextView.setClickable(true);
        dragedTextView.setTypeface(null, Typeface.BOLD);
        return dragedTextView;
    }

    public DragedTextView createUserWordTextView(Context context, Editable words) {
        DragedTextView dragedTextView = new DragedTextView(context);
        dragedTextView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        dragedTextView.setText(words);
        dragedTextView.setClickable(true);
        dragedTextView.setTypeface(null, Typeface.BOLD);
        return dragedTextView;
    }
}
