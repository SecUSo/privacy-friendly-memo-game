package org.secuso.privacyfriendlymemory.model;

import android.net.Uri;

import org.secuso.privacyfriendlymemory.common.MemoGamePlayerFactory;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoGame {

    private final MemoGameDifficulty memoryDifficulty;
    private final MemoGameMode memoryMode;
    private final CardDesign cardDesign;

    private final Map<Integer, MemoGameCard> deck;
    private final int notFoundImageResID;

    private Integer[] falseSelectedCards = new Integer[2];
    private boolean falseSelected = false;

    private MemoGamePlayer currentPlayer;
    private MemoGameCard selectedCard = null;
    private MemoGameTimer timer;

    private MemoGameCard[] temporarySelectedCards = new MemoGameCard[2];
    private List<MemoGameCard> foundCards = new LinkedList<>();
    private List<MemoGamePlayer> players = new LinkedList<>();

    private int selectedCardsCount = 0;


    public MemoGame(CardDesign cardDesign, MemoGameMode memoryMode, MemoGameDifficulty memoryDifficulty) {
        this.memoryDifficulty = memoryDifficulty;
        this.memoryMode = memoryMode;
        this.cardDesign = cardDesign;
        if(cardDesign == CardDesign.CUSTOM){
            this.deck = new MemoGameDeck(MemoGameCustomImages.getUris(memoryDifficulty)).getDeck();
        }else{
            this.deck = new MemoGameDeck(MemoGameDefaultImages.getResIDs(cardDesign, memoryDifficulty, true)).getDeck();
        }
        this.notFoundImageResID = MemoGameDefaultImages.getNotFoundImageResID();
        this.players = MemoGamePlayerFactory.createPlayers(memoryMode);
        this.currentPlayer = players.get(0);
        this.timer = new MemoGameTimer();
    }

    public void select(int position) {
        MemoGameCard cardAtPosition = deck.get(position);
        // do not count a selection on an already found or selected card
        if (isFound(cardAtPosition) || isSelected(cardAtPosition)) {
            return; //
        }
        switch (selectedCardsCount) {
            case 0:
                // set first selected card and clear temporary selected cards
                temporarySelectedCards[0] = null;
                temporarySelectedCards[1] = null;
                selectedCard = cardAtPosition;
                selectedCardsCount++;
                break;
            case 1:
                currentPlayer.incrementTries();
                // set founded if cards are matching; if not matching and more than 1 player is available, set next player
                if (selectedCard.getMatchingId() == cardAtPosition.getMatchingId()) {
                    setFound(selectedCard, cardAtPosition);
                    if(isFinished()){
                        stopTimer();
                    }
                }else if(players.size() > 1){
                    currentPlayer = getNextPlayer();
                }

                // if not match set cards to false selected
                if (selectedCard.getMatchingId() != cardAtPosition.getMatchingId()) {
                    falseSelectedCards[0] = selectedCard.getResImageID();
                    falseSelectedCards[1] = cardAtPosition.getResImageID();
                    falseSelected = true;
                }

                // add selected cards as temporary selected that the images are displayed till another card is selected even if they do not match
                temporarySelectedCards[0] = selectedCard;
                temporarySelectedCards[1] = cardAtPosition;
                selectedCard = null;
                selectedCardsCount = 0;
                break;
        }
    }

    public Integer[] getFalseSelectedCards(){
        if(falseSelected){
            falseSelected = false;
            return falseSelectedCards;
        }
        return null;
    }

    public int getDeckSize(){ return deck.size();}


    public int getImageResID(int position) {
        MemoGameCard cardAtPosition = deck.get(position);
        if (isFound(cardAtPosition) || isSelected(cardAtPosition) || isTemporySelected(cardAtPosition)) {
            return cardAtPosition.getResImageID();
        }
        return notFoundImageResID;
    }

    public Uri getImageUri(int position){
        MemoGameCard cardAtPosition = deck.get(position);
        if (isFound(cardAtPosition) || isSelected(cardAtPosition) || isTemporySelected(cardAtPosition)) {
            return cardAtPosition.getImageUri();
        }
        return Uri.parse("android.resource://org.secuso.privacyfriendlymemory/" + R.drawable.secuso_not_found);
    }

    public boolean isFinished() {
        return deck.size() == foundCards.size() ? true : false;
    }

    private void setFound(MemoGameCard firstCard, MemoGameCard secondCard){
        foundCards.add(firstCard);
        foundCards.add(secondCard);
        currentPlayer.addFoundCard(firstCard);
        currentPlayer.addFoundCard(secondCard);
    }

    private boolean isFound(MemoGameCard card) {
        return foundCards.contains(card);
    }

    private boolean isSelected(MemoGameCard card) {
        return (selectedCard == card);
    }

    private boolean isTemporySelected(MemoGameCard card) {
        return (temporarySelectedCards[0] == card || temporarySelectedCards[1] == card);
    }

    private MemoGamePlayer getNextPlayer(){
        // get the next available player in the list, if last position is reached the first player is next
        int indexCurrentPlayer = players.indexOf(currentPlayer);
        if(indexCurrentPlayer+1 == players.size()) {
            return players.get(0);
        }else{
            return players.get(indexCurrentPlayer+1);
        }
    }
    public boolean isMultiplayer(){
        switch(memoryMode){
            case ONE_PLAYER:
                return false;
            default:
                return true;
        }
    }

    public MemoGamePlayer getCurrentPlayer(){
        return currentPlayer;
    }

    public MemoGameHighscore getHighscore(){
        if(isMultiplayer()){
            throw new UnsupportedOperationException("Highscore is disabled in multiplayer mode!");
        }
        return new MemoGameHighscore(memoryDifficulty, timer.getTime(), currentPlayer.getTries(), !isCustomDesign());
    }

    public List<MemoGamePlayer> getPlayers(){
        return players;
    }

    public MemoGameDifficulty getDifficulty(){
        return memoryDifficulty;
    }

    public MemoGameMode getMode(){ return memoryMode; }

    public int getTime(){
        return timer.getTime();
    }

    public void startTimer(){
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }

    public boolean isCustomDesign(){
        return cardDesign.isCustom();
    }

    public CardDesign getCardDesign(){
        return cardDesign;
    }

    public Map<Integer, MemoGameCard> getDeck(){ return deck; }
}
