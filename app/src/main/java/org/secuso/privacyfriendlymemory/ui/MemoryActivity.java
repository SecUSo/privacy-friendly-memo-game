package org.secuso.privacyfriendlymemory.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.secuso.privacyfriendlymemory.Constants;
import org.secuso.privacyfriendlymemory.model.CardDesign;
import org.secuso.privacyfriendlymemory.model.Memory;
import org.secuso.privacyfriendlymemory.model.MemoryDifficulty;
import org.secuso.privacyfriendlymemory.model.MemoryHighscore;
import org.secuso.privacyfriendlymemory.model.MemoryLayoutProvider;
import org.secuso.privacyfriendlymemory.model.MemoryMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryActivity extends AppCompatDrawerActivity {

    private Memory memory;
    private MemoryLayoutProvider layoutProvider;
    private Timer timerViewUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);
        super.setupNavigationView();
        createMemory();
        createLayoutProvider();
        setupGridview();
        initFoundsCardsView();
        setupToolBar();
    }

    @Override
    protected void onResume(){
        super.onResume();
        memory.startTimer();
    }
    @Override
    protected void onPause(){
        super.onPause();
        memory.stopTimer();
    }

    private void setupToolBar(){
        // setup memory mode text view
        TextView modeView = (TextView) findViewById(R.id.gameModeText);
        modeView.setText(memory.getMode().getStringResID());
        // setup difficulty bar view
        List<MemoryDifficulty> validDifficulties = MemoryDifficulty.getValidDifficulties();
        int difficultyCounts = validDifficulties.size();
        RatingBar difficultyBar = (RatingBar) findViewById(R.id.gameModeStar);
        difficultyBar.setMax(difficultyCounts);
        difficultyBar.setNumStars(difficultyCounts);
        difficultyBar.setRating(validDifficulties.indexOf(memory.getDifficulty()) + 1);
        // setup diffculty text view
        TextView difficultyText = (TextView) findViewById(R.id.difficultyText);
        difficultyText.setText(getString(memory.getDifficulty().getStringResID()));

        // setup timertask and timer view
        final TextView timerView = (TextView) findViewById(R.id.timerView);
        timerViewUpdater = new Timer();
        timerViewUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerView.setText(timeToString(memory.getTime()));
                    }
                });
            }
        },0 , 1000);

    }

    private void setupGridview(){
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(layoutProvider.getColumnCount());
        final ViewGroup.MarginLayoutParams marginLayoutParams =(ViewGroup.MarginLayoutParams)gridview.getLayoutParams();
        marginLayoutParams.setMargins(layoutProvider.getMarginLeft(),layoutProvider.getMargin(),layoutProvider.getMarginRight(),0);
        gridview.setLayoutParams(marginLayoutParams);
        final MemoryImageAdapter imageAdapter = new MemoryImageAdapter(this, layoutProvider);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                memory.select(position);
                // adapter must be notified, that images will be refreshed if selected
                imageAdapter.notifyDataSetChanged();
                // update found cards and needed tries views
                TextView foundsValueView = (TextView) findViewById(R.id.found_value);
                StringBuilder foundsValueBuilder = new StringBuilder();
                foundsValueBuilder.append(memory.getFoundCardsSize()).append("/").append(memory.getDeckSize());
                String foundsValue = foundsValueBuilder.toString();
                foundsValueView.setText(foundsValue);
                TextView triesValueView = (TextView) findViewById(R.id.tries_value);
                triesValueView.setText(String.valueOf(memory.getTries()));

                if(memory.isFinished()){
                    showWinDialog();
                    gridview.setEnabled(false);
                    timerViewUpdater.cancel();
                }
            }
        });

    }
    private void initFoundsCardsView(){
        TextView foundsValueView = (TextView) findViewById(R.id.found_value);
        StringBuilder foundsValueBuilder = new StringBuilder();
        foundsValueBuilder.append(memory.getFoundCardsSize()).append("/").append(memory.getDeckSize());
        String foundsValue = foundsValueBuilder.toString();
        foundsValueView.setText(foundsValue);
    }
    private void showWinDialog(){
        final WinDialog dialog = new WinDialog(this, R.style.WinDialog, memory.getHighscore());
        dialog.getWindow().setContentView(R.layout.win_screen_layout);
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        dialog.show();

        final Activity activity = this;
        ((Button)dialog.findViewById(R.id.win_continue_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                activity.finish();
            }
        });
        ((Button)dialog.findViewById(R.id.win_showGame_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void createLayoutProvider(){
        layoutProvider = new MemoryLayoutProvider(this, memory);
    }

    private void createMemory() {
        Bundle intentExtras = getIntent().getExtras();
        MemoryMode mode = (MemoryMode) intentExtras.get(Constants.GAME_MODE);
        MemoryDifficulty difficulty = (MemoryDifficulty) intentExtras.get(Constants.GAME_DIFFICULTY);
        CardDesign design = (CardDesign) intentExtras.get(Constants.CARD_DESIGN);
        memory = new Memory(design, mode, difficulty);
    }

    private String timeToString(int time) {
        int seconds = time % 60;
        int minutes = ((time - seconds) / 60) % 60;
        int hours = (time - minutes - seconds) / (3600);
        String h, m, s;
        s = (seconds < 10) ? "0" + String.valueOf(seconds) : String.valueOf(seconds);
        m = (minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
        h = (hours < 10) ? "0" + String.valueOf(hours) : String.valueOf(hours);
        return h + ":" + m + ":" + s;
    }

    public class WinDialog extends Dialog {

        private MemoryHighscore highscore;
        public WinDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        public WinDialog(Context context, int themeResId, MemoryHighscore highscore) {
            super(context,themeResId);
            this.highscore = highscore;
        }



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            ((TextView)findViewById(R.id.win_time)).setText(timeToString(highscore.getTime()));
            ((TextView)findViewById(R.id.win_tries)).setText(String.valueOf(highscore.getTries()));
            ((TextView)findViewById(R.id.win_highscore)).setText(String.valueOf(highscore.getScore()));

        }


    }

}
