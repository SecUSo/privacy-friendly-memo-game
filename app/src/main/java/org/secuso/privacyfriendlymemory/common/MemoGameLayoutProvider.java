package org.secuso.privacyfriendlymemory.common;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;

import org.secuso.privacyfriendlymemory.model.MemoGame;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoGameLayoutProvider {

    private final static int MARGIN_LEFT = 35;               // in pixel
    private final static int MARGIN_RIGHT = 35;            // in pixel
    private final Context context;
    private final MemoGame memory;
    private final MemoGameDifficulty memoryDifficulty;
    private static Map<MemoGameDifficulty, Integer> columnCountMapping = new HashMap<>();

    static {
        columnCountMapping.put(MemoGameDifficulty.Easy, 4);
        columnCountMapping.put(MemoGameDifficulty.Moderate, 6);
        columnCountMapping.put(MemoGameDifficulty.Hard, 8);
    }

    public MemoGameLayoutProvider(Context context, MemoGame memory) {
        this.context = context;
        this.memory = memory;
        this.memoryDifficulty = memory.getDifficulty();
    }

    public int getColumnCount() {
        return columnCountMapping.get(memoryDifficulty);
    }

    public int getDeckSize() {
        return memoryDifficulty.getDeckSize();
    }

    public int getImageResID(int position) {
        return memory.getImageResID(position);
    }

    public Uri getImageUri(int position) {
        return memory.getImageUri(position);
    }

    public int getCardSizePixel() {
        // calculate the card size in pixel based on the screen width
        // [remember: card = square, so width=height]
        int orientation=context.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
            int cardSize = (displayWidth - getMarginLeft() - getMarginRight()) / getColumnCount();
            return cardSize;
        }
        else{
            int displayHeight = context.getResources().getDisplayMetrics().heightPixels;
            int displayHeightWithoutStats = (int)(displayHeight*(47.5f/100.f));
            int cardSize = displayHeightWithoutStats/getColumnCount();
            return cardSize;
        }
    }

    public int getMargin() {
        int displayHeight = context.getResources().getDisplayMetrics().heightPixels;
        int cardsHeight = getColumnCount() * getCardSizePixel();
        int heightLeft = displayHeight - cardsHeight;
        return heightLeft / 2;
    }


    public int getMarginLeft() {
        int orientation=context.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            return MARGIN_LEFT;
        }else{
            return calculateLandscapeSideMargin();
        }
    }

    public int getMarginRight() {
        int orientation=context.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            return MARGIN_RIGHT;
        }else{
            return calculateLandscapeSideMargin();
        }
    }

    private int calculateLandscapeSideMargin(){
        int cardSpaceWidth = getColumnCount()*getCardSizePixel();
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int spaceLeft = displayWidth-cardSpaceWidth;
        return spaceLeft/2;
    }

    public boolean isCustomDeck() {
        return memory.isCustomDesign();
    }

}
