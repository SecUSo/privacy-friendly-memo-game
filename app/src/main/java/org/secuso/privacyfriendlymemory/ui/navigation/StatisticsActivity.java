package org.secuso.privacyfriendlymemory.ui.navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.common.MemoryStatistics;
import org.secuso.privacyfriendlymemory.common.ResIdAdapter;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoryDefaultImages;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StatisticsActivity extends AppCompatActivity {

    SharedPreferences  preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.menu_statistics);
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_statistics_reset:
                List<Integer> resIdsDeckOne = MemoryDefaultImages.getResIDs(CardDesign.FIRST, MemoryDifficulty.Hard, false);
                List<Integer> resIdsDeckTwo = MemoryDefaultImages.getResIDs(CardDesign.SECOND, MemoryDifficulty.Hard, false);
                List<String> resourceNamesDeckOne = ResIdAdapter.getResourceName(resIdsDeckOne, this);

                List<String> resourceNamesDeckTwo = ResIdAdapter.getResourceName(resIdsDeckTwo, this);
                Set<String> statisticsDeckOne = MemoryStatistics.createInitStatistics(resourceNamesDeckOne);
                Set<String> staticticsDeckTwo =  MemoryStatistics.createInitStatistics(resourceNamesDeckTwo);
                preferences.edit().putStringSet(Constants.STATISTICS_DECK_ONE, statisticsDeckOne).commit();
                preferences.edit().putStringSet(Constants.STATISTICS_DECK_TWO, staticticsDeckTwo).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
