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

import androidx.annotation.StringRes;

import org.secuso.privacyfriendlymemory.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hannes on 19.05.2016.
 */
public enum MemoGameDifficulty {


    Easy(R.string.difficulty_easy, 16),
    Moderate(R.string.difficulty_moderate, 36),
    Hard(R.string.difficulty_hard, 64);

    private final int resID;
    private final int deckSize;

    private static List<MemoGameDifficulty> validDifficulties = new LinkedList<>();

    static{
        validDifficulties.add(Easy);
        validDifficulties.add(Moderate);
        validDifficulties.add(Hard);
    }


    MemoGameDifficulty(@StringRes int resID, int deckSize) {
        this.resID = resID;
        this.deckSize = deckSize;
    }

    public int getStringResID() {
        return resID;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public static List<MemoGameDifficulty> getValidDifficulties(){
        return validDifficulties;
    }

}
