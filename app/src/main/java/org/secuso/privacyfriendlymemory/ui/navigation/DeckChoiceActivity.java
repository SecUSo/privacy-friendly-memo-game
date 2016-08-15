package org.secuso.privacyfriendlymemory.ui.navigation;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.MemoryDeck;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;
import org.secuso.privacyfriendlymemory.ui.AppCompatPreferenceActivity;
import org.secuso.privacyfriendlymemory.ui.R;

import java.util.ArrayList;
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

        private int PICK_IMAGE_MULTIPLE = 100;
        private Set<String> customImageUris = new HashSet<>();

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
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.d("DeckChoiceActivity", "In onActivityResult");
            /*
            try {
                // When an Image is picked
                if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    imagesEncodedList = new ArrayList<String>();
                    if(data.getData()!=null){

                        Uri mImageUri=data.getData();

                        // Get the cursor
                        Cursor cursor = getContentResolver().query(mImageUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded  = cursor.getString(columnIndex);
                        cursor.close();

                    }else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                // Get the cursor
                                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                imageEncoded  = cursor.getString(columnIndex);
                                imagesEncodedList.add(imageEncoded);
                                cursor.close();

                            }
                            Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        }
                    }
                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        */
            super.onActivityResult(requestCode, resultCode, data);
        }


        private void createOptionsItemListener(){
            Preference setCustomDeckPreference = findPreference("set_custom_deck");
            Preference resetCustomDeckPreference = findPreference("reset_custom_deck");

            setCustomDeckPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    // let user pick multiple custom images
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE_MULTIPLE);

                    // check if enough images are picked
                    int neededImageSize = MemoryDifficulty.Hard.getDeckSize() / 2;
                    if(customImageUris.size() >= neededImageSize){
                        sharedPreferences.edit().putStringSet(Constants.CUSTOM_CARDS_URIS, customImageUris).commit();
                        thirdBox.setEnabled(true);
                    }

                    return true;
                }
            });

            resetCustomDeckPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences.edit().putStringSet(Constants.CUSTOM_CARDS_URIS, new HashSet<String>()).commit();
                    customImageUris.clear();
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