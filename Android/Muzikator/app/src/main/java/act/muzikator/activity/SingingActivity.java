package act.muzikator.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

import act.muzikator.R;
import act.muzikator.ktv.KTVRecorder;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by vcoder on 4/9/16.
 */
public class SingingActivity extends BaseActivity {
    private final static String TAG = "SingingActivity";

    public final static String MODE = "mode";
    public final static int RECORD_MODE = 1;
    public final static int PLAY_MODE = 2;

    private KTVRecorder ktvRecorder;
    private int mode;
    @Bind(R.id.button_singing_play) ImageButton playButton;
    @Bind(R.id.button_singing_stop) ImageButton stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singing);
        ktvRecorder = new KTVRecorder(R.raw.song, this);

        // get mode
        final Intent intent = getIntent();
        mode = intent.getIntExtra(MODE, RECORD_MODE);
    }

    @OnClick(R.id.button_singing_play)
    public void StartRecording() {
        if(mode == RECORD_MODE) {
            Log.d(TAG, "StartRecording");
            try {
                ktvRecorder.StartRecording();
                playButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                Log.d(TAG, "ktv recorder StartRecording fail");
                e.printStackTrace();
            }
        } else {
            ktvRecorder.StartPlaying();
            playButton.setVisibility(View.INVISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.button_singing_stop)
    public void StopRecording() {
        if(mode == RECORD_MODE) {
            Log.d(TAG, "StopRecording");
            ktvRecorder.StopRecording();
            stopButton.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.VISIBLE);
        } else {
            ktvRecorder.StopPlaying();
            stopButton.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.VISIBLE);
        }
    }
}
