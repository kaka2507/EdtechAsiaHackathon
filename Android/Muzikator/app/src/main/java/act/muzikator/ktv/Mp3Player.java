package act.muzikator.ktv;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.nfc.Tag;
import android.util.Log;

import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

/**
 * Created by vcoder on 4/9/16.
 */
public class Mp3Player extends Player {
    private final static String TAG = "Mp3Player";
    private Decoder decoder;
    private AudioTrack audioTrack;
    private boolean isPausing = false;
    private boolean isStoped = false;

    public Mp3Player(int id, InputStream inputStream) {
        super(id, inputStream);
        // Init Decoder
        decoder = new Decoder();

        // Init Audio Track to play video
        final int sampleRate = 44100;
        final int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        Log.d(TAG, "minBufferSize:" + minBufferSize);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize,
                AudioTrack.MODE_STREAM);
    }

    @Override
    public synchronized void start() {
        if(isPausing)
            isPausing = false;
        else {
            super.start();
            audioTrack.play();
            listener.onPlay(id);
        }
    }

    @Override
    public void run() {
        super.run();
        Event event = Event.EOF;
        try {
            Header header;
            final int READ_THRESHOLD = 2147483647;
            int framesReaded = READ_THRESHOLD;
            Bitstream bitstream = new Bitstream(inputStream);
            Log.d(TAG, "playing buffer 1: " + inputStream.available());
            while (!isStoped) {
                Log.d(TAG, "playing buffer 2");
                if(!isPausing) {
                    Log.d(TAG, "playing buffer 3");
                    header = bitstream.readFrame();
                    if (header == null) {
                        Log.d(TAG, "playing buffer 4");
                        event = Event.BAD_FORMAT;
                        break;
                    }
                    Log.d(TAG, "playing buffer 5");
                    SampleBuffer sampleBuffer = (SampleBuffer) decoder.decodeFrame(header, bitstream);
                    short[] buffer = sampleBuffer.getBuffer();
                    Log.d(TAG, "playing buffer:" + buffer.length);
                    audioTrack.write(buffer, 0, buffer.length);
                    framesReaded--;
                    if (framesReaded == 0) {
                        Log.d(TAG, "playing buffer 6");
                        event = Event.EOF;
                        break;
                    }
                    bitstream.closeFrame();
                }
            }
        } catch (Exception e) {
            event = Event.BAD_FORMAT;
            e.printStackTrace();
        }
        if(!isStoped)
            listener.onStop(id, event);
    }

    @Override
    public void Pause() {
        isPausing = true;
    }

    @Override
    public void Stop() {
        isStoped = true;
    }

    @Override
    public void Release() {
        audioTrack.stop();
        audioTrack.release();
    }
}
