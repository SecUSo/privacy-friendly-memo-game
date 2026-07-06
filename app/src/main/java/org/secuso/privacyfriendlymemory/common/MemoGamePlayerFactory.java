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
package org.secuso.privacyfriendlymemory.common;

import org.secuso.privacyfriendlymemory.model.MemoGameMode;
import org.secuso.privacyfriendlymemory.model.MemoGamePlayer;

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
