package org.secuso.privacyfriendlymemory.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import org.secuso.privacyfriendlymemory.game.model.GameDifficulty;
import org.secuso.privacyfriendlymemory.game.model.GameType;
import org.secuso.privacyfriendlymemory.preference.PreferencesConstants;
import org.secuso.privacyfriendlymemory.ui.dialog.WelcomeDialog;


public class MainActivity extends AppCompatDrawerActivity {

    private SharedPreferences preferences   = null;
    private ViewPager viewPager             = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPreferences();

        if (isFirstAppStart()) {
            showWelcomeDialog();
            setAppStarted();
        }
        setContentView(R.layout.activity_main);
        setupViewPager();
        setupDifficultybar();
        super.setupNavigationView();
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

    public void setupDifficultybar() {
        final TextView difficultyText = (TextView) findViewById(R.id.difficultyText);
        final RatingBar difficultyBar = (RatingBar) findViewById(R.id.difficultyBar);
        difficultyBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating < 1){
                    ratingBar.setRating(1);
                }
                difficultyText.setText(getString(GameDifficulty.getValidDifficulties().get((int) ratingBar.getRating() - 1).getStringResID()));
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
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            default:
                Log.d(MainActivity.class.getSimpleName(), "View " + view.toString() + " clicked.");
        }
    }

    private void setAppStarted() {
        preferences.edit().putBoolean(PreferencesConstants.FIRST_APP_START, false).commit();
    }

    private void showWelcomeDialog() {
        new WelcomeDialog().show(getFragmentManager(), WelcomeDialog.class.getSimpleName());
    }

    private void setupPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private boolean isFirstAppStart() {
        return preferences.getBoolean(PreferencesConstants.FIRST_APP_START, true);
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
            // Show 3 total pages.
            return GameType.getValidTypes().size();
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

            GameType gameType = GameType.getValidTypes().get(getArguments().getInt(ARG_SECTION_NUMBER));

            ImageView imageView = (ImageView) rootView.findViewById(R.id.gameTypeImage);

            imageView.setImageResource(gameType.getImageResID());

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(gameType.getStringResID()));
            return rootView;
        }
    }

}
