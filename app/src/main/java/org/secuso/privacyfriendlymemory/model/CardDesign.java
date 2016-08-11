package org.secuso.privacyfriendlymemory.model;

/**
 * Created by Hannes on 20.05.2016.
 */
public enum CardDesign {

    FIRST(1),
    SECOND(2),
    CUSTOM(3);

    private final int value;

    CardDesign(int value){
        this.value = value;
    }

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
