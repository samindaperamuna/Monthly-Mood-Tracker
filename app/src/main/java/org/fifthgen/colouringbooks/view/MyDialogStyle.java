package org.fifthgen.colouringbooks.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.util.DensityUtil;

import java.util.Objects;

/**
 * Created by macpro001 on 30/5/15.
 */
@SuppressWarnings("WeakerAccess")
public class MyDialogStyle {
    public static final int POPSTYLE = R.style.MyDialogPop;
    protected Dialog dialog;
    protected Context context;

    /**
     * randomly choose a animation
     *
     * @param context Application context.
     */
    public MyDialogStyle(Context context) {
        this.context = context;
        dialog = new Dialog(context, POPSTYLE);
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnDismissListener(dialog -> MyProgressDialog.DismissDialog());
    }

    public void dismissDialog(Dialog.OnDismissListener onCancelListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setOnDismissListener(onCancelListener);
            dialog.dismiss();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @SuppressWarnings("unused")
    public void reShowDialog() {
        if (!((AppCompatActivity) context).isFinishing()) {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public void showDialog() {
        if (!((AppCompatActivity) context).isFinishing()) {
            dialog.show();
        }
    }

    public void showOneButtonDialog(String titlestr, CharSequence Content, String btntext1, View.OnClickListener listener1, boolean cancelable) {
        dialog.setCancelable(cancelable);

        View detail_layout = View.inflate(context, R.layout.dialog_one_button, null);
        dialog.setContentView(detail_layout);

        TextView content = detail_layout.findViewById(R.id.content);
        TextView title = detail_layout.findViewById(R.id.title);

        if (title != null) {
            title.setText(titlestr);
            title.setVisibility(View.VISIBLE);
        }

        content.setText(Content);
        Button button1 = detail_layout.findViewById(R.id.button1);
        button1.setText(btntext1);
        button1.setOnClickListener(listener1);
        showDialog();
    }

    public void showTwoButtonDialog(CharSequence Content, String btntext1, String btntext2, View.OnClickListener listener1, View.OnClickListener listener2, boolean cancelable) {
        dialog.setCancelable(cancelable);
        View detail_layout = View.inflate(context, R.layout.dialog_two_button, null);
        dialog.setContentView(detail_layout);
        TextView content = detail_layout.findViewById(R.id.content);
        content.setText(Content);
        Button button1 = detail_layout.findViewById(R.id.button1);
        Button button2 = detail_layout.findViewById(R.id.button2);
        button1.setText(btntext1);
        button2.setText(btntext2);
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener2);
        showDialog();
    }

    public void showBlankDialog(String titlestr, String btntext1, View.OnClickListener listener1, boolean cancelable, View view) {
        dialog.setCancelable(cancelable);

        View detail_layout = View.inflate(context, R.layout.dialog_blank, null);
        dialog.setContentView(detail_layout);

        TextView title = detail_layout.findViewById(R.id.title);
        FrameLayout frameLayout = detail_layout.findViewById(R.id.customcontent);

        if (title != null) {
            title.setText(titlestr);
            title.setVisibility(View.VISIBLE);
        }

        frameLayout.addView(view);
        Button button1 = detail_layout.findViewById(R.id.button1);
        button1.setText(btntext1);
        button1.setOnClickListener(listener1);
        showDialog();
    }

    public void showBlankDialog(String titlestr, View view) {
        View.OnClickListener listener = view1 -> dismissDialog();
        showBlankDialog(titlestr, context.getString(R.string.ok), listener, true, view);
    }

    public void showBlankDialog(String titlestr, View view, View.OnClickListener listener) {
        showBlankDialog(titlestr, context.getString(R.string.ok), listener, true, view);
    }


    @SuppressWarnings("SameParameterValue")
    protected void showTwoImageDialog(StringBuffer buffer, Drawable resId1, String s1, Drawable resId2, String s2, View.OnClickListener listener1, View.OnClickListener listener2, boolean b) {
        dialog.setCancelable(b);
        View detail_layout = View.inflate(context, R.layout.dialog_two_button, null);
        dialog.setContentView(detail_layout);
        TextView content = detail_layout.findViewById(R.id.content);
        content.setText(buffer);
        Button button1 = detail_layout.findViewById(R.id.button1);
        Button button2 = detail_layout.findViewById(R.id.button2);
        resId1.setBounds(0, 0, DensityUtil.dip2px(context, 40), DensityUtil.dip2px(context, 40));
        resId2.setBounds(0, 0, DensityUtil.dip2px(context, 40), DensityUtil.dip2px(context, 40));
        button1.setCompoundDrawables(null, resId1, null, null);
        button2.setCompoundDrawables(null, resId2, null, null);
        button1.setText(s1);
        button1.setTextColor(context.getResources().getColor(R.color.maincolor));
        button2.setText(s2);
        button2.setTextColor(context.getResources().getColor(R.color.maincolor));
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener2);
        showDialog();
    }
}
