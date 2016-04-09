package act.muzikator.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class AppUtils {

    // **** Activity helper method ****

    public static void showActivity(Activity current, Class<? extends Activity> nextActivity) {
        Intent intent = new Intent(current, nextActivity);
        current.startActivity(intent);
    }

    public static void showActivityAndFinishCurrent(Activity current, Class<? extends Activity> nextActivity) {
        Intent intent = new Intent(current, nextActivity);
        current.startActivity(intent);
        current.finish();
    }

    // *** Others ***

    public static void runCallbackWithDelay(final Callback callback, int delayInMilli) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.call();
            }
        }, delayInMilli);
    }

    public static void runCallbackInBackground(final Callback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                callback.call();
                return null;
            }
        }.execute();
    }
}
