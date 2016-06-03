package org.secuso.privacyfriendlymemory;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.Memory;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;
import org.secuso.privacyfriendlymemory.model.MemoryMode;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Hannes on 29.05.2016.
 */
public class MemoryTest {

    private Memory memory;

    @Before
    public void setUp(){
        this.memory = new Memory(CardDesign.FIRST, MemoryMode.ONE_PLAYER, MemoryDifficulty.Easy);
    }

    @Test
    public void testMemorySovlable(){
            for (int i = 0; i <= memory.getDifficulty().getDeckSize() - 1; i++) {
                for (int j = 1; j <= memory.getDifficulty().getDeckSize() - 1; j++) {
                    if(i != j) {
                        memory.select(i);
                        memory.select(j);
                    }
                }
        }
        assertTrue("Memory should be solved!", memory.isFinished());
    }
}
