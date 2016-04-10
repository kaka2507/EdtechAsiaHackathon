package act.muzikator.utils;

/**
 * Created by quangphuc789 on 10/4/16.
 */

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
    private AudioManager mAudioManager;
    private Context mContext;
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> mSoundPoolMap;

    @SuppressLint("UseSparseArrays")
    public void initSounds(Context context) {
        this.mContext = context;
        this.mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.mSoundPoolMap = new HashMap<Integer, Integer>();
        this.mAudioManager = ((AudioManager) this.mContext.getSystemService(Context.AUDIO_SERVICE));
    }

    public void addSoundResource(int resId) {
        Log.d("FFF", "ADDED IN");
        this.mSoundPoolMap.put(resId, this.mSoundPool.load(this.mContext, resId, 1));
    }

    public void playSoundResource(int resId) {
        Log.d("FFF", "PLAYED");
        int i = this.mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        this.mSoundPool.play(this.mSoundPoolMap.get(resId), i, i, 1, 0, 1.0F);
    }

    public void release() {
        mSoundPool.release();
        mSoundPoolMap.clear();
    }
}