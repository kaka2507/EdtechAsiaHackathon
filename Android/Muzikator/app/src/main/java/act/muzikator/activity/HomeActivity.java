package act.muzikator.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;

import java.util.Random;

import act.muzikator.R;
import act.muzikator.utils.AppUtils;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    private final static String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @OnClick(R.id.selfLearning)
    public void startSelfLearning() {
        AppUtils.showActivity(this, SelfLearningActivity.class);
    }

    @OnClick(R.id.classroom)
    public void startClassroom() {
        AppUtils.showActivity(this, ClassroomActivity.class);
    }

    @OnClick(R.id.playground)
    public void startPlayground() {
        AppUtils.showActivity(this, PlaygroundActivity.class);
    }

}
