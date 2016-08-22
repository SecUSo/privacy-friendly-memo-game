package org.secuso.privacyfriendlymemory.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.secuso.privacyfriendlymemory.model.Memory;
import org.secuso.privacyfriendlymemory.model.MemoryCard;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;

import java.io.IOException;
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

    static {
        columnCountMapping.put(MemoryDifficulty.Easy, 4);
        columnCountMapping.put(MemoryDifficulty.Moderate, 6);
        columnCountMapping.put(MemoryDifficulty.Hard, 8);
    }

    public MemoryLayoutProvider(Context context, Memory memory) {
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
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int cardSize = (displayWidth - getMarginLeft() - getMarginRight()) / getColumnCount();
        return cardSize;
    }

    public int getMargin() {
        int displayHeight = context.getResources().getDisplayMetrics().heightPixels;
        int cardsHeight = getColumnCount() * getCardSizePixel();
        int heightLeft = displayHeight - cardsHeight;
        return heightLeft / 2;
    }


    public int getMarginLeft() {
        return MARGIN_LEFT;
    }

    public int getMarginRight() {
        return MARGIN_RIGHT;
    }

    public boolean isCustomDeck() {
        return memory.isCustomDesign();
    }

    public Map<Integer, Bitmap> getCachedDeck(){
        Map<Integer, Bitmap> cachedDeck = new HashMap<>();
        // generate mapping only if it is a custom deck
        if(memory.isCustomDesign()){
            for(Map.Entry<Integer, MemoryCard> entry : memory.getDeck().entrySet()){
                cachedDeck.put(entry.getKey(), decodeUri(entry.getValue().getImageUri(), getCardSizePixel()));
            }
        }
        return cachedDeck;
    }


    private Bitmap decodeUri(Uri uri, final int requiredSize) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, o);
        } catch (IOException e) {
        }
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, o2);
        } catch (IOException e) {
        }
        return null;
    }
}
