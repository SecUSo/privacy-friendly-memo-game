package org.secuso.privacyfriendlymemogame.common;

import org.secuso.privacyfriendlymemogame.model.MemoGameMode;
import org.secuso.privacyfriendlymemogame.model.MemoGamePlayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 24.05.2016.
 */
public class MemoGamePlayerFactory {

    public static List<MemoGamePlayer> createPlayers(MemoGameMode memoryMode){
        List<MemoGamePlayer> players = new LinkedList<>();
        int playerCount = memoryMode.getPlayerCount();
        MemoGamePlayer player;
        for(int i = 1; i <= playerCount; i++){
            player = new MemoGamePlayer(String.valueOf(i));
            players.add(player);
        }
        return players;
    }
}
