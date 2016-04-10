package act.muzikator.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import act.muzikator.R;
import act.muzikator.customView.PitchStaffView;
import act.muzikator.model.PitchingNote;
import act.muzikator.utils.PitchingUtils;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import butterknife.Bind;
import butterknife.OnClick;

public class PitchingActivity extends BaseActivity {
    private static final int SAMPLE_RATE     = 22050;
    private static final int BUFFER_SIZE     = 1024;
    private static final int OVERLAP         = 0;
    private static final int NOTE_DURATION   = 1000;
    private static final int PITCH_THREAHOLD = 500;

    @Bind(R.id.pitchingStaff) PitchStaffView pitchingStaff;

    private AudioDispatcher    dispatcher;
    private int                animRoundCount;
    private int                originalRoundCount;
    private long               lastMarkedTime;
    private int                lastNoteIndex;
    private int                accuTime;
    private List<PitchingNote> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitching);

//        startRecording();
        PitchingUtils.prepare();
        Map<String, PitchingNote> noteMap = PitchingUtils.getNoteMap();
        notes = new ArrayList<>();
        notes.add(noteMap.get("o0"));
        notes.add(noteMap.get("o0"));
        notes.add(noteMap.get("C4"));
        notes.add(noteMap.get("D4"));
        notes.add(noteMap.get("E4"));
        notes.add(noteMap.get("F4"));
        notes.add(noteMap.get("G4"));
        notes.add(noteMap.get("A4"));
        pitchingStaff.setNotes(notes);
        animRoundCount = notes.size() - 1;
        originalRoundCount = animRoundCount;

        pitchingStaff.getLayoutParams().width = notes.size() * getResources().getDimensionPixelSize(R.dimen.pitchNoteSide);
    }

    private void startRecording() {
        if (dispatcher != null) {
            dispatcher.stop();
        }
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, BUFFER_SIZE, OVERLAP);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentNoteIndex = originalRoundCount - animRoundCount - 1;
                        long currentMarkedTime = Calendar.getInstance().getTimeInMillis();

                        if (currentNoteIndex == lastNoteIndex || currentNoteIndex == -1) {
                            if (notes.get(lastNoteIndex).isFrequencyAtPitch(pitchInHz)) {
                                accuTime += (currentMarkedTime - lastMarkedTime);
                            }
                            lastMarkedTime = currentMarkedTime;
                        } else {
                            pitchingStaff.markNoteResult(lastNoteIndex, accuTime >= PITCH_THREAHOLD);
                            lastNoteIndex = currentNoteIndex;
                            accuTime = 0;

                            if (notes.get(currentNoteIndex).isFrequencyAtPitch(pitchInHz)) {
                                accuTime += (currentMarkedTime - lastMarkedTime);
                            }
                            lastMarkedTime = currentMarkedTime;
                        }
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, SAMPLE_RATE, BUFFER_SIZE, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher, "Audio Dispatcher").start();
    }

    private void stopRecording() {
        dispatcher.stop();
    }

//    @Override
//    protected void onDestroy() {
//        if (dispatcher != null) {
//            dispatcher.stop();
//        }
//
//        super.onDestroy();
//    }

    @OnClick(R.id.playButtton)
    public void playPitching() {
        final int noteWidth = getResources().getDimensionPixelSize(R.dimen.pitchNoteSide);
        startRecording();
        lastMarkedTime = Calendar.getInstance().getTimeInMillis();
        lastNoteIndex = 0;
        accuTime = 0;

        animateStaff(noteWidth);
    }

    private void animateStaff(final int noteWidth) {
        animRoundCount--;
        pitchingStaff.animate().setInterpolator(new LinearInterpolator()).translationX(-noteWidth * (originalRoundCount - animRoundCount)).setDuration(NOTE_DURATION).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animRoundCount >= 0) {
                    animateStaff(noteWidth);
                } else {
                    stopRecording();
                }
            }
        });
    }
}
