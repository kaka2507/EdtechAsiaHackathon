package act.muzikator.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    public void showProgressDialog(String text, boolean cancelable) {
        dismissProgressDialog();

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(cancelable);
        }
        mProgressDialog.setMessage(TextUtils.isEmpty(text) ? "Loading..." : text);

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
