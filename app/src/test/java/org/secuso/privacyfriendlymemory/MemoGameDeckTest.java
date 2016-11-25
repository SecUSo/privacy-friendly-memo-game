package org.secuso.privacyfriendlymemory;

import org.junit.Before;
import org.junit.Test;
import org.secuso.privacyfriendlymemory.model.MemoGameCard;
import org.secuso.privacyfriendlymemory.model.MemoGameDeck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * Created by Hannes on 21.05.2016.
 */
public class MemoGameDeckTest {

    private Map<Integer, MemoGameCard> cardMapping        = new HashMap<>();
    private final static List<Integer> IMAGE_RES_IDS    = new LinkedList<>();

    static{
        IMAGE_RES_IDS.add(1);
        IMAGE_RES_IDS.add(2);
        IMAGE_RES_IDS.add(3);
    }

    @Before
    public void setupMapping(){
        MemoGameDeck deck = new MemoGameDeck(IMAGE_RES_IDS);
        cardMapping = deck.getDeck();
    }

    @Test
    public void testCardMapping(){
        assertEquals("Mapping should contain twice as much entries", IMAGE_RES_IDS.size()*2, cardMapping.size());
    }

}
