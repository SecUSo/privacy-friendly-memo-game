package org.secuso.privacyfriendlymemory.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.secuso.privacyfriendlymemory.ui.navigation.AboutActivity;
import org.secuso.privacyfriendlymemory.ui.navigation.DeckChoiceActivity;
import org.secuso.privacyfriendlymemory.ui.navigation.HelpActivity;
import org.secuso.privacyfriendlymemory.ui.navigation.HighscoreActivity;
import org.secuso.privacyfriendlymemory.ui.navigation.StatisticsActivity;

/**
 * Created by Hannes on 18.05.2016.
 */
public abstract class MemoAppCompatDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupNavigationView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.menu_main:
                // go back to main menu
                finish();
                break;
            case R.id.menu_highscore:
                intent = new Intent(this, HighscoreActivity.class);
                intent.putExtra(DeckChoiceActivity.EXTRA_SHOW_FRAGMENT, HighscoreActivity.HelpFragment.class.getName());
                intent.putExtra(DeckChoiceActivity.EXTRA_NO_HEADERS, true);
                break;
            case R.id.menu_statistics:
                intent = new Intent(this, StatisticsActivity.class);
                break;
            case R.id.menu_settings:
                intent = new Intent(this, DeckChoiceActivity.class);
                intent.putExtra(DeckChoiceActivity.EXTRA_SHOW_FRAGMENT, DeckChoiceActivity.HelpFragment.class.getName());
                intent.putExtra(DeckChoiceActivity.EXTRA_NO_HEADERS, true);
                break;
            case R.id.menu_help:
                intent = new Intent(this, HelpActivity.class);
                intent.putExtra(HelpActivity.EXTRA_SHOW_FRAGMENT, HelpActivity.HelpFragment.class.getName());
                intent.putExtra(HelpActivity.EXTRA_NO_HEADERS, true);
                break;
            case R.id.menu_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }

        if (intent != null){
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // show dialog, if user want to exit game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(getResources().getString(R.string.quit_game_text));
            builder.setNegativeButton(getResources().getString(R.string.quit_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // nothing to do here
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.quit_yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    cancelMemory();
                }
            });
            builder.show();
        }
    }

    private void cancelMemory(){
        super.onBackPressed();;
    }

}
