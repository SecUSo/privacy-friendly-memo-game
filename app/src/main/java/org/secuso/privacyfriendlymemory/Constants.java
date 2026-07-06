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
package org.secuso.privacyfriendlymemory;

/**
 * Created by Hannes on 05.05.2016.
 */
public final class Constants {

    private Constants(){} // this class should not be initialized

    // Preferences Constants
    public final static String FIRST_APP_START          = "FIRST_APP_START";
    public final static String SELECTED_CARD_DESIGN     = "SELECTED_CARD_DESIGN";
    public final static String CUSTOM_CARDS_URIS        = "CUSTOM_CARDS_URIS";

    // Preferences Constants Highscore
    public final static String HIGHSCORE_EASY           = "HIGHSCORE_EASY";
    public final static String HIGHSCORE_EASY_TRIES     = "HIGHSCORE_EASY_TRIES";
    public final static String HIGHSCORE_EASY_TIME      = "HIGHSCORE_EASY_TIME";

    public final static String HIGHSCORE_MODERATE       = "HIGHSCORE_MODERATE";
    public final static String HIGHSCORE_MODERATE_TRIES = "HIGHSCORE_MODERATE_TRIES";
    public final static String HIGHSCORE_MODERATE_TIME  = "HIGHSCORE_MODERATE_TIME";

    public final static String HIGHSCORE_HARD           = "HIGHSCORE_HARD";
    public final static String HIGHSCORE_HARD_TRIES     = "HIGHSCORE_HARD_TRIES";
    public final static String HIGHSCORE_HARD_TIME      = "HIGHSCORE_HARD_TIME";


    // Preferences Constants Statistics
    public final static String STATISTICS_DECK_ONE      =  "STATISTICS_DECK_ONE";
    public final static String STATISTICS_DECK_TWO      =  "STATISTICS_DECK_TWO";

    // Intent Extra Constants
    public final static String GAME_DIFFICULTY          = "GAME_DIFFICULTY";
    public final static String GAME_MODE                = "GAME_MODE";
    public final static String CARD_DESIGN              = "CARD_DESIGN";


}
