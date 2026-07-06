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

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import org.secuso.privacyfriendlymemory.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum MemoGameMode {

    ONE_PLAYER(R.string.mode_single_player, R.drawable.ic_person_black_24px, 1),
    DUO_PLAYER(R.string.mode_duo_player, R.drawable.ic_people_black_24px, 2);

    private final int resIDString;
    private final int resIDImage;
    private final int playerCount;
    private static List<MemoGameMode> validTypes = new LinkedList<>();


    static{
        validTypes.add(ONE_PLAYER);
        validTypes.add(DUO_PLAYER);
    }


    MemoGameMode(@StringRes int resIDString, @DrawableRes int resIDImage, int playerCount){
        this.resIDString = resIDString;
        this.resIDImage = resIDImage;
        this.playerCount = playerCount;
    }

    public int getStringResID() {
        return resIDString;
    }

    public int getImageResID(){
        return resIDImage;
    }

    public int getPlayerCount(){ return playerCount; }

    public static List<MemoGameMode> getValidTypes(){
        return validTypes;
    }
}
