package org.fifthgen.colouringbooks.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.util.L;

/**
 * Created by macpro001 on 4/8/15.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.initLanguage(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // obtain current focus normally is EditText
            View v = getCurrentFocus();
            try {
                if (isShouldHideInput(v, ev)) {
                    hideSoftInput(v.getWindowToken());
                }
            } catch (Exception e) {
                L.e(e.toString());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * According compared EditText where the user clicks on the coordinates and
     * the coordinates, to determine whether to hide the keyboard, because no
     * need to hide when the user clicks the EditText
     *
     * @param v     View object.
     * @param event Motion event object.
     * @return Whether the output should be hidden.
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // If the focus is not the EditText is ignored, this occurs on view just
        // been drawn, the first focus is not EditView, and the user selects
        // another focus with the trackball
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
