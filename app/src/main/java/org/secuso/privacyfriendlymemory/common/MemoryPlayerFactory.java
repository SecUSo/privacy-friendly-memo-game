package org.secuso.privacyfriendlymemory.common;

import org.secuso.privacyfriendlymemory.model.MemoryMode;
import org.secuso.privacyfriendlymemory.model.MemoryPlayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoryPlayerFactory {

    public static List<MemoryPlayer> createPlayers(MemoryMode memoryMode){
        List<MemoryPlayer> players = new LinkedList<>();
        int playerCount = memoryMode.getPlayerCount();
        MemoryPlayer player;
        for(int i = 1; i <= playerCount; i++){
            player = new MemoryPlayer(String.valueOf(i));
            players.add(player);
        }
        return players;
    }
}
