package act.muzikator.ktv;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

import act.muzikator.utils.KTVUtils;

/**
 * Created by vcoder on 4/9/16.
 */
public class KTVRecorder {
    private final static String TAG = "KTVRecorder";
    private final static int BACKGROUND_ID = 1;
    private final static int RECORD_ID = 2;
    private MediaRecorder recorder;
    private int resId;
    private Context context;
    private Mp3Player backgroundTrack;
    public KTVRecorder(int resId, Context context) {
        this.resId = resId;
        this.context = context;
        backgroundTrack = new Mp3Player(BACKGROUND_ID, context.getResources().openRawResource(resId));
    }

    public void StartRecording() throws IOException {
        Log.d(TAG, "FilePath:" + KTVUtils.GetRecordFilePath(context, resId));
        // prepare to record
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(KTVUtils.GetRecordFilePath(context, resId));
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.prepare();

        backgroundTrack.start();
//        recorder.start();
    }

    public void StopRecording() {
        recorder.stop();
    }
}
