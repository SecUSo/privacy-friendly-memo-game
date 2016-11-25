package org.secuso.privacyfriendlymemory.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.secuso.privacyfriendlymemory.Constants;
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
        for (String uriString : preferences.getStringSet(Constants.CUSTOM_CARDS_URIS, new LinkedHashSet<String>())) {
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
