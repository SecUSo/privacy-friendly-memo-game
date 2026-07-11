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
package org.secuso.privacyfriendlymemory.ui.navigation;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.PFApplicationData;
import org.secuso.privacyfriendlymemory.ui.AppCompatPreferenceActivity;
import org.secuso.privacyfriendlymemory.R;

import java.util.List;

/**
 * Show the best scores for every difficulty and let the user to reset them. After the migration
 * it read and write the highscores over the PFApplicationData variables, and not more the raw
 * preferences.
 */
public class HighscoreActivity extends AppCompatPreferenceActivity {

    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_highscore_reset:
                PFApplicationData data = PFApplicationData.instance(this);
                // reset score, tries and time for each mode
                data.getHighscoreEasy().setValue(0);
                data.getHighscoreEasyTries().setValue(0);
                data.getHighscoreEasyTime().setValue(0);

                data.getHighscoreModerate().setValue(0);
                data.getHighscoreModerateTries().setValue(0);
                data.getHighscoreModerateTime().setValue(0);

                data.getHighscoreHard().setValue(0);
                data.getHighscoreHardTries().setValue(0);
                data.getHighscoreHardTime().setValue(0);
                getFragmentRefreshListener().onRefresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.menu_highscore);
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_highscore_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || HelpFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class HelpFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_highscore_general);
            setHasOptionsMenu(true);
            setHighscoreToUI();
            setHighscoreResetListener();
        }

        private void setScoreInPreference(String preferenceKey, int score, int tries, int time){
            Preference preference = findPreference(preferenceKey);
            preference.setTitle("Score:\t" + score);
            preference.setSummary(getString(R.string.win_tries
            ) + "\t" + tries +"\n" + getString(R.string.win_time)+ "\t" + timeToString(time));
        }

        private void setHighscoreToUI(){
            // get highscores from preferences
            PFApplicationData data = PFApplicationData.instance(getActivity().getApplicationContext());
            int highscoreEasy = data.getHighscoreEasy().getValue();
            int highscoreEasyTries = data.getHighscoreEasyTries().getValue();
            int highscoreEasyTime = data.getHighscoreEasyTime().getValue();

            int highscoreModerate = data.getHighscoreModerate().getValue();
            int highscoreModerateTries = data.getHighscoreModerateTries().getValue();
            int highscoreModerateTime = data.getHighscoreModerateTime().getValue();

            int highscoreHard = data.getHighscoreHard().getValue();
            int highscoreHardTries = data.getHighscoreHardTries().getValue();
            int highscoreHardTime = data.getHighscoreHardTime().getValue();

            // set highscore in views
            setScoreInPreference("highscore_easy", highscoreEasy, highscoreEasyTries, highscoreEasyTime);
            setScoreInPreference("highscore_moderate", highscoreModerate, highscoreModerateTries, highscoreModerateTime);
            setScoreInPreference("highscore_hard", highscoreHard,highscoreHardTries, highscoreHardTime);
        }

        private void setHighscoreResetListener(){
            ((HighscoreActivity)getActivity()).setFragmentRefreshListener(new HighscoreActivity.FragmentRefreshListener(){
                @Override
                public  void onRefresh(){
                    setHighscoreToUI();
                }
            });
        }

        private String timeToString(int time) {
            int seconds = time % 60;
            int minutes = ((time - seconds) / 60) % 60;
            int hours = (time - minutes - seconds) / (3600);
            String h, m, s;
            s = (seconds < 10) ? "0" + String.valueOf(seconds) : String.valueOf(seconds);
            m = (minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
            h = (hours < 10) ? "0" + String.valueOf(hours) : String.valueOf(hours);
            return h + ":" + m + ":" + s + "\t (h:m:s)";
        }

    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}