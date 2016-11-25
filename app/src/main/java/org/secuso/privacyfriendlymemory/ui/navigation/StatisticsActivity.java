package org.secuso.privacyfriendlymemory.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.common.MemoGameStatistics;
import org.secuso.privacyfriendlymemory.common.ResIdAdapter;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoGameDefaultImages;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticsActivity extends AppCompatActivity {

    private static StatisticsActivity statisticsActivity;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistcs_content);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        setupActionBar();

        this.statisticsActivity = this;
        // setup up fragments in sectionspager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // setup tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.menu_statistics);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#024265")));
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
                List<Integer> resIdsDeckOne = MemoGameDefaultImages.getResIDs(CardDesign.FIRST, MemoGameDifficulty.Hard, false);
                List<Integer> resIdsDeckTwo = MemoGameDefaultImages.getResIDs(CardDesign.SECOND, MemoGameDifficulty.Hard, false);
                List<String> resourceNamesDeckOne = ResIdAdapter.getResourceName(resIdsDeckOne, this);

                List<String> resourceNamesDeckTwo = ResIdAdapter.getResourceName(resIdsDeckTwo, this);
                Set<String> statisticsDeckOne = MemoGameStatistics.createInitStatistics(resourceNamesDeckOne);
                Set<String> staticticsDeckTwo = MemoGameStatistics.createInitStatistics(resourceNamesDeckTwo);
                preferences.edit().putStringSet(Constants.STATISTICS_DECK_ONE, statisticsDeckOne).commit();
                preferences.edit().putStringSet(Constants.STATISTICS_DECK_TWO, staticticsDeckTwo).commit();
                mSectionsPagerAdapter.refresh(getApplicationContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        private FragmentManager fm;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(CardDesign.get(position + 1).getDisplayNameResId());
        }

        public void refresh(Context context) {
            for (Fragment f : fm.getFragments()) {
                if (f instanceof PlaceholderFragment) {
                    ((PlaceholderFragment) f).refresh(context);
                }
            }
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private ListView statisticsListView;
        private StatisticsAdapter statisticsAdapter;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View rootView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void refresh(Context context) {
            createListViewWithAdapter();
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
            this.rootView = rootView;
            statisticsListView = (ListView) rootView.findViewById(R.id.statistics_listview);
            createListViewWithAdapter();
            return rootView;
        }

        private void createListViewWithAdapter() {
            int currentPosition = getArguments().getInt(ARG_SECTION_NUMBER);
            CardDesign cardDesignInFragmenet = CardDesign.get(currentPosition + 1);
            MemoGameStatistics memoryStats = createStatistics(cardDesignInFragmenet);
            statisticsAdapter = new StatisticsAdapter(StatisticsActivity.statisticsActivity, memoryStats.getResourceNames(), memoryStats.getFalseSelectedCounts());
            statisticsListView.setAdapter(statisticsAdapter);
        }

        private MemoGameStatistics createStatistics(CardDesign design) {
            String statisticsConstants = "";
            switch (design) {
                case FIRST:
                    statisticsConstants = Constants.STATISTICS_DECK_ONE;
                    break;
                case SECOND:
                    statisticsConstants = Constants.STATISTICS_DECK_TWO;
                    break;
            }
            Set<String> statisticsSet = PreferenceManager.getDefaultSharedPreferences(statisticsActivity).getStringSet(statisticsConstants, new HashSet<String>());
            return new MemoGameStatistics(statisticsSet);
        }
    }

    static class StatisticsAdapter extends ArrayAdapter<String> {

        private Activity activity;
        private Map<String, Integer> orderedNameCountMapping = new LinkedHashMap<>();

        public StatisticsAdapter(Activity activity, String[] resourceNames, Integer[] falseSelectedCounts) {
            super(activity, R.layout.activity_single_statistics_entry, resourceNames);
            this.activity = activity;
            initOrderedNameCountMapping(resourceNames, falseSelectedCounts);
        }

        private void initOrderedNameCountMapping(String[] resourceNames, Integer[] falseSelectedCounts) {
            for (int i = 0; i < resourceNames.length; i++) {
                orderedNameCountMapping.put(resourceNames[i], falseSelectedCounts[i]);
            }
            orderedNameCountMapping = sortByValue(orderedNameCountMapping);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // invert index to show resource with highest false selection count on the top
            int invertedIndex = orderedNameCountMapping.size() - position - 1;
            LayoutInflater inflater = activity.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_single_statistics_entry, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.card_false_selected_stats);
            txtTitle.setText("\t" + activity.getResources().getString(R.string.false_selected) + "\t" + getCountAtPosition(invertedIndex));

            // get drawable from resource name
            ImageView imageView = (ImageView) rowView.findViewById(R.id.card_image);
            int drawableResourceId = activity.getResources().getIdentifier(getResourceNameAtPosition(invertedIndex), "drawable", activity.getPackageName());
            imageView.setImageResource(drawableResourceId);

            return rowView;
        }

        private int getCountAtPosition(int position) {
            return new ArrayList<Integer>(orderedNameCountMapping.values()).get(position);
        }

        private String getResourceNameAtPosition(int position) {
            return new ArrayList<String>(orderedNameCountMapping.keySet()).get(position);
        }

/*        private int getPercentageAtPosition(int position) {
            int totalFalseSelected = 0;
            for (Integer falseSelectedCount : orderedNameCountMapping.values()) {
                totalFalseSelected += falseSelectedCount;
            }
            int ownFalseSelectedCount = getCountAtPosition(position);
            if (totalFalseSelected == 0) {
                return 0;
            }
            return (ownFalseSelectedCount * 100 / totalFalseSelected);
        }
*/

        public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
            List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                @Override
                public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            Map<K, V> result = new LinkedHashMap<>();
            for (Map.Entry<K, V> entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }

    }
}
