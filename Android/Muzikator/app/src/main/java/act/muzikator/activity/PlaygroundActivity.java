package act.muzikator.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import act.muzikator.R;
import butterknife.Bind;
import butterknife.OnClick;

public class PlaygroundActivity extends BaseActivity {
    @Bind(R.id.songsButton)       Button     songsButton;
    @Bind(R.id.collectionButton)  Button     collectionButton;
    @Bind(R.id.songWrapper)       ScrollView songWrapper;
    @Bind(R.id.collectionWrapper) ScrollView collectionWrapper;

    private boolean isSongsSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        showSongTab();
    }

    @OnClick(R.id.songsButton)
    public void showSongTab() {
        if (!isSongsSelected) {
            isSongsSelected = true;

            songWrapper.setVisibility(View.VISIBLE);
            songsButton.setBackgroundResource(R.drawable.ic_song_bt_selected);

            collectionWrapper.setVisibility(View.INVISIBLE);
            collectionButton.setBackgroundResource(R.drawable.ic_collection_bt);
        }
    }

    @OnClick(R.id.collectionButton)
    public void showCollectionTab() {
        if (isSongsSelected) {
            isSongsSelected = false;

            songWrapper.setVisibility(View.INVISIBLE);
            songsButton.setBackgroundResource(R.drawable.ic_song_bt);

            collectionWrapper.setVisibility(View.VISIBLE);
            collectionButton.setBackgroundResource(R.drawable.ic_collection_bt_selected);
        }
    }

    @OnClick(R.id.song1)
    public void song1Selected() {
        startSinging();
    }

    @OnClick(R.id.song2)
    public void song2Selected() {
        startSinging();
    }

    @OnClick(R.id.song3)
    public void song3Selected() {
        startSinging();
    }

    @OnClick(R.id.song4)
    public void song4Selected() {
        startSinging();
    }

    @OnClick(R.id.song5)
    public void song5Selected() {
        startSinging();
    }

    private void startSinging() {

    }
}
