package act.muzikator.ktv;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

/**
 * Created by vcoder on 4/9/16.
 */
public class Mp3Player extends Thread {
    public enum Event {
        EOF,
        REACH_LIMIT,
        BAD_FORMAT
    }

    private final static String TAG = "Mp3Player";
    private Decoder decoder;
    private AudioTrack audioTrack;
    private InputStream inputStream;
    private int id;
    private Mp3PlayerListener listener;

    public Mp3Player(int id, InputStream inputStream) {
        super();
        this.id = id;
        this.inputStream = inputStream;
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

    public void SetListener(Mp3PlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public synchronized void start() {
        super.start();
        audioTrack.play();
        listener.onPlay(id);
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
            while(true) {
                header = bitstream.readFrame();
                if (header == null) {
                    event = Event.BAD_FORMAT;
                    break;
                }
                SampleBuffer sampleBuffer = (SampleBuffer) decoder.decodeFrame(header, bitstream);
                short[] buffer = sampleBuffer.getBuffer();
                audioTrack.write(buffer, 0, buffer.length);
                framesReaded--;
                if(framesReaded == 0) {
                    event = Event.EOF;
                    break;
                }
                bitstream.closeFrame();
            }
        } catch (Exception e) {
            event = Event.BAD_FORMAT;
            e.printStackTrace();
        }
        listener.onStop(id, event);
    }
}
