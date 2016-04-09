package act.muzikator.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import act.muzikator.R;
import butterknife.Bind;
import butterknife.OnClick;

public class ClassroomActivity extends BaseActivity {
    @Bind(R.id.popularButton)  Button     popularButton;
    @Bind(R.id.historyButton)  Button     historyButton;
    @Bind(R.id.popularWrapper) ScrollView popularWrapper;
    @Bind(R.id.historyWrapper) ScrollView historyWrapper;

    private boolean isPopularSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        showPopularTab();
    }

    @OnClick(R.id.popularButton)
    public void showPopularTab() {
        if (!isPopularSelected) {
            isPopularSelected = true;

            popularWrapper.setVisibility(View.VISIBLE);
            popularButton.setBackgroundResource(R.drawable.ic_class_tab_popular_selected);

            historyWrapper.setVisibility(View.INVISIBLE);
            historyButton.setBackgroundResource(R.drawable.ic_class_tab_history);
        }
    }

    @OnClick(R.id.historyButton)
    public void showHistoryTab() {
        if (isPopularSelected) {
            isPopularSelected = false;

            popularWrapper.setVisibility(View.INVISIBLE);
            popularButton.setBackgroundResource(R.drawable.ic_class_tab_popular);

            historyWrapper.setVisibility(View.VISIBLE);
            historyButton.setBackgroundResource(R.drawable.ic_class_tab_history_selected);
        }
    }

    @OnClick(R.id.tutor1)
    public void tutor1Selected() {
        videoCallWithTutor();
    }

    @OnClick(R.id.tutor2)
    public void tutor2Selected() {
        videoCallWithTutor();
    }

    @OnClick(R.id.tutor3)
    public void tutor3Selected() {
        videoCallWithTutor();
    }

    @OnClick(R.id.tutor4)
    public void tutor4Selected() {
        videoCallWithTutor();
    }

    private void videoCallWithTutor() {

    }
}
