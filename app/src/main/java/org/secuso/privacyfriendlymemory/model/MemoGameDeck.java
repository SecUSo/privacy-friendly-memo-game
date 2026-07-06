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
package org.secuso.privacyfriendlymemory.model;

import android.net.Uri;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoGameDeck {

    // mapping between position(k), card(v)
    private Map<Integer, MemoGameCard> deck   = new HashMap<>();

    public MemoGameDeck(List<Integer> imagesResIds){
        // generate for each image two cards with the same matching id
        int matchingId = 1;
        List<MemoGameCard> cards = new LinkedList<>();
        for(Integer imageResId : imagesResIds){
            for(int i = 0; i<=1;i++) {
                MemoGameCard card = new MemoGameCard(matchingId, imageResId, null);
                cards.add(card);
            }
            matchingId++;
        }

        // generate random position for cards and map between position and card
        Collections.shuffle(cards);

        Integer position = 0;
        for(MemoGameCard card : cards){
            deck.put(position, card);
            position++;
        }
    }

    public MemoGameDeck(Set<Uri> imageUris){
        // generate for each image two cards with the same matching id
        int matchingId = 1;
        List<MemoGameCard> cards = new LinkedList<>();
        for(Uri imageUri : imageUris){
            for(int i = 0; i<=1;i++) {
                MemoGameCard card = new MemoGameCard(matchingId, 0, imageUri);
                cards.add(card);
            }
            matchingId++;
        }

        // generate random position for cards and map between position and card
        Collections.shuffle(cards);

        Integer position = 0;
        for(MemoGameCard card : cards){
            deck.put(position, card);
            position++;
        }
    }
    
    public Map<Integer, MemoGameCard> getDeck(){
        return deck;
    }


}
