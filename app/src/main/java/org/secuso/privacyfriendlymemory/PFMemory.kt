/*
 This file is part of Privacy Friendly 2048.

 Privacy Friendly Sudoku is free software:
 you can redistribute it and/or modify it under the terms of the
 GNU General Public License as published by the Free Software Foundation,
 either version 3 of the License, or any later version.

 Privacy Friendly Sudoku is distributed in the hope
 that it will be useful, but WITHOUT ANY WARRANTY; without even
 the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Privacy Friendly Sudoku. If not, see <http://www.gnu.org/licenses/>.
 */
package org.secuso.privacyfriendlymemory

import android.app.Activity
import android.preference.PreferenceManager
import android.util.Log
import androidx.work.Configuration
import org.json.JSONArray
import org.secuso.pfacore.ui.PFApplication
import org.secuso.pfacore.ui.PFData
import org.secuso.privacyfriendlymemory.ui.MainActivity

class PFMemory : PFApplication() {

    override val name: String
        get() = getString(R.string.app_name)

    override val data: PFData
        get() = PFApplicationData.instance(this).data

    override val mainActivity: Class<out Activity> = MainActivity::class.java

    override fun onCreate() {
        migrateStringSetsToJson()
        super.onCreate()
        // Build the application data eagerly on the main thread. PFApplicationData wires up
        // LiveData transformations (e.g. the theme), and LiveData.setValue may only run on the
        // main thread. The backup runs on a background thread, so building it lazily there would
        // crash; building it here makes the singleton ready before the backup worker uses it.
        PFApplicationData.instance(this)
    }

    /**
     * The statistics and custom-card preferences used to be stored as Set<String>, which the
     * PFA-Core backup system cannot serialize. They are now stored as JSON strings (see
     * PreferenceSetUtil). Convert any leftover Set<String> values from an older version once.
     */
    private fun migrateStringSetsToJson() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val keys = listOf(
            Constants.STATISTICS_DECK_ONE,
            Constants.STATISTICS_DECK_TWO,
            Constants.CUSTOM_CARDS_URIS
        )
        for (key in keys) {
            val value = preferences.all[key]
            if (value is Set<*>) {
                val array = JSONArray()
                value.forEach { array.put(it.toString()) }
                preferences.edit().remove(key).putString(key, array.toString()).apply()
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build()
}
