package act.muzikator.customView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import act.muzikator.R;
import act.muzikator.model.PitchingNote;

/**
 * Created by huy.pham@robusttechhouse.com on 4/10/16.
 */
public class PitchStaffView extends LinearLayout {
    private List<PitchingNote> notes;

    public PitchStaffView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setNotes(List<PitchingNote> notes) {
        this.notes = notes;
        int noteSideLength = getContext().getResources().getDimensionPixelSize(R.dimen.pitchNoteSide);
        for (PitchingNote note : notes) {
            PitchNoteView noteView = new PitchNoteView(getContext());
            noteView.setNote(note);
            addView(noteView, new LayoutParams(noteSideLength, noteSideLength));
        }
    }

    public void markNoteResult(int index, boolean isSuccess) {
        getChildAt(index).setBackgroundColor(isSuccess ? Color.GREEN : Color.RED);
    }
}
