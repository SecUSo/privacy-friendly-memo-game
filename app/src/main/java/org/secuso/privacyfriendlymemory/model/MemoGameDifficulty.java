package org.secuso.privacyfriendlymemory.model;

import android.support.annotation.StringRes;

import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum MemoGameDifficulty {


    Easy(R.string.difficulty_easy, 16),
    Moderate(R.string.difficulty_moderate, 36),
    Hard(R.string.difficulty_hard, 64);

    private final int resID;
    private final int deckSize;

    private static List<MemoGameDifficulty> validDifficulties = new LinkedList<>();

    static{
        validDifficulties.add(Easy);
        validDifficulties.add(Moderate);
        validDifficulties.add(Hard);
    }


    MemoGameDifficulty(@StringRes int resID, int deckSize) {
        this.resID = resID;
        this.deckSize = deckSize;
    }

    public int getStringResID() {
        return resID;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public static List<MemoGameDifficulty> getValidDifficulties(){
        return validDifficulties;
    }

}
