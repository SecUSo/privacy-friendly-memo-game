package org.secuso.privacyfriendlymemory.model;

import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoryPlayer {

    private final int namePrefixResID = R.string.player_name_prefix;

    private final String nameSuffix;
    private List<MemoryCard> foundCards = new LinkedList<>();

    public MemoryPlayer(String nameSuffix){
        this.nameSuffix = nameSuffix;
    }

    public boolean addFoundCard(MemoryCard card){
        return foundCards.add(card);
    }

    public int getFoundCardsCount(){
        return foundCards.size();
    }

    public String getNameSuffix(){
        return nameSuffix;
    }

    public int getNamePrefixResID(){
        return namePrefixResID;
    }

}
