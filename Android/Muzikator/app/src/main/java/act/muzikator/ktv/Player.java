package act.muzikator.ktv;

import java.io.InputStream;

/**
 * Created by vcoder on 4/10/16.
 */
public abstract class Player extends Thread {
    protected PlayerListener listener;
    protected int id;
    protected InputStream inputStream;
    public Player(int id, InputStream inputStream) {
        super();
        this.id = id;
        this.inputStream = inputStream;
    }

    public void SetListener(PlayerListener listener) {
        this.listener = listener;
    }

    public abstract void Pause();
    public abstract void Stop();
    public abstract void Release();

    public enum Event {
        EOF,
        REACH_LIMIT,
        BAD_FORMAT,
        FAILED_INIT,
        FAILED_DECODE,
    }

    public interface PlayerListener {
        public void onPlay(int id);
        public void onStop(int id, Event event);
        public void onError(int id, Event event);
    }
}
