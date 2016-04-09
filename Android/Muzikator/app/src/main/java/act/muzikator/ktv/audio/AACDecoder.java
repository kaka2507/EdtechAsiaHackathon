package act.muzikator.ktv.audio;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

import act.muzikator.utils.Debug;

/**
 * Created by vcoder on 4/9/16.
 */
public class AACDecoder {
    public static final int DEFAULT_SAMPLERATE = 24000;
    public static final int DEFAULT_CHANNELNUM = 1;
    public static final int DEFAULT_BITRATE = 32000;
    protected static final int DECODE_TIMEOUT = 10000; // 10 seconds
    protected static final int TIME_OUT = 1000;
    protected final AACDecoderListener listener;
    protected ConcurrentLinkedQueue<AudioPacket> audioCache;
    protected volatile Status status;
    protected long lastTSReceive;
    public static final int HEADER_LENGTH = 7;
    private int sampleRate;
    private int channelCount;
    private MediaCodec mediaCodec;
    private ByteBuffer[] inputBuffers;
    private ByteBuffer[] outputBuffers;
    private int numTime = 0;

    public AACDecoder(AACDecoderListener listener) {
        this.listener = listener;
        audioCache = new ConcurrentLinkedQueue<>();
        status = Status.NEW;
        lastTSReceive = 0;
    }

    public int Init() throws Exception {
        if (status != Status.NEW)
            return -1;

        mediaCodec = MediaCodec.createDecoderByType("audio/mp4a-latm");
        MediaFormat format = MediaFormat.createAudioFormat("audio/mp4a-latm", DEFAULT_SAMPLERATE, DEFAULT_CHANNELNUM);
        format.setInteger(MediaFormat.KEY_BIT_RATE, DEFAULT_BITRATE);

        int audioObjectType = 2;
        int sampleIndex = 6;
        int channelConfig = 1;
        ByteBuffer csd = ByteBuffer.allocate(2);
        csd.put((byte) ((audioObjectType << 3) | (sampleIndex >> 1)));
        csd.position(1);
        csd.put((byte) ((byte) ((sampleIndex << 7) & 0x80) | (channelConfig << 3)));
        csd.flip();
        format.setByteBuffer("csd-0", csd); // add csd-0
        for (int k = 0; k < csd.capacity(); ++k) {
            Debug.log(this, "csd : " + csd.array()[k]);
        }

        mediaCodec.configure(format, null, null, 0);
        mediaCodec.start();
        inputBuffers = mediaCodec.getInputBuffers();
        outputBuffers = mediaCodec.getOutputBuffers();

        lastTSReceive = System.currentTimeMillis();
        status = Status.READY;
        return 0;
    }

    public void Reset() throws Exception {
        if (status == Status.READY) {
            status = Status.NEW;
            audioCache.clear();
            mediaCodec.flush();
            mediaCodec.stop();
            mediaCodec.release();
        }
    }

    private boolean preDecode() {
        AudioPacket packet = audioCache.peek();
        if (packet == null) {
            return false;
        }
        int inputIdx = mediaCodec.dequeueInputBuffer(TIME_OUT);
        if (inputIdx < 0) {
            return false;
        }
        // available input buffer to decode
        ByteBuffer inputBuffer = inputBuffers[inputIdx];
        inputBuffer.clear();
        inputBuffer.put(packet.buf, 7, packet.len - 7);
        mediaCodec.queueInputBuffer(inputIdx, 0, packet.len - 7, -1, 0);
        audioCache.remove();
        return true;
    }

    private void posDecode() {
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputIdx = mediaCodec.dequeueOutputBuffer(bufferInfo, TIME_OUT);
        if (outputIdx >= 0) {
            AudioPacket packet = new AudioPacket();
            packet.len = bufferInfo.size;
            packet.buf = new byte[packet.len];
            packet.sampleRate = sampleRate;
            packet.channelCount = channelCount;
            ByteBuffer outputBuffer = outputBuffers[outputIdx];
            outputBuffer.get(packet.buf, 0, bufferInfo.size);
            outputBuffer.clear();
            listener.onDecodeFrame(packet);
            mediaCodec.releaseOutputBuffer(outputIdx, false);
        } else if (outputIdx == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
            Debug.log(this, "MediaCodec Output Format Changed");
            MediaFormat format = mediaCodec.getOutputFormat();
            sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
            channelCount = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        } else if (outputIdx == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
            Debug.log(this, "MediaCodec Buffer Change");
            inputBuffers = mediaCodec.getInputBuffers();
            outputBuffers = mediaCodec.getOutputBuffers();
            Debug.log(this, "MediaCodec inputbuffer length:" + inputBuffers.length);
            Debug.log(this, "MediaCodec outputbuffer length:" + outputBuffers.length);
        }
    }

    public Status Execute() throws Exception {
        if (System.currentTimeMillis() - lastTSReceive > DECODE_TIMEOUT) {
            return Status.TIMEOUT;
        }

        if (status != Status.READY) {
            return Status.NOTHING;
        }

        preDecode();
        posDecode();
        return Status.OK;
    }

    public int EnqueueToDecode(byte[] buf, int len, int seq) {
        if (status != Status.READY)
            return -1;

        AudioPacket packet = new AudioPacket();
        packet.idx = 0;
        packet.len = len;
        packet.seq = seq;
        packet.buf = buf;
        audioCache.add(packet);
        lastTSReceive = System.currentTimeMillis();
        return 0;
    }

    public static int GetaAACFrameLength(byte[] buff, int offset, int len) {
        /**
         * Get AAC Frame Length from adts header (without CRC check)
         *
         * @param buff      The buffer which is storage adts header and aac raw datas
         * @param offset    Offset of adts header
         * @param len       The length of buffer
         * @return The length of AAC frame
         */
        short highLen = buff[offset + 3];
        short midLen = buff[offset + 4];
        short lowLen = buff[offset + 5];

        return ((highLen & 0x03) << 11) | ((midLen & 0xFF) << 3) | ((lowLen & 0xFF) >> 5);
    }

    public interface AACDecoderListener {
        void onDecodeFrame(AudioPacket packet);
    }

    public enum Status {
        NEW, READY, OK, NOTHING, TIMEOUT
    }
}
