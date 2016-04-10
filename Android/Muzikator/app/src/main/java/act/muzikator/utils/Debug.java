package act.muzikator.utils;

import android.util.Log;

/**
 * Created by vcoder on 4/9/16.
 */
public class Debug {
    public static void log(Object target, String msg) {
        Log.d("Muzikator", String.format("%s | %s | %s",
                Thread.currentThread().getName(),
                target.getClass().getSimpleName(),
                msg));
    }

    public static void error(Object target, String msg) {
        Log.e("Muzikator", String.format("%s | %s | %s",
                Thread.currentThread().getName(),
                target.getClass().getSimpleName(),
                msg));
    }
}
