package org.secuso.privacyfriendlymemory;

import org.junit.Before;
import org.junit.Test;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoGameDefaultImages;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemoryImagesDefaultTest {

    private static final int     EXPECTED_SIZE_EASY        = MemoGameDifficulty.Easy.getDeckSize()/2;
    private static final int     EXPECTED_SIZE_MODERATE    = MemoGameDifficulty.Moderate.getDeckSize()/2;
    private static final int     EXPECTED_SIZE_HARD        = MemoGameDifficulty.Hard.getDeckSize()/2;

    private static List<Integer> IMAGES_FIRST_EASY         = new LinkedList<>();
    private static List<Integer> IMAGES_FIRST_MODERATE     = new LinkedList<>();
    private static List<Integer> IMAGES_FIRST_HARD         = new LinkedList<>();

    @Before
    public void setupImages(){
        IMAGES_FIRST_EASY       = MemoGameDefaultImages.getResIDs(CardDesign.FIRST, MemoGameDifficulty.Easy,false);
        IMAGES_FIRST_MODERATE   = MemoGameDefaultImages.getResIDs(CardDesign.FIRST, MemoGameDifficulty.Moderate, false);
        IMAGES_FIRST_HARD       = MemoGameDefaultImages.getResIDs(CardDesign.FIRST, MemoGameDifficulty.Hard, false);
    }

    @Test
    public void testFirstDeck(){
        assertEquals("List size not as expected", EXPECTED_SIZE_EASY, IMAGES_FIRST_EASY.size());
        assertEquals("List size not as expected", EXPECTED_SIZE_MODERATE, IMAGES_FIRST_MODERATE.size());
        assertEquals("List size not as expected", EXPECTED_SIZE_HARD, IMAGES_FIRST_HARD.size());
    }


}