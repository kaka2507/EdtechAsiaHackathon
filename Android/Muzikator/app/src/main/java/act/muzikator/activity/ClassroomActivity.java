package act.muzikator.activity;

import android.os.Bundle;
import android.widget.ToggleButton;

import act.muzikator.R;
import butterknife.Bind;

public class ClassroomActivity extends BaseActivity {
    @Bind(R.id.popularButton) ToggleButton popularButton;
    @Bind(R.id.historyButton) ToggleButton historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
    }

}
