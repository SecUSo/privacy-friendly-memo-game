package org.secuso.privacyfriendlymemory.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum MemoGameMode {

    ONE_PLAYER(R.string.mode_single_player, R.drawable.ic_person_black_24px, 1),
    DUO_PLAYER(R.string.mode_duo_player, R.drawable.ic_people_black_24px, 2);

    private final int resIDString;
    private final int resIDImage;
    private final int playerCount;
    private static List<MemoGameMode> validTypes = new LinkedList<>();


    static{
        validTypes.add(ONE_PLAYER);
        validTypes.add(DUO_PLAYER);
    }


    MemoGameMode(@StringRes int resIDString, @DrawableRes int resIDImage, int playerCount){
        this.resIDString = resIDString;
        this.resIDImage = resIDImage;
        this.playerCount = playerCount;
    }

    public int getStringResID() {
        return resIDString;
    }

    public int getImageResID(){
        return resIDImage;
    }

    public int getPlayerCount(){ return playerCount; }

    public static List<MemoGameMode> getValidTypes(){
        return validTypes;
    }
}
