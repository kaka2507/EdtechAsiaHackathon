package act.muzikator.activity;

import android.os.Bundle;

import act.muzikator.R;
import act.muzikator.utils.AppUtils;
import butterknife.OnClick;

public class SelfLearningActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_learning);
    }

    @OnClick(R.id.pitchLesson1)
    public void viewLesson1() {
        AppUtils.showActivity(this, PitchingActivity.class);
    }

    @OnClick(R.id.pitchLesson2)
    public void viewLesson2() {
        AppUtils.showActivity(this, PitchingActivity.class);
    }

    @OnClick(R.id.pitchLesson3)
    public void viewLesson3() {
        AppUtils.showActivity(this, PitchingActivity.class);
    }

    @OnClick(R.id.pitchLesson4)
    public void viewLesson4() {
        AppUtils.showActivity(this, PitchingActivity.class);
    }
}
