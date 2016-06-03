package org.secuso.privacyfriendlymemory.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hannes on 20.05.2016.
 */
public class Memory {

    private final MemoryDifficulty memoryDifficulty;
    private final MemoryMode memoryMode;

    private final Map<Integer, MemoryCard> deck;
    private final int notFoundImageResID;

    private MemoryPlayer currentPlayer;
    private MemoryCard selectedCard = null;
    private MemoryTimer timer;

    private MemoryCard[] temporySelectedCards = new MemoryCard[2];
    private List<MemoryCard> foundCards = new LinkedList<>();
    private List<MemoryPlayer> players = new LinkedList<>();

    private int selectedCardsCount = 0;
    private int tries = 0;


    public Memory(CardDesign cardDesign, MemoryMode memoryMode, MemoryDifficulty memoryDifficulty) {
        this.memoryDifficulty = memoryDifficulty;
        this.memoryMode = memoryMode;
        this.deck = new MemoryDeck(MemoryImages.getResIDs(cardDesign, memoryDifficulty)).getDeck();
        this.notFoundImageResID = MemoryImages.getNotFoundImageResID();
        this.players = MemoryPlayerFactory.createPlayers(memoryMode);
        this.currentPlayer = players.get(0);
        this.timer = new MemoryTimer();
    }

    public void select(int position) {
        MemoryCard cardAtPosition = deck.get(position);
        // do not count a selection on an already found or selected card
        if (isFound(cardAtPosition) || isSelected(cardAtPosition)) {
            return; //
        }
        switch (selectedCardsCount) {
            case 0:
                // set first selected card and clear tempory selected cards
                temporySelectedCards[0] = null;
                temporySelectedCards[1] = null;
                selectedCard = cardAtPosition;
                selectedCardsCount++;
                break;
            case 1:
                // set founded if cards are matching; if not matching and more than 1 player is available, set next player
                if (selectedCard.getMatchingId() == cardAtPosition.getMatchingId()) {
                    setFound(selectedCard, cardAtPosition);
                    if(isFinished()){
                        stopTimer();
                    }
                }else if(players.size() > 1){
                    currentPlayer = getNextPlayer();
                }
                // add selected cards as tempory selected that the images are displayed till another card is selected even if they do not match
                temporySelectedCards[0] = selectedCard;
                temporySelectedCards[1] = cardAtPosition;
                selectedCard = null;
                selectedCardsCount = 0;
                tries++;
                break;
        }
    }

    public int getFoundCardsSize(){
        return foundCards.size();
    }

    public int getDeckSize(){ return deck.size();}


    public int getTries(){
        return tries;
    }

    public int getImageResID(int position) {
        MemoryCard cardAtPosition = deck.get(position);
        if (isFound(cardAtPosition) || isSelected(cardAtPosition) || isTemporySelected(cardAtPosition)) {
            return cardAtPosition.getResImageID();
        }
        return notFoundImageResID;
    }

    public boolean isFinished() {
        return deck.size() == foundCards.size() ? true : false;
    }

    private void setFound(MemoryCard firstCard, MemoryCard secondCard){
        foundCards.add(firstCard);
        foundCards.add(secondCard);
        currentPlayer.addFoundCard(firstCard);
        currentPlayer.addFoundCard(secondCard);
    }

    private boolean isFound(MemoryCard card) {
        return foundCards.contains(card);
    }

    private boolean isSelected(MemoryCard card) {
        return (selectedCard == card);
    }

    private boolean isTemporySelected(MemoryCard card) {
        return (temporySelectedCards[0] == card || temporySelectedCards[1] == card);
    }

    private MemoryPlayer getNextPlayer(){
        // get the next available player in the list, if last position is reached the first player is next
        int indexCurrentPlayer = players.indexOf(currentPlayer);
        if(indexCurrentPlayer+1 == players.size()) {
            return players.get(0);
        }else{
            return players.get(indexCurrentPlayer+1);
        }
    }

    public MemoryHighscore getHighscore(){
        return new MemoryHighscore(memoryDifficulty, timer.getTime(), tries);
    }

    public MemoryDifficulty getDifficulty(){
        return memoryDifficulty;
    }

    public MemoryMode getMode(){ return memoryMode; }

    public int getTime(){
        return timer.getTime();
    }

    public void startTimer(){
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }


}
