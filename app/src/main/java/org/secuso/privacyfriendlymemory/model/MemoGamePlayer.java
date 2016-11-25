package org.secuso.privacyfriendlymemory.model;

import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoGamePlayer {

    private final int namePrefixResID = R.string.player_name_prefix;

    private final String nameSuffix;
    private int tries = 0;

    private List<MemoGameCard> foundCards = new LinkedList<>();

    public MemoGamePlayer(String nameSuffix){
        this.nameSuffix = nameSuffix;
    }

    public boolean addFoundCard(MemoGameCard card){
        return foundCards.add(card);
    }

    public int getFoundCardsCount(){
        if(foundCards.isEmpty()){
            return 0;
        }
        return foundCards.size()/2;
    }

    public String getNameSuffix(){
        return nameSuffix;
    }


    public void incrementTries(){
        tries++;
    }

    public int getTries(){
        return tries;
    }

}
