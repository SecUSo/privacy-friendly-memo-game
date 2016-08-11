package org.secuso.privacyfriendlymemory.model;


import org.secuso.privacyfriendlymemory.ui.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoryImages {

    private static final int NOT_FOUND_IMAGE_RES_ID = R.drawable.secuso_not_found;

    private static List<Integer> images1            = new LinkedList<>();
    private static List<Integer> images2            = new LinkedList<>();

    static{
        // fill first deck with images
        images1.add(R.drawable.set1_card1);
        images1.add(R.drawable.set1_card2);
        images1.add(R.drawable.set1_card3);
        images1.add(R.drawable.set1_card4);
        images1.add(R.drawable.set1_card5);
        images1.add(R.drawable.set1_card6);
        images1.add(R.drawable.set1_card7);
        images1.add(R.drawable.set1_card8);
        images1.add(R.drawable.set1_card9);
        images1.add(R.drawable.set1_card10);
        images1.add(R.drawable.set1_card11);
        images1.add(R.drawable.set1_card12);
        images1.add(R.drawable.set1_card13);
        images1.add(R.drawable.set1_card14);
        images1.add(R.drawable.set1_card15);
        images1.add(R.drawable.set1_card16);
        images1.add(R.drawable.set1_card17);
        images1.add(R.drawable.set1_card18);
        images1.add(R.drawable.set1_card19);
        images1.add(R.drawable.set1_card20);
        images1.add(R.drawable.set1_card21);
        images1.add(R.drawable.set1_card22);
        images1.add(R.drawable.set1_card23);
        images1.add(R.drawable.set1_card24);
        images1.add(R.drawable.set1_card25);
        images1.add(R.drawable.set1_card26);
        images1.add(R.drawable.set1_card27);
        images1.add(R.drawable.set1_card28);
        images1.add(R.drawable.set1_card29);
        images1.add(R.drawable.set1_card30);
        images1.add(R.drawable.set1_card31);
        images1.add(R.drawable.set1_card32);
    }

    public static int getNotFoundImageResID(){
        return NOT_FOUND_IMAGE_RES_ID;
    }

    public static List<Integer> getResIDs(CardDesign cardDesign, MemoryDifficulty memoryDifficulty, boolean shuffled){
        List<Integer> imagesForCardDesign = new LinkedList<>();
        switch(cardDesign){
            case FIRST:
                imagesForCardDesign = images1;
                break;
            case SECOND:
                imagesForCardDesign = images2;
                break;
        }

        if(shuffled){
            Collections.shuffle(imagesForCardDesign);
        }
        return reduce(memoryDifficulty, imagesForCardDesign);
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
