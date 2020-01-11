package org.fifthgen.colouringbooks.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fifthgen.colouringbooks.util.L;

/**
 * Created by GameGFX Studio on 2015/8/5.
 */
@SuppressWarnings("unused")
public class FCDBHelper extends SQLiteOpenHelper {

    public static final String MOOD_TABLE = "mood_table";

    public static final String APP_TABLE = "mood_tracker_table";
    public static final String APP_IMAGE_TABLE = "mood_tracker_imagetable";

    public static final String MOOD_TABLE_COL_0 = "date";

    public static final String FCTABLE_COL_0 = "ThemeID";
    public static final String FCTABLE_COL_1 = "ThemeName";
    public static final String FCTABLE_COL_2 = "Status";

    public static final String FCIMAGETABLE_COL_0 = "theme_id";
    public static final String FCIMAGETABLE_COL_1 = "pic_id";
    public static final String FCIMAGETABLE_COL_2 = "Status";
    public static final String FCIMAGETABLE_COL_3 = "WvHRadio";
    public static final String MOOD_TABLE_COL_1 = "mood";
    public static final String MOOD_TABLE_COL_2 = "notes";
    private static final String DBNAME = "mood_tracker.db";


    private static final int version = 5;

    public FCDBHelper(Context context) {
        super(context, DBNAME, null, version);
    }

    public FCDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + APP_TABLE + "( " +
                FCTABLE_COL_0 + " INTEGER, " +
                FCTABLE_COL_1 + " varchar(30), " +
                FCTABLE_COL_2 + " INTEGER DEFAULT 0" + " );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + APP_IMAGE_TABLE + "( " +
                FCIMAGETABLE_COL_0 + " INTEGER, " +
                FCIMAGETABLE_COL_1 + " INTEGER, " +
                FCIMAGETABLE_COL_2 + " INTEGER DEFAULT 0" + " );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + MOOD_TABLE + "( " +
                MOOD_TABLE_COL_0 + " DATE, " +
                MOOD_TABLE_COL_1 + " VARCHAR(100), " +
                MOOD_TABLE_COL_2 + " VARCHAR(250) " + " );");

        onUpgrade(sqLiteDatabase, 3, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                L.e("upgrade to" + i);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + APP_IMAGE_TABLE);
                onCreate(sqLiteDatabase);
            case 3:
            case 4:
                L.e("upgrade to" + i);

                try {
                    sqLiteDatabase.execSQL("ALTER TABLE " + APP_IMAGE_TABLE + " ADD COLUMN " +
                            FCIMAGETABLE_COL_3 + " REAL;");
                } catch (Exception e) {
                    L.e(e.toString());
                }
        }
    }
}
