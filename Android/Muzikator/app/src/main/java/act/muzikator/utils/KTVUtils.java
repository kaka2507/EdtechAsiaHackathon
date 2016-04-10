package act.muzikator.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import act.muzikator.R;

/**
 * Created by vcoder on 4/9/16.
 */
public class KTVUtils {
    private static final String TAG = "KTVUtils";
    static public String GetRecordFilePath(Context context, int resId) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getString(R.string.app_storage) + "/" + resId + ".aac";
    }

    public static boolean CreateDirIfNotExists(Context context) {
        boolean ret = true;
        File file = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_storage));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }
}
