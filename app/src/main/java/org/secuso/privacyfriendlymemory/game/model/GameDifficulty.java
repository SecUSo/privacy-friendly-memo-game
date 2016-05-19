package org.secuso.privacyfriendlymemory.game.model;

import android.support.annotation.StringRes;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum GameDifficulty {


    Easy(R.string.difficulty_easy),
    Moderate(R.string.difficulty_moderate),
    Hard(R.string.difficulty_hard);

    private int resID;
    private static List<GameDifficulty> validDifficulties = new LinkedList<>();

    static{
        validDifficulties.add(Easy);
        validDifficulties.add(Moderate);
        validDifficulties.add(Hard);
    }


    GameDifficulty(@StringRes int resID) {
        this.resID = resID;
    }

    public int getStringResID() {
        return resID;
    }

    public static List<GameDifficulty> getValidDifficulties(){
        return validDifficulties;
    }

}
