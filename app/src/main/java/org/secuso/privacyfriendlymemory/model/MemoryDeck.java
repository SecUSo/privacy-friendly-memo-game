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
public class MemoryDeck {

    // mapping between position(k), card(v)
    private Map<Integer, MemoryCard> deck   = new HashMap<>();

    public MemoryDeck(List<Integer> imagesResIds){
        // generate for each image two cards with the same matching id
        int matchingId = 1;
        List<MemoryCard> cards = new LinkedList<>();
        for(Integer imageResId : imagesResIds){
            for(int i = 0; i<=1;i++) {
                MemoryCard card = new MemoryCard(matchingId, imageResId, null);
                cards.add(card);
            }
            matchingId++;
        }

        // generate random position for cards and map between position and card
        Collections.shuffle(cards);

        Integer position = 0;
        for(MemoryCard card : cards){
            deck.put(position, card);
            position++;
        }
    }

    public MemoryDeck(Set<Uri> imageUris){
        // generate for each image two cards with the same matching id
        int matchingId = 1;
        List<MemoryCard> cards = new LinkedList<>();
        for(Uri imageUri : imageUris){
            for(int i = 0; i<=1;i++) {
                MemoryCard card = new MemoryCard(matchingId, 0, imageUri);
                cards.add(card);
            }
            matchingId++;
        }

        // generate random position for cards and map between position and card
        Collections.shuffle(cards);

        Integer position = 0;
        for(MemoryCard card : cards){
            deck.put(position, card);
            position++;
        }
    }
    
    public Map<Integer, MemoryCard> getDeck(){
        return deck;
    }


}
