package act.muzikator.activity;

import android.os.Bundle;

import act.muzikator.R;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @OnClick(R.id.selfLearning)
    public void startSelfLearning() {

    }

    @OnClick(R.id.classroom)
    public void startClassroom() {

    }

    @OnClick(R.id.playground)
    public void startPlayground() {

    }
}
