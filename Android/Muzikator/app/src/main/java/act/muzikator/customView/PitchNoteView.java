package act.muzikator.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import act.muzikator.R;
import act.muzikator.model.PitchingNote;

/**
 * Created by huy.pham@robusttechhouse.com on 4/10/16.
 */
public class PitchNoteView extends LinearLayout {
    private PitchingNote note;

    public PitchNoteView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.customview_pitch_note_view, this, true);
    }

    public PitchNoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.customview_pitch_note_view, this, true);
    }

    public void setNote(PitchingNote note) {
        this.note = note;
        ((ImageView) findViewById(R.id.note)).setImageResource(note.getImageResId());
    }
}
