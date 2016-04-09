package act.muzikator.ktv;

import android.media.AudioFormat;
import android.media.AudioTrack;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import act.muzikator.ktv.audio.AACDecoder;
import act.muzikator.ktv.audio.AudioPacket;
import act.muzikator.utils.Debug;

/**
 * Created by vcoder on 4/10/16.
 */
public class AACPlayer extends Player implements AACDecoder.AACDecoderListener {
    private final static String TAG = "AACPlayer";
    private static final int MAX_BUFF_LEN = 1024;
    private AACDecoder decoder;
    private byte[] recorderBuffer = null;
    private int recorderBufferLength = 0;
    private AudioTrack audioTrack = null;
    private Timer timer;

    // task for decoder
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                doMyDecoderTask();
            } catch (Exception e) {
                Log.e("AudioPlayer", "ERROR: " + e.toString());
                e.printStackTrace();
            }
        }
    };

    private void doMyDecoderTask() throws Exception {
        long currentTs = System.currentTimeMillis();

        if (decoder.Execute() == AACDecoder.Status.TIMEOUT) {
            release();
            listener.onError(id, Event.FAILED_DECODE);
        }
    }

    private void release() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    public AACPlayer(int id, InputStream inputStream) {
        super(id, inputStream);
        decoder = new AACDecoder(this);
    }

    @Override
    public synchronized void start() {
        super.start();
        try {
            decoder.Init();
            timer = new Timer();
            timer.schedule(timerTask,  0, 20);
        } catch (Exception e)  {
            listener.onError(id, Event.FAILED_INIT);
        }
    }

    @Override
    public void run() {
        super.run();
        // try to read AAC file
        while(true) {
            try {
                int numByteRead = 0;
                if (recorderBufferLength != 0) {
                    Debug.log(this, "1 0 recorderBufferLength=" + recorderBufferLength);
                    // try to continue read audio data
                    if (recorderBufferLength < AACDecoder.HEADER_LENGTH) {
                        // adts header is still not completed
                        numByteRead = inputStream.read(recorderBuffer, recorderBufferLength, AACDecoder.HEADER_LENGTH - recorderBufferLength);
                        if(numByteRead == -1) {
                            Log.d(TAG, "EOF Reached");
                            listener.onStop(id, Event.EOF);
                            break;
                        }
                        recorderBufferLength += numByteRead;
                        Debug.log(this, "1 1 recorderBufferLength=" + recorderBufferLength);
                    }
                    if (recorderBufferLength >= AACDecoder.HEADER_LENGTH) {
                        // try to read aac adts header
                        int aacLen = AACDecoder.GetaAACFrameLength(recorderBuffer, 0, recorderBufferLength);
                        numByteRead = inputStream.read(recorderBuffer, recorderBufferLength, aacLen - recorderBufferLength);
                        if(numByteRead == -1) {
                            Log.d(TAG, "EOF Reached");
                            listener.onStop(id, Event.EOF);
                            break;
                        }
                        recorderBufferLength += numByteRead;
                        Debug.log(this, "1 2 recorderBufferLength=" + recorderBufferLength);
                        if (recorderBufferLength == aacLen) {
                            // have read completed aac packet
                            decoder.EnqueueToDecode(recorderBuffer, 0, recorderBufferLength);
                            recorderBufferLength = 0;
                        }
                    }
                } else {
                    // try to read adts header
                    numByteRead = inputStream.read(recorderBuffer, 0, AACDecoder.HEADER_LENGTH);
                    recorderBufferLength += numByteRead;
                    if (recorderBufferLength == AACDecoder.HEADER_LENGTH) {
                        // try to read aac adts header
                        int aacLen = AACDecoder.GetaAACFrameLength(recorderBuffer, 0, recorderBufferLength);
                        numByteRead = inputStream.read(recorderBuffer, recorderBufferLength, aacLen - recorderBufferLength);
                        if(numByteRead == -1) {
                            Log.d(TAG, "EOF Reached");
                            listener.onStop(id, Event.EOF);
                            break;
                        }
                        recorderBufferLength += numByteRead;
                        if (recorderBufferLength == aacLen) {
                            // have read completed aac packet
                            decoder.EnqueueToDecode(recorderBuffer, 0, recorderBufferLength);
                            recorderBufferLength = 0;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Receive exception when trying to read aac file");
                listener.onError(id, Event.BAD_FORMAT);
            }
        }
    }

    @Override
    public void onDecodeFrame(AudioPacket packet) {
        if(audioTrack == null) {
            // for init audio at the first received decoded frame
            int channelConfig = packet.channelCount == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO;
            int buffSize = AudioTrack.getMinBufferSize(packet.sampleRate, channelConfig, AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(android.media.AudioManager.STREAM_MUSIC, packet.sampleRate, channelConfig, AudioFormat.ENCODING_PCM_16BIT, buffSize, AudioTrack.MODE_STREAM);
            audioTrack.play();
        }
    }

    @Override
    public void Pause() {

    }

    @Override
    public void Stop() {

    }

    @Override
    public void Release() {

    }
}
