package act.muzikator.audio;

/**
 * Created by vcoder on 4/9/16.
 */
public interface Mp3PlayerListener {
    public void onPlay(int id);
    public void onStop(int id, Mp3Player.Event event);
}
