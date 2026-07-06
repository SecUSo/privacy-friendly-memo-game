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