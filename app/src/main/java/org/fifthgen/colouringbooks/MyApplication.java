package org.fifthgen.colouringbooks;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.fifthgen.colouringbooks.factory.SharedPreferencesFactory;
import org.fifthgen.colouringbooks.model.bean.UserBean;
import org.fifthgen.colouringbooks.util.ImageLoaderUtil;
import org.fifthgen.colouringbooks.util.L;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by GameGFX Studio on 2018/7/31.
 */
public class MyApplication extends Application {

    public static final String PATHIMG = "coloring/";
    public static final String PATH_MASK = "masks/";
    public static final String BAIDUTRANSLATEAPI = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=iD8ulydDkWhNWFmQEGEGY4m9&";

    public static final String ADMOB_APP_ID = "ca-app-pub-5247973607982667~2117166723";
    public static final String INTERSTITIAL_AD = "ca-app-pub-5247973607982667/1609900266";
    public static final String BANNER_AD = "ca-app-pub-5247973607982667/4551758375";

    public static final String MainUrl = "http://api.fingercoloring.com/pic/extAPI";
    public static final String ThemeListUrl = MainUrl + "/category"; // post pageid from 0
    public static final String ThemeDetailUrl = MainUrl + "/list"; // post categoryid
    public static final String ImageThumbUrl = MainUrl + "/imageres?category=%d&image=t_%d"; //get add categoryid and imageid
    public static final String ImageLargeUrl = MainUrl + "/imageres?category=%d&image=f_%d";  //get add categoryid and imageid

    public static final String THEMEID = "theme_id";
    public static final String BIGPIC = "bigpic";
    public static final String BIGPICFROMUSER = "bigpic_user";
    public static final String THEMENAME = "theme_name";
    public static final String FOLDERIMG = "folder_image";
    public static final int PaintActivityRequest = 900;
    public static final int RepaintResult = 999;
    public static final String BIGPICFROMUSERPAINTNAME = "bigpic_user_name";
    public static final String MASK = "mask";
    public static final String MOOD_DAY = "mood_day";


    public static int screenWidth;
    public static CharSequence SHAREWORK = "share_work";

    public static UserBean.User user;
    public static String userToken;

    public static final Map<Integer, String> COLOR_MAP = new HashMap<Integer, String>() {{
        put(1, "#ffff00");
        put(2, "#ff0000");
        put(3, "#f69679");
        put(4, "#fdc689");
        put(5, "#bd8cbf");
        put(6, "#ec008c");
        put(7, "#f06eaa");
        put(8, "#f49ac1");
        put(9, "#f5989d");
        put(10, "#e074e5");
        put(11, "#e71af2");
        put(12, "#00ffff");
        put(13, "#6dcff6");
        put(14, "#605ca8");
        put(15, "#1a136b");
        put(16, "#0072bc");
        put(17, "#00bff3");
        put(18, "#00a651");
        put(19, "#c4df9b");
        put(20, "#aba000");
        put(21, "#a36209");
        put(22, "#00ffc8");
        put(23, "#4366e3");
        put(24, "#c69c6d");
        put(25, "#f7941d");
        put(26, "#00ff5d");
        put(27, "#7ab905");
        put(28, "#cf2d7e");
        put(29, "#e1e343");
        put(30, "#caaaff");
        put(31, "#875bcc");
    }};


    public static void initLanguage(Context context) {
        int lancode = SharedPreferencesFactory.getInteger(context, SharedPreferencesFactory.LanguageCode, 0);
        if (lancode == 0) {
            return;
        }
        if (lancode == 1) {
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
        } else if (lancode == 2) {
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);
        } else if (lancode == 3) {
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);
        } else if (lancode == 4) {
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.FRENCH;
            resources.updateConfiguration(config, dm);
        }
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm != null ? wm.getDefaultDisplay().getWidth() : 0;
    }

    @SuppressWarnings("unused")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm != null ? wm.getDefaultDisplay().getHeight() : 0;
    }

    public static String getVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            L.e("VersionE", e.getMessage());
            e.printStackTrace();
            return "0";
        }
    }

    public static void restart(Context context) {
        if (context == null)
            return;

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        context.startActivity(intent);
        if (context instanceof AppCompatActivity)
            ((AppCompatActivity) context).overridePendingTransition(0, 0);
    }

    public static int getCurrentLanguageCode(Context context) {
        int lancode = SharedPreferencesFactory.getInteger(context, SharedPreferencesFactory.LanguageCode, 0);
        if (lancode != 0) {
            return lancode;
        } else {
            if (context.getResources().getConfiguration().locale.getCountry().toUpperCase().equals("CN"))
                return 1;
            else if (context.getResources().getConfiguration().locale.getCountry().toUpperCase().equals("TW"))
                return 2;
            else if (context.getResources().getConfiguration().locale.getCountry().toUpperCase().equals("EN"))
                return 3;
            else
                return 4;
        }
    }

    public static void setLanguageCode(Context context, int lancode) {
        if (lancode == 0) {
            return;
        }

        if (lancode == 1) {
            SharedPreferencesFactory.saveInteger(context, SharedPreferencesFactory.LanguageCode, 1);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
            configChanged(context, config);
        } else if (lancode == 2) {
            SharedPreferencesFactory.saveInteger(context, SharedPreferencesFactory.LanguageCode, 2);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);
            configChanged(context, config);
        } else if (lancode == 3) {
            SharedPreferencesFactory.saveInteger(context, SharedPreferencesFactory.LanguageCode, 3);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);
            configChanged(context, config);
        } else if (lancode == 4) {
            SharedPreferencesFactory.saveInteger(context, SharedPreferencesFactory.LanguageCode, 4);
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.FRENCH;
            resources.updateConfiguration(config, dm);
            configChanged(context, config);
        }
    }

    private static void configChanged(Context context, Configuration configuration) {
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLanguage(this);
        initImageLoader();

        screenWidth = getScreenWidth(this);
    }

    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoaderUtil.getInstance().init(config);
    }
}
