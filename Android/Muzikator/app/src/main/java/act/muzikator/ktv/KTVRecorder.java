package act.muzikator.ktv;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import act.muzikator.utils.KTVUtils;

/**
 * Created by vcoder on 4/9/16.
 */
public class KTVRecorder implements Player.PlayerListener {
    private final static String TAG = "KTVRecorder";
    private final static int BACKGROUND_ID = 1;
    private final static int RECORD_ID = 2;
    private MediaRecorder recorder;
    private int resId;
    private Context context;
    private Mp3Player backgroundTrack;
    private Mp3Player recordedTrack;
    public KTVRecorder(int resId, Context context) {
        this.resId = resId;
        this.context = context;
    }

    public void StartRecording() throws IOException {
        Log.d(TAG, "FilePath:" + KTVUtils.GetRecordFilePath(context, resId));
        // prepare to record
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(KTVUtils.GetRecordFilePath(context, resId));
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.prepare();

        // prepare to play background music
        backgroundTrack = new Mp3Player(BACKGROUND_ID, context.getResources().openRawResource(resId));
        backgroundTrack.SetListener(this);

        backgroundTrack.start();
        recorder.start();
    }

    public void PauseRecording() {
        backgroundTrack.Pause();
    }

    public void StopRecording() {
        recorder.stop();
        recorder.release();
        backgroundTrack.Stop();
        backgroundTrack.Release();
    }

    public void StartPlaying() {
        try {
            // prepare to play background music
            backgroundTrack = new Mp3Player(BACKGROUND_ID, context.getResources().openRawResource(resId));
            backgroundTrack.SetListener(this);

            // prepare to play record music
            recordedTrack = new Mp3Player(RECORD_ID, new FileInputStream(KTVUtils.GetRecordFilePath(context, resId)));
            recordedTrack.SetListener(this);

            // play
//            backgroundTrack.start();
            recordedTrack.start();
        } catch (IOException e) {
            Log.e(TAG, "file is not existed");
            e.printStackTrace();
        }
    }

    public void StopPlaying() {
//        backgroundTrack.Stop();
        backgroundTrack.Release();
        recordedTrack.Stop();
        recordedTrack.Release();
    }

    @Override
    public void onPlay(int id) {

    }

    @Override
    public void onStop(int id, Player.Event event) {

    }

    @Override
    public void onError(int id, Player.Event event) {
        Log.d(TAG, "onError: id = " + id + " event:" + event);
    }
}
