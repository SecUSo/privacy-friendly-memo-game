package org.secuso.privacyfriendlymemory.model;

import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoGameCard {

    private final int matchingId;
    private final int resImageID;
    private final Uri imageUri;

    public MemoGameCard(int matchingId, @DrawableRes int resImageID, Uri imageUri){
        this.matchingId = matchingId;
        this.resImageID = resImageID;
        this.imageUri = imageUri;
    }


    public int getMatchingId() {
        return matchingId;
    }


    public int getResImageID() {
        return resImageID;
    }

    public Uri getImageUri(){ return imageUri; }
}
