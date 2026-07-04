package org.secuso.privacyfriendlymemory.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.secuso.pfacore.model.DrawerElement;
import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.R;
import org.secuso.privacyfriendlymemory.common.MemoGameStatistics;
import org.secuso.privacyfriendlymemory.common.PreferenceSetUtil;
import org.secuso.privacyfriendlymemory.common.ResIdAdapter;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoGameDefaultImages;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;
import org.secuso.privacyfriendlymemory.model.MemoGameMode;

import java.util.List;
import java.util.Set;


public class MainActivity extends BaseActivity {

    private SharedPreferences preferences   = null;
    private ViewPager viewPager             = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPreferences();

        // The first launch flag is owned by the PFA-Core splash and tutorial now, so it is
        // already consumed before we get here. Seed the per deck statistics once by checking
        // whether they exist instead of relying on that flag.
        if (PreferenceSetUtil.getStringSet(preferences, Constants.STATISTICS_DECK_ONE, java.util.Collections.<String>emptySet()).isEmpty()) {
            initStatistics();
        }

        setContentView(R.layout.activity_main_content);
        setupViewPager();
        setupDifficultyBar();
    }

    @Override
    public boolean isActiveDrawerElement(@NonNull DrawerElement element) {
        return element.getName().equals(getString(R.string.menu_menu));
    }

    public void setupViewPager() {
        final ImageView arrowLeft = (ImageView) findViewById(R.id.arrow_left);
        final ImageView arrowRight = (ImageView) findViewById(R.id.arrow_right);
        arrowLeft.setVisibility(View.INVISIBLE);
        arrowRight.setVisibility(View.VISIBLE);

        final SectionsPagerAdapter  sectionPagerAdapter = new SectionsPagerAdapter (getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.scroller);
        viewPager.setAdapter(sectionPagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // not used
            }

            @Override
            public void onPageSelected(int position) {
                arrowLeft.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
                arrowRight.setVisibility(position == 1 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // not used
            }
        });
    }

    public void setupDifficultyBar() {
        final TextView difficultyText = (TextView) findViewById(R.id.difficultyText);
        final RatingBar difficultyBar = (RatingBar) findViewById(R.id.difficultyBar);
        difficultyBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating < 1){
                    ratingBar.setRating(1);
                }
                difficultyText.setText(getString(MemoGameDifficulty.getValidDifficulties().get((int) ratingBar.getRating() - 1).getStringResID()));
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                viewPager.arrowScroll(View.FOCUS_LEFT);
                break;
            case R.id.arrow_right:
                viewPager.arrowScroll(View.FOCUS_RIGHT);
                break;
            case R.id.playButton:
                // get select game type and difficulty
                int modeIndex = viewPager.getCurrentItem();
                MemoGameMode memoryMode = MemoGameMode.getValidTypes().get(modeIndex);
                int difficultyIndex = ((RatingBar) findViewById(R.id.difficultyBar)).getProgress() - 1;
                MemoGameDifficulty memoryDifficulty = MemoGameDifficulty.getValidDifficulties().get(difficultyIndex < 0 ? 0 : difficultyIndex);

                // send game information to game activity
                Intent intent = new Intent(this, MemoActivity.class);
                intent.putExtra(Constants.GAME_MODE, memoryMode);
                intent.putExtra(Constants.GAME_DIFFICULTY, memoryDifficulty);
                int selectedCardDesign = preferences.getInt(Constants.SELECTED_CARD_DESIGN, 1);
                CardDesign cardDesign =  CardDesign.get(selectedCardDesign);
                intent.putExtra(Constants.CARD_DESIGN, cardDesign);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initStatistics(){
        List<Integer> resIdsDeckOne = MemoGameDefaultImages.getResIDs(CardDesign.FIRST, MemoGameDifficulty.Hard, false);
        List<Integer> resIdsDeckTwo = MemoGameDefaultImages.getResIDs(CardDesign.SECOND, MemoGameDifficulty.Hard, false);
        List<String> resourceNamesDeckOne = ResIdAdapter.getResourceName(resIdsDeckOne, this);

        List<String> resourceNamesDeckTwo = ResIdAdapter.getResourceName(resIdsDeckTwo, this);
        Set<String> statisticsDeckOne = MemoGameStatistics.createInitStatistics(resourceNamesDeckOne);
        Set<String> staticticsDeckTwo =  MemoGameStatistics.createInitStatistics(resourceNamesDeckTwo);
        PreferenceSetUtil.putStringSet(preferences, Constants.STATISTICS_DECK_ONE, statisticsDeckOne);
        PreferenceSetUtil.putStringSet(preferences, Constants.STATISTICS_DECK_TWO, staticticsDeckTwo);
    }

    private void setupPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a GameTypeFragment (defined as a static inner class below).
            return GameTypeFragment.newInstance(position);
        }



        @Override
        public int getCount() {
            // Show 2 total pages.
            return MemoGameMode.getValidTypes().size();
        }

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GameTypeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GameTypeFragment newInstance(int sectionNumber) {
            GameTypeFragment fragment = new GameTypeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public GameTypeFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_type_main_content, container, false);

            MemoGameMode memoryMode = MemoGameMode.getValidTypes().get(getArguments().getInt(ARG_SECTION_NUMBER));

            ImageView imageView = (ImageView) rootView.findViewById(R.id.gameTypeImage);

            imageView.setImageResource(memoryMode.getImageResID());

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(memoryMode.getStringResID()));
            return rootView;
        }
    }

}
