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
package org.secuso.privacyfriendlymemory.ui

import android.content.Intent
import android.preference.PreferenceActivity
import org.secuso.pfacore.model.DrawerElement
import org.secuso.pfacore.model.DrawerMenu
import org.secuso.pfacore.ui.activities.DrawerActivity
import org.secuso.privacyfriendlymemory.R
import org.secuso.privacyfriendlymemory.ui.navigation.DeckChoiceActivity
import org.secuso.privacyfriendlymemory.ui.navigation.HighscoreActivity
import org.secuso.privacyfriendlymemory.ui.navigation.StatisticsActivity

/**
 * Shared base for the activities that show the navigation drawer, backed by the PFA-Core
 * [DrawerActivity]. Only the app-specific entries are declared here; the Tutorial, Help,
 * Settings, About and error-report entries are added by [defaultDrawerSection] from the library.
 */
abstract class BaseActivity : DrawerActivity() {

    override fun drawer(): DrawerMenu = DrawerMenu.build {
        name = getString(R.string.app_name)
        icon = R.mipmap.ic_launcher

        section {
            activity {
                name = getString(R.string.menu_menu)
                icon = R.drawable.ic_home
                clazz = MainActivity::class.java
                extras = { it.apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP } }
            }
            activity {
                name = getString(R.string.menu_highscore)
                icon = android.R.drawable.ic_menu_myplaces
                clazz = HighscoreActivity::class.java
                extras = {
                    it.apply {
                        putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, HighscoreActivity.HelpFragment::class.java.name)
                        putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true)
                    }
                }
            }
            activity {
                name = getString(R.string.menu_statistics)
                icon = R.drawable.ic_statistics
                clazz = StatisticsActivity::class.java
            }
            activity {
                name = getString(R.string.menu_settings)
                icon = R.drawable.ic_settings
                clazz = DeckChoiceActivity::class.java
                extras = {
                    it.apply {
                        putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, DeckChoiceActivity.HelpFragment::class.java.name)
                        putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true)
                    }
                }
            }
        }

        defaultDrawerSection(this)
    }

    override fun isActiveDrawerElement(element: DrawerElement): Boolean = false
}
