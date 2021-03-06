package org.fifthgen.colouringbooks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.controller.main.MainActivity;
import org.fifthgen.colouringbooks.util.DensityUtil;
import org.fifthgen.colouringbooks.util.ImageLoaderUtil;

import java.util.Objects;

/**
 * Created by Swifty on 2015/10/3.
 */
public class UserLoginReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = null;

        if (intent.hasExtra("msg")) {
            action = intent.getStringExtra("msg");
        }

        if (context instanceof MainActivity) {
            if ("loginsuccess".equals(action)) {
                if (MyApplication.user != null) {

                    ImageLoader.getInstance().loadImage(MyApplication.user.getUserIcon(), new ImageSize(DensityUtil.dip2px(context, 32), DensityUtil.dip2px(context, 32)), ImageLoaderUtil.getOpenAllCacheOptions(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            Objects.requireNonNull(((MainActivity) context).getSupportActionBar()).setIcon(new BitmapDrawable(context.getResources(), bitmap));
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    ((MainActivity) context).setTitle(MyApplication.user.getName());
                    if (MainActivity.logout != null) {
                        MainActivity.logout.setVisible(true);
                    }
                }
            } else if ("logoutsuccess".equals(action)) {
                Objects.requireNonNull(((MainActivity) context).getSupportActionBar()).setIcon(0);
                ((MainActivity) context).setTitle(context.getString(R.string.app_name));
                if (MainActivity.logout != null) {
                    MainActivity.logout.setVisible(false);
                }
            }
        }
    }

}

