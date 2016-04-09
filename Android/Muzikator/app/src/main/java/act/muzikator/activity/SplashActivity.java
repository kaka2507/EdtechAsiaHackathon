package act.muzikator.activity;

import android.os.Bundle;

import act.muzikator.R;
import act.muzikator.utils.AppUtils;
import act.muzikator.utils.Callback;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppUtils.runCallbackWithDelay(new Callback() {
            @Override
            public void call() {
                AppUtils.showActivityAndFinishCurrent(SplashActivity.this, HomeActivity.class);
            }
        }, 2000);
    }
}
