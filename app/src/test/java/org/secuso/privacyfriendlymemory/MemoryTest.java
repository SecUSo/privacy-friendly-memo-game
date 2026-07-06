/*
    This file is part of Privacy Friendly Memo Game.

    Privacy Friendly Memo Game is free software: you can redistribute it
    and/or modify it under the terms of the GNU General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package org.secuso.privacyfriendlymemory;

import org.junit.Before;
import org.junit.Test;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoGame;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;
import org.secuso.privacyfriendlymemory.model.MemoGameMode;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Hannes on 29.05.2016.
 */
public class MemoryTest {

    private MemoGame memory;

    @Before
    public void setUp(){
        this.memory = new MemoGame(CardDesign.FIRST, MemoGameMode.ONE_PLAYER, MemoGameDifficulty.Easy);
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
