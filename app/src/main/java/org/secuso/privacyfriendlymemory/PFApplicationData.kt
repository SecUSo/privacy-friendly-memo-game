package org.secuso.privacyfriendlymemory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import org.secuso.pfacore.model.Theme
import org.secuso.pfacore.model.about.About
import org.secuso.pfacore.model.preferences.Preferable
import org.secuso.pfacore.model.preferences.settings.ISettingData
import org.secuso.pfacore.ui.PFData
import org.secuso.pfacore.ui.help.Help
import org.secuso.pfacore.ui.preferences.appPreferences
import org.secuso.pfacore.ui.preferences.settings.appearance
import org.secuso.pfacore.ui.preferences.settings.settingDeviceInformationOnErrorReport
import org.secuso.pfacore.ui.preferences.settings.settingThemeSelector
import org.secuso.pfacore.ui.tutorial.buildTutorial

/**
 * Single source of truth for the data the PFA-Core empty-shell needs (preferences,
 * settings, about and tutorial). Built once and exposed as a singleton.
 *
 * All of the app's persisted preferences are declared here so that the PFA-Core backup
 * system can include and restore them; its restorer rejects any key it does not know.
 */
class PFApplicationData private constructor(context: Context) {

    lateinit var theme: ISettingData<String>
        private set
    lateinit var firstAppStart: Preferable<Boolean>
        private set
    lateinit var includeDeviceDataInReport: Preferable<Boolean>
        private set

    private val preferences = appPreferences(context) {
        preferences {
            firstAppStart = preference { key = Constants.FIRST_APP_START; default = true; backup = true }

            // Deck selection
            preference { key = "deck1_key"; default = true; backup = true }
            preference { key = "deck2_key"; default = false; backup = true }
            preference { key = "custom_deck_key"; default = false; backup = true }
            preference { key = Constants.SELECTED_CARD_DESIGN; default = 1; backup = true }

            // Highscores
            preference { key = Constants.HIGHSCORE_EASY; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_EASY_TRIES; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_EASY_TIME; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_MODERATE; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_MODERATE_TRIES; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_MODERATE_TIME; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_HARD; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_HARD_TRIES; default = 0; backup = true }
            preference { key = Constants.HIGHSCORE_HARD_TIME; default = 0; backup = true }

            // Statistics (stored as JSON strings, see PreferenceSetUtil)
            preference { key = Constants.STATISTICS_DECK_ONE; default = ""; backup = true }
            preference { key = Constants.STATISTICS_DECK_TWO; default = ""; backup = true }

            // Custom card URIs are not backed up: the URIs would need their permissions
            // granted again manually, so restoring them would not work anyway.
            preference { key = Constants.CUSTOM_CARDS_URIS; default = ""; backup = false }
        }
        settings {
            appearance {
                theme = settingThemeSelector
            }
            category("Error Report") {
                includeDeviceDataInReport = settingDeviceInformationOnErrorReport
            }
        }
    }

    private val about = About(
        name = context.resources.getString(R.string.app_name),
        version = BuildConfig.VERSION_NAME,
        authors = context.resources.getString(R.string.about_author_names),
        repo = "https://github.com/SecUSo/privacy-friendly-memory"
    )

    private val tutorial = buildTutorial {
        stage {
            title = context.getString(R.string.field_select_description_headline)
            description = context.getString(R.string.field_select_description)
        }
        stage {
            title = context.getString(R.string.difficulty_description_headline)
            description = context.getString(R.string.difficulty_description)
        }
    }

    private val help = Help.build(context) {
        item {
            title { resource(R.string.help_game_mode) }
            description { literal(context.getString(R.string.help_single_player) + "\n" + context.getString(R.string.help_duo_player)) }
        }
        item {
            title { resource(R.string.help_game_difficulty) }
            description { resource(R.string.help_game_difficulty_summary) }
        }
        item {
            title { resource(R.string.custom_deck_options) }
            description { resource(R.string.help_custom_deck) }
        }
        item {
            title { resource(R.string.menu_highscore) }
            description { resource(R.string.help_highscore) }
        }
        item {
            title { resource(R.string.help_permissions) }
            description { resource(R.string.help_permissions_summary) }
        }
    }

    val data: PFData = PFData(
        preferences = preferences,
        about = about,
        help = help,
        tutorial = tutorial,
        theme = theme.state.map { Theme.valueOf(it) },
        firstLaunch = firstAppStart,
        includeDeviceDataInReport = includeDeviceDataInReport,
    )

    companion object {
        private var _instance: PFApplicationData? = null
        fun instance(context: Context): PFApplicationData {
            if (_instance == null) {
                _instance = PFApplicationData(context.applicationContext)
            }
            return _instance!!
        }
    }
}
