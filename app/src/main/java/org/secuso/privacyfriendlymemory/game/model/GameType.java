package org.secuso.privacyfriendlymemory.game.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum GameType {

    ONE_PLAYER(R.string.type_single_player, R.drawable.ic_person_black_24px),
    DUO_PLAYER(R.string.type_duo_player, R.drawable.ic_people_black_24px);

    private int resIDString;
    private int resIDImage;
    private static List<GameType> validTypes = new LinkedList<>();


    static{
        validTypes.add(ONE_PLAYER);
        validTypes.add(DUO_PLAYER);
    }


    GameType(@StringRes int resIDString,@DrawableRes int resIDImage){
        this.resIDString = resIDString;
        this.resIDImage = resIDImage;
    }

    public int getStringResID() {
        return resIDString;
    }

    public int getImageResID(){
        return resIDImage;
    }

    public static List<GameType> getValidTypes(){
        return validTypes;
    }
}
