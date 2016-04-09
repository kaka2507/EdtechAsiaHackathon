package act.muzikator.ktv.audio;

/**
 * Created by vcoder on 4/9/16.
 */
public class AudioPacket {public int len;
    public int seq;
    public int idx;
    public int sampleRate;
    public int channelCount;
    public byte[] buf = null;

    public AudioPacket() {
        len = 0;
        seq = 0;
        idx = 0;
        sampleRate = 0;
        channelCount = 0;
    }
}
