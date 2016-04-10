package act.muzikator.ktv;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import act.muzikator.utils.KTVUtils;

/**
 * Created by vcoder on 4/9/16.
 */
public class KTVRecorder implements Player.PlayerListener, MediaPlayer.OnPreparedListener {
    private final static String TAG = "KTVRecorder";
    private final static int BACKGROUND_ID = 1;
    private final static int RECORD_ID = 2;
    private MediaRecorder recorder;
    private int resId;
    private Context context;
    private Mp3Player backgroundTrack;
    private MediaPlayer recordedTrack;
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
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
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
            recordedTrack = new MediaPlayer();
            recordedTrack.setDataSource(KTVUtils.GetRecordFilePath(context, resId));
            recordedTrack.prepare();

            // play
            backgroundTrack.start();
            recordedTrack.start();
        } catch (IOException e) {
            Log.e(TAG, "file is not existed");
            e.printStackTrace();
        }
    }

    public void StopPlaying() {
        backgroundTrack.Stop();
        backgroundTrack.Release();
        recordedTrack.stop();
        recordedTrack.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
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
