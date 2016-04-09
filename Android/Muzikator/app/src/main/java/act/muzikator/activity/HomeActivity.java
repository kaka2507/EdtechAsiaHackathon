package act.muzikator.activity;

import android.os.Bundle;

import act.muzikator.R;
import act.muzikator.utils.AppUtils;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
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
