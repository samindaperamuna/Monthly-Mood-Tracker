package org.fifthgen.colouringbooks.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.fifthgen.colouringbooks.R;

import java.util.Objects;


/**
 * Created by GameGFX Studio on 2015/5/29.
 */
public class MyProgressDialog extends Dialog {
    private static MyProgressDialog dialog;

    public MyProgressDialog(Context context) {
        super(context);
    }


    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message,
                                        boolean cancelable, OnCancelListener cancelListener) {
        dialog = new MyProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View detail_layout = View.inflate(context, R.layout.progress_dialog, null);
        dialog.setContentView(detail_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView textView = detail_layout.findViewById(R.id.progress_text);
        textView.setText(message);
        ImageView imageView = detail_layout.findViewById(R.id.progress);
        imageView.setBackgroundResource(R.drawable.loadinganimation);
        AnimationDrawable rocketAnimation = (AnimationDrawable) imageView.getBackground();
        rocketAnimation.start();
        dialog.show();
        dialog.setOnCancelListener(cancelListener);

        return dialog;
    }

    public static MyProgressDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static MyProgressDialog show(Context context, CharSequence title, CharSequence message, boolean cancelable) {
        return show(context, title, message, cancelable, null);
    }

    public static void DismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static boolean isProgressShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }
}
