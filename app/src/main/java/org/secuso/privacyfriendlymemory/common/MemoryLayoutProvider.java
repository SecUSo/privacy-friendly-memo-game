package org.secuso.privacyfriendlymemory.common;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import org.secuso.privacyfriendlymemory.model.Memory;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoryLayoutProvider {

    private final static int MARGIN_LEFT = 35;  // in pixel
    private final static int MARGIN_RIGHT = 35; // in pixel
    private final Context context;
    private final Memory memory;
    private final MemoryDifficulty memoryDifficulty;
    private static Map<MemoryDifficulty, Integer> columnCountMapping = new HashMap<>();

    static{
        columnCountMapping.put(MemoryDifficulty.Easy, 4);
        columnCountMapping.put(MemoryDifficulty.Moderate, 6);
        columnCountMapping.put(MemoryDifficulty.Hard, 8);
    }

    public MemoryLayoutProvider(Context context, Memory memory){
        this.context = context;
        this.memory = memory;
        this.memoryDifficulty = memory.getDifficulty();
    }

    public int getColumnCount(){
        return columnCountMapping.get(memoryDifficulty);
    }

    public int getDeckSize(){
        return memoryDifficulty.getDeckSize();
    }

    public int getImageResID(int position){
        return memory.getImageResID(position);
    }

    public Uri getImageUri(int position){
        return memory.getImageUri(position);
    }

    public int getCardSizePixel(){
        // calculate the card size in pixel based on the screen width
        // [remember: card = square, so width=height]
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int cardSize = (displayWidth-getMarginLeft()-getMarginRight())/getColumnCount();
        return cardSize;
    }

    public int getMargin(){
        int displayHeight = context.getResources().getDisplayMetrics().heightPixels;
        int cardsHeight = getColumnCount()*getCardSizePixel();
        int heightLeft = displayHeight-cardsHeight;
        return heightLeft/2;
    }


    public int getMarginLeft(){
        return MARGIN_LEFT;
    }

    public int getMarginRight(){
        return MARGIN_RIGHT;
    }

    public boolean isCustomDeck(){ return memory.isCustomDesign(); }

}
