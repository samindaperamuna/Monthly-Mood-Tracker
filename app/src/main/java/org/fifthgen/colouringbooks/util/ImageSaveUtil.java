package org.fifthgen.colouringbooks.util;

import java.util.Locale;

/**
 * Created by GameGFX Studio on 2015/9/21.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ImageSaveUtil {
    public static final String OldMainUrl = "http://23.99.106.254";
    public static final String ThemeThumbUrl = OldMainUrl + "/%d/category.png"; //get add categoryid + /category.png
    public static final String ImageThumbUrl = OldMainUrl + "/%d/t_%d.jpg"; //get add categoryid and imageid
    public static final String ImageLargeUrl = OldMainUrl + "/%d/f_%d.png"; //get add categoryid and imageid

    public static String convertImageLargeUrl(String url) {
        try {
            int[] ids = parseIds(url);
            return String.format(Locale.getDefault(), ImageLargeUrl, ids[0], ids[1]);
        } catch (Exception e) {
            L.e(e.toString());
            return url;
        }
    }

    private static int[] parseIds(String url) {
        int[] ids = new int[2];
        ids[0] = getNum(url.split("&image=")[0]);
        ids[1] = getNum(url.split("&image=")[1]);

        return ids;
    }

    private static int getNum(String str) {
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[^0-9]", "");

        }

        return Integer.valueOf(dest);
    }
}
