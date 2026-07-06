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
