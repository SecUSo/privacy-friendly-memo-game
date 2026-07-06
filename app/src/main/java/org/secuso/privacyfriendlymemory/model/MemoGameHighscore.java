/*
    This file is part of Privacy Friendly Memo Game.

    Privacy Friendly Memo Game is free software: you can redistribute it
    and/or modify it under the terms of the GNU General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
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
