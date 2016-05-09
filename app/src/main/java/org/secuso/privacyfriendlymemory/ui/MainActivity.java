package org.secuso.privacyfriendlymemory.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.secuso.privacyfriendlymemory.preference.PreferencesConstants;
import org.secuso.privacyfriendlymemory.ui.dialog.WelcomeDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPreferences();

        if(isFirstAppStart()) {
            showWelcomeDialog();
            setAppStarted();
        }
        setContentView(R.layout.activity_main);
        setupNavigationView();
    }

    private void setAppStarted(){
        preferences.edit().putBoolean(PreferencesConstants.FIRST_APP_START, false).commit();
    }

    private void showWelcomeDialog(){
        new WelcomeDialog().show(getFragmentManager(), WelcomeDialog.class.getSimpleName());
    }

    private void setupPreferences(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private boolean isFirstAppStart(){
        return preferences.getBoolean(PreferencesConstants.FIRST_APP_START, true);
    }

    private void setupNavigationView(){
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
            case R.id.menu_highscore:
                break;
            case R.id.menu_settings:
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
            super.onBackPressed();
        }
    }
}
