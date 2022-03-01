package org.secuso.privacyfriendlymemory.model;

import android.net.Uri;
import android.util.Log;

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
    private static Map<Integer, MemoGameCard> deck   = new HashMap<>();

    public MemoGameDeck(List<Integer> imagesResIds){
        // generate for each image two cards with the same matching id
        int matchingId = 1;
        List<MemoGameCard> cards = new LinkedList<>();
        for(Integer imageResId : imagesResIds){
            for(int i = 0; i<=1;i++) {
                MemoGameCard card = new MemoGameCard(matchingId, imageResId, null);
                cards.add(card);
            }
            Log.d("DEBUG", "MATCHING_ID = " + String.valueOf(matchingId));
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

    public static boolean isMatchCardFlipped(MemoGameCard currentCard, int position){
        for(int i = 0 ; i< deck.size(); i++){
            // Find the card that matches the current card , and verifie if it's already flipped
            if(deck.get(i).getMatchingId() == currentCard.getMatchingId() && i != position){
                return deck.get(i).isAlreadyFlipped();
            }
        }
        // Never returned
        return false;
    }



}
