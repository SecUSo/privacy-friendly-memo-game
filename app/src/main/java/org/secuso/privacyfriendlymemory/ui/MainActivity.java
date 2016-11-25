package org.secuso.privacyfriendlymemory.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.common.MemoGameStatistics;
import org.secuso.privacyfriendlymemory.common.ResIdAdapter;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoGameDefaultImages;
import org.secuso.privacyfriendlymemory.model.MemoGameDifficulty;
import org.secuso.privacyfriendlymemory.model.MemoGameMode;
import org.secuso.privacyfriendlymemory.ui.navigation.HelpActivity;

import java.util.List;
import java.util.Set;


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
            initStatistics();
         }

        setContentView(R.layout.activity_main);
        setupViewPager();
        setupDifficultyBar();
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
        preferences.edit().putStringSet(Constants.STATISTICS_DECK_ONE, statisticsDeckOne).commit();
        preferences.edit().putStringSet(Constants.STATISTICS_DECK_TWO, staticticsDeckTwo).commit();
    }

    private void setAppStarted() {
        preferences.edit().putBoolean(Constants.FIRST_APP_START, false).commit();
    }

    private void showWelcomeDialog() {
        new WelcomeDialog().show(getFragmentManager(), WelcomeDialog.class.getSimpleName());
    }

    private void setupPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private boolean isFirstAppStart() {
        return preferences.getBoolean(Constants.FIRST_APP_START, true);
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

    public static class WelcomeDialog extends DialogFragment {


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater i = getActivity().getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setView(i.inflate(R.layout.dialog_welcome, null));
            builder.setIcon(R.mipmap.ic_drawer);
            builder.setTitle(getActivity().getString(R.string.welcome_title));
            builder.setPositiveButton(getActivity().getString(R.string.button_ok), null);
            builder.setNegativeButton(getActivity().getString(R.string.button_help), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), HelpActivity.class);
                    intent.putExtra(HelpActivity.EXTRA_SHOW_FRAGMENT, HelpActivity.HelpFragment.class.getName());
                    intent.putExtra(HelpActivity.EXTRA_NO_HEADERS, true);
                    startActivity(intent);
                }
            });
            return builder.create();
        }
    }

}
