package org.fifthgen.colouringbooks.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.L;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.model.bean.CacheImageBean;
import org.fifthgen.colouringbooks.model.bean.MoodBean;
import org.fifthgen.colouringbooks.model.bean.PictureBean;
import org.fifthgen.colouringbooks.model.bean.ThemeBean;
import org.fifthgen.colouringbooks.model.db.FCDBHelper;
import org.fifthgen.colouringbooks.util.DateTimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by GameGFX Studio on 2015/8/5.
 */
public class FCDBModel {
    private static FCDBModel fcdbModel;
    private SQLiteDatabase db;
    private Cursor cursor;
    private FCDBHelper fcdbHelper;

    private FCDBModel() {
    }

    public static FCDBModel getInstance() {
        if (fcdbModel == null) {
            fcdbModel = new FCDBModel();
        }

        return fcdbModel;
    }

    public List<ThemeBean.Theme> readThemeList(Context context) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getReadableDatabase();
        cursor = db.query(FCDBHelper.FCTABLE, null, null, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            closeDBandCursor();

            return null;
        } else {
            List<ThemeBean.Theme> themeBeans = new ArrayList<>();

            while (cursor.moveToNext()) {
                themeBeans.add(new ThemeBean.Theme(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }

            closeDBandCursor();

            return themeBeans;
        }
    }

    public void insertNewThemes(Context context, List<ThemeBean.Theme> themeBeanList) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();

        for (ThemeBean.Theme theme : themeBeanList) {
            ContentValues value = new ContentValues();
            value.put(FCDBHelper.FCTABLE_COL_0, theme.getC());
            value.put(FCDBHelper.FCTABLE_COL_1, theme.getN());
            value.put(FCDBHelper.FCTABLE_COL_2, theme.getStatus());

            db.insert(FCDBHelper.FCTABLE, null, value);
        }

        closeDBandCursor();
    }


    public List<PictureBean.Picture> readPicList(Context context, int theme_id) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getReadableDatabase();
        cursor = db.query(FCDBHelper.FCIMAGETABLE, null, FCDBHelper.FCIMAGETABLE_COL_0 + "=" + theme_id, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            closeDBandCursor();

            return null;
        } else {
            List<PictureBean.Picture> pictureBeans = new ArrayList<>();

            while (cursor.moveToNext()) {
                pictureBeans.add(new PictureBean.Picture(cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3)));
            }

            closeDBandCursor();

            return pictureBeans;
        }
    }

    public void insertPics(Context context, int id, List<PictureBean.Picture> pictureBeans) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();

        for (PictureBean.Picture picBean : pictureBeans) {
            ContentValues value = new ContentValues();
            value.put(FCDBHelper.FCIMAGETABLE_COL_0, id);
            value.put(FCDBHelper.FCIMAGETABLE_COL_1, picBean.getId());
            value.put(FCDBHelper.FCIMAGETABLE_COL_2, picBean.getStatus());
            value.put(FCDBHelper.FCIMAGETABLE_COL_3, picBean.getWvHradio());
            db.insert(FCDBHelper.FCIMAGETABLE, null, value);
        }

        closeDBandCursor();
    }

    private void closeDBandCursor() {
        if (cursor != null) cursor.close();
        if (db != null) db.close();
    }

    public void deleteAllRows(Context context, String table) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();
        db.delete(table, null, null);
        closeDBandCursor();
    }

    public void deleteThisCategoryid(Context context, int categoryid, String fcImageTable) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();
        db.delete(fcImageTable, FCDBHelper.FCIMAGETABLE_COL_0 + " = " + categoryid, null);
        closeDBandCursor();
    }

    public List<CacheImageBean> readHaveCacheImages(Context context) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();
        cursor = db.query(FCDBHelper.FCIMAGETABLE, null, null, null, null, null, null);

        List<CacheImageBean> cacheImageBeans = new ArrayList<>();

        if (cursor.getCount() == 0) {
            closeDBandCursor();

            return cacheImageBeans;
        } else {
            while (cursor.moveToNext()) {
                String url = String.format(Locale.getDefault(), MyApplication.ImageLargeUrl,
                        cursor.getInt(0), cursor.getInt(1));
                File file = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());

                if (file != null && file.exists()) {
                    float aFloat = 0f;

                    try {
                        aFloat = cursor.getFloat(3);
                    } catch (Exception e) {
                        L.e(e.toString());
                    }

                    cacheImageBeans.add(new CacheImageBean(url, aFloat));
                }
            }

            return cacheImageBeans;
        }
    }

    /**
     * Insert a new mood object into the SQLite database.
     *
     * @param context Application context.
     * @param mood    Mood object to be saved.
     * @return Whether a new record was created.
     */
    public boolean insertMood(Context context, MoodBean mood) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FCDBHelper.MOOD_TABLE_COL_0, DateTimeUtil.formatTimeStamp(mood.getDate().getTime()));
        values.put(FCDBHelper.MOOD_TABLE_COL_1, mood.getMood());
        values.put(FCDBHelper.MOOD_TABLE_COL_2, mood.getNotes());

        long newRowId = db.insert(FCDBHelper.FCTABLE, null, values);

        closeDBandCursor();

        return newRowId > 0;
    }

    /***
     * Update an existing mood object from the database.
     * @param context Application context.
     * @param mood Mood object to be updated.
     * @return Whether at least one record were updated.
     */
    public boolean updateMood(Context context, MoodBean mood) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FCDBHelper.MOOD_TABLE_COL_1, mood.getMood());
        values.put(FCDBHelper.MOOD_TABLE_COL_2, mood.getNotes());

        String selection = FCDBHelper.MOOD_TABLE_COL_0 + " = ?";
        String[] selectionArgs = {DateTimeUtil.formatTimeStamp(mood.getDate().getTime())};

        int count = db.update(FCDBHelper.FCTABLE, values, selection, selectionArgs);

        closeDBandCursor();

        return count > 0;
    }

    /**
     * Load a mood object from the database.
     *
     * @param context Application context.
     * @param date    Date of the mood object to be loaded.
     * @return The mood object if found, else null.
     */
    public MoodBean loadMood(Context context, Date date) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();
        MoodBean mood = null;

        String selection = FCDBHelper.MOOD_TABLE_COL_0 + " = ?";
        String[] selectionArgs = {DateTimeUtil.formatTimeStamp(date.getTime())};

        cursor = db.query(FCDBHelper.MOOD_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mood = new MoodBean(
                    new Date(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));
        }

        closeDBandCursor();

        return mood;
    }

    /**
     * Delete a mood object from the database.
     *
     * @param context Application context.
     * @param mood    Mood object to be deleted.
     * @return Whether at least one record were deleted.
     */
    public boolean deleteMood(Context context, MoodBean mood) {
        fcdbHelper = new FCDBHelper(context);
        db = fcdbHelper.getWritableDatabase();

        String selection = FCDBHelper.MOOD_TABLE_COL_0 + " LIKE ?";
        String[] selectionArgs = {DateTimeUtil.formatTimeStamp(mood.getDate().getTime())};

        int deletedRows = db.delete(FCDBHelper.MOOD_TABLE, selection, selectionArgs);

        return deletedRows > 0;
    }
}
