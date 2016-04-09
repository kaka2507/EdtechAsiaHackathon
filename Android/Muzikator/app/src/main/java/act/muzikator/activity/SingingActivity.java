package act.muzikator.activity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import act.muzikator.R;
import act.muzikator.ktv.KTVRecorder;
import butterknife.OnClick;

/**
 * Created by vcoder on 4/9/16.
 */
public class SingingActivity extends BaseActivity {
    private final static String TAG = "SingingActivity";
    private KTVRecorder ktvRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singing);
        ktvRecorder = new KTVRecorder(R.raw.song, this);
    }

    @OnClick(R.id.button_singing_play)
    public void StartRecording() {
        Log.d(TAG, "StartRecording");
        try{
            ktvRecorder.StartRecording();
        } catch (IOException e) {
            Log.d(TAG, "ktv recorder StartRecording fail");
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_singing_pause)
    public void StopRecording() {
        Log.d(TAG, "StopRecording");
    }
}
