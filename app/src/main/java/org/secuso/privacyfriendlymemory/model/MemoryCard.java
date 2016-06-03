package org.secuso.privacyfriendlymemory.model;

import android.support.annotation.DrawableRes;

/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoryCard {

    private final int matchingId;
    private final int resImageID;

    public MemoryCard(int matchingId, @DrawableRes int resImageID){
        this.matchingId = matchingId;
        this.resImageID = resImageID;
    }


    public int getMatchingId() {
        return matchingId;
    }


    public int getResImageID() {
        return resImageID;
    }
}
