package act.muzikator.activity;

import android.os.Bundle;
import android.widget.ListView;

import act.muzikator.R;
import act.muzikator.adapter.LessonListAdapter;
import butterknife.Bind;

public class SelfLearningActivity extends BaseActivity {
    @Bind(R.id.lessonList) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_learning);

        listView.setAdapter(new LessonListAdapter(this, 0));
    }
}
