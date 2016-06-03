package org.secuso.privacyfriendlymemory.model;


import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoryImages {

    private static final int NOT_FOUND_IMAGE_RES_ID = R.drawable.not_found;

    private static List<Integer> images1            = new LinkedList<>();
    private static List<Integer> images2            = new LinkedList<>();

    static{
        // fill first deck with images
        images1.add(R.drawable.item_1);
        images1.add(R.drawable.item_2);
        images1.add(R.drawable.item_3);
        images1.add(R.drawable.item_4);
        images1.add(R.drawable.item_5);
        images1.add(R.drawable.item_6);
        images1.add(R.drawable.item_7);
        images1.add(R.drawable.item_8);
        images1.add(R.drawable.item_9);
        images1.add(R.drawable.item_10);
        images1.add(R.drawable.item_11);
        images1.add(R.drawable.item_12);
        images1.add(R.drawable.item_13);
        images1.add(R.drawable.item_14);
        images1.add(R.drawable.item_15);
        images1.add(R.drawable.item_16);
        images1.add(R.drawable.item_17);
        images1.add(R.drawable.item_18);
        images1.add(R.drawable.item_19);
        images1.add(R.drawable.item_20);
        images1.add(R.drawable.item_21);
        images1.add(R.drawable.item_22);
        images1.add(R.drawable.item_23);
        images1.add(R.drawable.item_24);
        images1.add(R.drawable.item_25);
        images1.add(R.drawable.item_26);
        images1.add(R.drawable.item_27);
        images1.add(R.drawable.item_28);
        images1.add(R.drawable.item_29);
        images1.add(R.drawable.item_30);
        images1.add(R.drawable.item_31);
        images1.add(R.drawable.item_32);
    }

    public static int getNotFoundImageResID(){
        return NOT_FOUND_IMAGE_RES_ID;
    }

    public static List<Integer> getResIDs(CardDesign cardDesign, MemoryDifficulty memoryDifficulty){
        switch(cardDesign){
            case FIRST:
                return reduce(memoryDifficulty, images1);
            case SECOND:
                return reduce(memoryDifficulty, images2);
            default:
                throw new IllegalStateException("Can not get card images for design " + cardDesign.getValue() + ".");
        }
    }

    private static List<Integer> reduce(MemoryDifficulty memoryDifficulty, List<Integer> images){
        // get images based on the deck size defined in the game difficulty
        int differentImages = memoryDifficulty.getDeckSize()/2;
        if(differentImages > images.size()) {
            throw new IllegalStateException("Requested deck contains not enough images for the specific game difficulty");
        }
        return images.subList(0, differentImages);
    }

}
