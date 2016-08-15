package org.secuso.privacyfriendlymemory.ui.navigation;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.ui.AppCompatPreferenceActivity;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DeckChoiceActivity extends AppCompatPreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }
    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        loadHeadersFromResource(R.xml.pref_deckchoice_headers, target);
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
    public static class HelpFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

        private SharedPreferences sharedPreferences = null;
        private List<CheckBoxPreference> checkBoxes = new LinkedList<>();

        private CheckBoxPreference firstBox = null;
        private CheckBoxPreference secondBox = null;
        private CheckBoxPreference thirdBox = null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_deckchoice_general);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
            setupCheckboxes();
            setupSelection();
            createOptionsItemListener();
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            saveSelectedCardDesign(preference.getKey());
            setupSelection();
            return true;
        }

        private void createOptionsItemListener(){
            Preference setCustomDeckPreference = findPreference("set_custom_deck");
            Preference resetCustomDeckPreference = findPreference("reset_custom_deck");

            setCustomDeckPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Set<String> dummyUris = new HashSet<String>();
                    for(int i = 0; i <= 100; i++){
                        dummyUris.add(String.valueOf(i));
                    }
                    sharedPreferences.edit().putStringSet(Constants.CUSTOM_CARDS_URIS, dummyUris).commit();
                    thirdBox.setEnabled(true);
                    return true;
                }
            });

            resetCustomDeckPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences.edit().putStringSet(Constants.CUSTOM_CARDS_URIS, new HashSet<String>()).commit();
                    // refresh selection after custom images are deleted in case of the custom deck was selected
                    if(thirdBox.isChecked()){
                        saveSelectedCardDesign("deck1_key");
                    }
                    setupSelection();
                    return true;
                }
            });

        }

        private void setupCheckboxes(){
            firstBox = (CheckBoxPreference) findPreference("deck1_key");
            secondBox = (CheckBoxPreference) findPreference("deck2_key");
            thirdBox = (CheckBoxPreference) findPreference(("custom_deck_key")) ;
            checkBoxes.add(firstBox);
            checkBoxes.add(secondBox);
            checkBoxes.add(thirdBox);
            for(CheckBoxPreference checkbox : checkBoxes){
                checkbox.setOnPreferenceClickListener(this);
            }
        }

        private void setupSelection(){
            CardDesign selectedDesign = CardDesign.get(sharedPreferences.getInt(Constants.SELECTED_CARD_DESIGN, 1));
            Set<String> selectedCustomImages = sharedPreferences.getStringSet(Constants.CUSTOM_CARDS_URIS, new HashSet<String>());
            if(selectedCustomImages.isEmpty()) {
                thirdBox.setEnabled(false);
            }

            switch(selectedDesign){
                case FIRST:
                    firstBox.setChecked(true);
                    secondBox.setChecked(false);
                    thirdBox.setChecked(false);
                    break;
                case SECOND:
                    firstBox.setChecked(false);
                    secondBox.setChecked(true);
                    thirdBox.setChecked(false);
                    break;
                case CUSTOM:
                    if(selectedCustomImages.isEmpty()) {
                        firstBox.setChecked(true);
                        secondBox.setChecked(false);
                        thirdBox.setChecked(false);
                    }else{
                        firstBox.setChecked(false);
                        secondBox.setChecked(false);
                        thirdBox.setChecked(true);
                    }
                    break;
            }
        }

        private void saveSelectedCardDesign(String checkboxKey){
            int cardDesignValue = 1;
            switch(checkboxKey){
                case "deck1_key":
                    cardDesignValue = 1;
                    break;
                case "deck2_key":
                    cardDesignValue = 2;
                    break;
                case "custom_deck_key":
                    cardDesignValue = 3;
                    break;
            }
            sharedPreferences.edit().putInt(Constants.SELECTED_CARD_DESIGN, cardDesignValue).commit();
        }
    }
}