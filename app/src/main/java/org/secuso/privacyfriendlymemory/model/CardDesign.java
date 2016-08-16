package org.secuso.privacyfriendlymemory.model;

import org.secuso.privacyfriendlymemory.ui.R;

/**
 * Created by Hannes on 20.05.2016.
 */
public enum CardDesign {

    FIRST(1, R.string.carddesign_displayname_first),
    SECOND(2, R.string.carddesign_displayname_second),
    CUSTOM(3, R.string.carddesign_displayname_custom);

    private final int value;
    private final int displayNameResId;

    CardDesign(int value, int displayNameResId){
        this.value = value;
        this.displayNameResId = displayNameResId;
    }

    public int getDisplayNameResId(){ return displayNameResId; }
    public int getValue(){
        return value;
    }

    public boolean isCustom(){
        if(value == 3) {
            return true;
        }else{
            return false;
        }
    }

    public static CardDesign get(int value){
        switch(value){
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            case 3:
                return CUSTOM;
            default:
                return FIRST;
        }
    }

}
