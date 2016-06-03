package org.secuso.privacyfriendlymemory.model;

/**
 * Created by Hannes on 20.05.2016.
 */
public enum CardDesign {

    FIRST(1),
    SECOND(2);

    private final int value;

    CardDesign(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }


}
