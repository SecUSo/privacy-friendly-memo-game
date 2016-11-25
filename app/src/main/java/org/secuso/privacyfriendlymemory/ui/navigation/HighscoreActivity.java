package org.secuso.privacyfriendlymemory.ui.navigation;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.ui.AppCompatPreferenceActivity;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.List;

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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                // reset score, tries and time for each mode
                preferences.edit().putInt(Constants.HIGHSCORE_EASY, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_EASY_TRIES, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_EASY_TIME, 0).commit();

                preferences.edit().putInt(Constants.HIGHSCORE_MODERATE, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_MODERATE_TRIES, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_MODERATE_TIME, 0).commit();

                preferences.edit().putInt(Constants.HIGHSCORE_HARD, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_HARD_TRIES, 0).commit();
                preferences.edit().putInt(Constants.HIGHSCORE_HARD_TIME, 0).commit();
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
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            int highscoreEasy = preferences.getInt(Constants.HIGHSCORE_EASY, 0);
            int highscoreEasyTries = preferences.getInt(Constants.HIGHSCORE_EASY_TRIES, 0);
            int highscoreEasyTime = preferences.getInt(Constants.HIGHSCORE_EASY_TIME, 0);

            int highscoreModerate = preferences.getInt(Constants.HIGHSCORE_MODERATE, 0);
            int highscoreModerateTries = preferences.getInt(Constants.HIGHSCORE_MODERATE_TRIES, 0);
            int highscoreModerateTime = preferences.getInt(Constants.HIGHSCORE_MODERATE_TIME, 0);

            int highscoreHard = preferences.getInt(Constants.HIGHSCORE_HARD, 0);
            int highscoreHardTries = preferences.getInt(Constants.HIGHSCORE_HARD_TRIES, 0);
            int highscoreHardTime = preferences.getInt(Constants.HIGHSCORE_HARD_TIME, 0);

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