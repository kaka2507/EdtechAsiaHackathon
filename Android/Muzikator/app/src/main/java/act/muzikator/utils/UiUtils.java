package act.muzikator.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class UiUtils {

    // *** Show Popup ***

    public static void showBinaryPopup(Context context, String title, String msg, String okCaption, String cancelCaption, DialogInterface.OnClickListener okCallback) {
        showBinaryPopup(context, title, msg, okCaption, cancelCaption, okCallback, null);
    }

    public static void showBinaryPopup(Context context, String title, String msg, String okCaption, String cancelCaption, DialogInterface.OnClickListener okCallback, DialogInterface.OnClickListener cancelCallback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton(okCaption, okCallback)
            .setNegativeButton(cancelCaption, cancelCallback)
            .show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void showYesNoPopup(Context context, String title, String msg, DialogInterface.OnClickListener okCallback) {
        showBinaryPopup(context, title, msg, "Yes", "No", okCallback);
    }

    public static void showOkCancelPopup(Context context, String title, String msg, DialogInterface.OnClickListener okCallback) {
        showBinaryPopup(context, title, msg, "OK", "Cancel", okCallback);
    }

    public static void showAlertPopup(Context context, String title, String msg, DialogInterface.OnClickListener okCallback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("OK", okCallback)
            .show();
        dialog.setCanceledOnTouchOutside(false);
    }

    // *** Tag for logging, using class name ***

    public static String tagOf(Object object) {
        return object.getClass().getSimpleName();
    }

    // *** Other UI utils ***

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass())) {
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }
}
