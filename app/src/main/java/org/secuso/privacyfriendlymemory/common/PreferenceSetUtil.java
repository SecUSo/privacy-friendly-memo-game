package org.secuso.privacyfriendlymemory.common;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Helper for storing a {@code Set<String>} preference as a JSON-encoded string.
 * <p>
 * The PFA-Core backup system can serialize and restore String preferences, but it has no
 * support for {@code Set<String>} preferences. Storing the sets as a JSON string keeps the
 * existing in-memory {@code Set<String>} API while making the values backup-compatible.
 */
public final class PreferenceSetUtil {

    private PreferenceSetUtil() {
    }

    /**
     * Stores the given set as a JSON array string under the given key.
     */
    public static void putStringSet(SharedPreferences preferences, String key, Set<String> values) {
        JSONArray array = new JSONArray();
        for (String value : values) {
            array.put(value);
        }
        preferences.edit().putString(key, array.toString()).commit();
    }

    /**
     * Reads a set previously stored with {@link #putStringSet}. Returns {@code defaultValue}
     * if nothing is stored or the stored value cannot be parsed.
     */
    public static Set<String> getStringSet(SharedPreferences preferences, String key, Set<String> defaultValue) {
        String raw = preferences.getString(key, null);
        if (raw == null) {
            return defaultValue;
        }
        try {
            JSONArray array = new JSONArray(raw);
            Set<String> result = new LinkedHashSet<>();
            for (int i = 0; i < array.length(); i++) {
                result.add(array.getString(i));
            }
            return result;
        } catch (JSONException e) {
            return defaultValue;
        }
    }
}
