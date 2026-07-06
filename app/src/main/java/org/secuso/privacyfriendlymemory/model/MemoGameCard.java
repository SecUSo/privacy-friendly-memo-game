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
import androidx.annotation.DrawableRes;

/**
 * Created by Hannes on 20.05.2016.
 */
public class MemoGameCard {

    private final int matchingId;
    private final int resImageID;
    private final Uri imageUri;

    public MemoGameCard(int matchingId, @DrawableRes int resImageID, Uri imageUri){
        this.matchingId = matchingId;
        this.resImageID = resImageID;
        this.imageUri = imageUri;
    }


    public int getMatchingId() {
        return matchingId;
    }


    public int getResImageID() {
        return resImageID;
    }

    public Uri getImageUri(){ return imageUri; }
}
