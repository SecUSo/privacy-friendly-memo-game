package org.secuso.privacyfriendlymemory.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hannes on 31.05.2016.
 */
public class MemoGameHighscore {

    private static Map<MemoGameDifficulty, Integer> baseScoreMapping = new HashMap<>();
    private final int baseScore;
    private final int time;
    private final int tries;
    private final boolean isValid;

    static {
        baseScoreMapping.put(MemoGameDifficulty.Easy, 3000);
        baseScoreMapping.put(MemoGameDifficulty.Moderate, 9000);
        baseScoreMapping.put(MemoGameDifficulty.Hard, 50000);
    }

    public MemoGameHighscore(MemoGameDifficulty difficulty, int time, int tries, boolean isValid) {
        this.baseScore = baseScoreMapping.get(difficulty);
        this.time = time;
        this.tries = tries;
        this.isValid = isValid;
    }

    public int getScore() {
        int calculatedScore = baseScore - (time * tries);
        // score should not be negative
        return calculatedScore < 0 ? 0 : calculatedScore;
    }

    public int getTries() {
        return tries;
    }

    public int getTime() {
        return time;
    }

    public boolean isValid() { return isValid; }

}
