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

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.common.PreferenceSetUtil;
import org.secuso.privacyfriendlymemory.ui.MemoActivity;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Hannes on 12.08.2016.
 */
public class MemoGameCustomImages {

    public static Set<Uri> getUris(MemoGameDifficulty memoryDifficulty) {
        Context context = MemoActivity.getAppContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<Uri> uris = new LinkedHashSet<>();
        for (String uriString : PreferenceSetUtil.getStringSet(preferences, Constants.CUSTOM_CARDS_URIS, new LinkedHashSet<String>())) {
            uris.add(Uri.parse(uriString));
        }

        // get images based on the deck size defined in the game difficulty
        int differentUris = memoryDifficulty.getDeckSize() / 2;
        if (differentUris > uris.size()) {
            throw new IllegalStateException("Requested deck contains not enough images for the specific game difficulty");
        }
        // create subset
        List<Uri> setAsList = new LinkedList<>(uris);
        return new LinkedHashSet<>(setAsList.subList(0, differentUris));
    }
}
