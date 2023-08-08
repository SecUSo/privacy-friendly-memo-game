package org.secuso.privacyfriendlymemory.backup

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.JsonReader
import android.util.Log
import androidx.annotation.NonNull
import org.secuso.privacyfriendlybackup.api.backup.DatabaseUtil
import org.secuso.privacyfriendlybackup.api.backup.FileUtil
import org.secuso.privacyfriendlybackup.api.pfa.IBackupRestorer
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess


class BackupRestorer : IBackupRestorer {

    @Throws(IOException::class)
    private fun readPreferences(reader: JsonReader, preferences: SharedPreferences.Editor) {
        reader.beginObject()
        while (reader.hasNext()) {
            val name: String = reader.nextName()
            Log.d("preference", name)
            when (name) {
                "custom_deck_key",
                "deck2_key",
                "deck1_key",
                "FIRST_APP_START" -> preferences.putBoolean(name, reader.nextBoolean())
                "HIGHSCORE_EASY_TRIES",
                "HIGHSCORE_EASY",
                "SELECTED_CARD_DESIGN",
                "HIGHSCORE_EASY_TIME",
                "FirstLaunchManager.PREF_BREAK_PICKER_MINUTES" -> preferences.putInt(name, reader.nextInt())
                "STATISTICS_DECK_ONE",
//                "CUSTOM_CARDS_URIS", // Sadly not possible as permissions have to be granted manually by the user by selecting them
                "STATISTICS_DECK_TWO" -> preferences.putStringSet(name, readPreferenceSet(reader))
                else -> throw RuntimeException("Unknown preference $name")
            }
        }
        reader.endObject()
    }

    private fun readPreferenceSet(reader: JsonReader): Set<String> {
        val preferenceSet = mutableSetOf<String>()

        reader.beginArray()
        while (reader.hasNext()) {
            preferenceSet.add(reader.nextString());
        }
        reader.endArray()
        return preferenceSet
    }

    override fun restoreBackup(context: Context, restoreData: InputStream): Boolean {
        return try {
            val isReader = InputStreamReader(restoreData)
            val reader = JsonReader(isReader)
            val preferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

            // START
            reader.beginObject()
            while (reader.hasNext()) {
                val type: String = reader.nextName()
                when (type) {
                    "preferences" -> readPreferences(reader, preferences)
                    else -> throw RuntimeException("Can not parse type $type")
                }
            }
            reader.endObject()
            preferences.commit()

            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        const val TAG = "PFABackupRestorer"
    }
}