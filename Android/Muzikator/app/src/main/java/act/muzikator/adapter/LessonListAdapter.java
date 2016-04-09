package act.muzikator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import act.muzikator.R;
import act.muzikator.model.PitchingLesson;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class LessonListAdapter extends ArrayAdapter<PitchingLesson> {
    private static class ViewHolder {
        public TextView tempo;
        public TextView viewCount;
        public TextView passedCount;
        public TextView failedCount;
    }

    private List<PitchingLesson> lessons;

    public LessonListAdapter(Context context, int resource) {
        super(context, resource);

        lessons = new ArrayList<>();
        createSampleData();
    }

    private void createSampleData() {
        lessons.add(new PitchingLesson(80, 100, 80, 8, 1));
        lessons.add(new PitchingLesson(85, 92, 79, 10, 1));
        lessons.add(new PitchingLesson(90, 85, 72, 11, 2));
        lessons.add(new PitchingLesson(95, 72, 60, 10, 2));
        lessons.add(new PitchingLesson(100, 50, 32, 15, 3));
        lessons.add(new PitchingLesson(105, 61, 45, 10, 3));
        lessons.add(new PitchingLesson(110, 60, 40, 13, 4));
        lessons.add(new PitchingLesson(115, 48, 38, 9, 4));
        lessons.add(new PitchingLesson(120, 40, 33, 5, 4));
        lessons.add(new PitchingLesson(125, 37, 27, 7, 5));
        lessons.add(new PitchingLesson(130, 32, 21, 8, 5));

        addAll(lessons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_pitch_lesson, parent, false);

            holder = new ViewHolder();
            holder.tempo = (TextView) convertView.findViewById(R.id.tempo);
            holder.viewCount = (TextView) convertView.findViewById(R.id.viewCount);
            holder.passedCount = (TextView) convertView.findViewById(R.id.passedCount);
            holder.failedCount = (TextView) convertView.findViewById(R.id.failedCount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PitchingLesson item = getItem(position);
        holder.tempo.setText(item.getTempo() + "");
        holder.viewCount.setText(item.getViewCount() + "");
        holder.passedCount.setText(item.getPassedCount() + "");
        holder.failedCount.setText(item.getFailedCount() + "");

        return convertView;
    }
}
