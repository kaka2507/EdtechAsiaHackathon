package act.muzikator.model;

/**
 * Created by huy.pham@robusttechhouse.com on 4/9/16.
 */
public class PitchingLesson {
    private int tempo;
    private int viewCount;
    private int passedCount;
    private int failedCount;
    private int difficulty;

    public PitchingLesson(int tempo, int viewCount, int passedCount, int failedCount, int difficulty) {
        this.tempo = tempo;
        this.viewCount = viewCount;
        this.passedCount = passedCount;
        this.failedCount = failedCount;
        this.difficulty = difficulty;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
