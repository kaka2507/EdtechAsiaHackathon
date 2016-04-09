package act.muzikator.utils;

import java.util.HashMap;
import java.util.Map;

import act.muzikator.R;
import act.muzikator.model.PitchingNote;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class PitchingUtils {
    private static String[] noteNames         = new String[]{
        "C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5", "E5", "F5", "G5"
    };
    private static int[]    imageResIds       = new int[]{
        R.drawable.c4, R.drawable.d4, R.drawable.e4, R.drawable.f4, R.drawable.g4, R.drawable.a4, R.drawable.b4,
        R.drawable.c5, R.drawable.d5, R.drawable.e5, R.drawable.f5, R.drawable.g5
    };
    private static float[]  pitches           = new float[]{
        261.63f, 293.66f, 329.63f, 349.23f, 392.00f, 440.00f, 493.88f,
        523.25f, 587.33f, 659.25f, 698.46f, 783.99f
    };
    private static float[]  rightBelowPitches = new float[]{
        246.94f, 261.63f, 293.66f, 329.63f, 349.23f, 392.00f, 440.00f,
        493.88f, 523.25f, 587.33f, 659.25f, 698.46f
    };
    private static float[]  rightAbovePitches = new float[]{
        293.66f, 329.63f, 349.23f, 392.00f, 440.00f, 493.88f, 523.25f,
        587.33f, 659.25f, 698.46f, 783.99f, 880.00f
    };
    private static Map<String, PitchingNote> noteMap = new HashMap<>();

    public static void prepare() {
        for (int i = 0; i < noteNames.length; i++) {
            noteMap.put(noteNames[i], new PitchingNote(noteNames[i], imageResIds[i], pitches[i], rightBelowPitches[i], rightAbovePitches[i]));
        }
    }

    public static Map<String, PitchingNote> getNoteMap() {
        return noteMap;
    }
}
