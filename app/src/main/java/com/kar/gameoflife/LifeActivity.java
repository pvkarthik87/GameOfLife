package com.kar.gameoflife;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kar.gameoflife.ui.fragments.LifeFragment;

public class LifeActivity extends AppCompatActivity {

    private MenuItem mStartMenu, mStopMenu, mNextMenu;
    private static final String LIFE_TAG = "fragment_life";
    private LifeFragment mLifeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        initUI();
    }

    private void initUI() {
        Fragment lifeFragment = getSupportFragmentManager().findFragmentByTag(LIFE_TAG);
        if (lifeFragment == null) {
            mLifeFragment = LifeFragment.newInstance();
        } else {
            mLifeFragment = (LifeFragment) lifeFragment;
        }
        if (!mLifeFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, mLifeFragment, LIFE_TAG)
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(mLifeFragment)
                    .attach(mLifeFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_life, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mStartMenu = menu.findItem(R.id.startGame);
        mStopMenu = menu.findItem(R.id.stopGame);
        mNextMenu = menu.findItem(R.id.nextStep);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item == mStartMenu) {
            mStartMenu.setVisible(false);
            mStopMenu.setVisible(true);
            mNextMenu.setVisible(false);
            onStartClicked();
        }
        if(item == mStopMenu) {
            mStopMenu.setVisible(false);
            mStartMenu.setVisible(true);
            mNextMenu.setVisible(true);
            onStopClicked();
        }
        if(item == mNextMenu) {
            onNextStepClicked();
        }
        return true;
    }

    private void onNextStepClicked() {
        mLifeFragment.next();
    }

    private void onStartClicked() {
        mLifeFragment.start();
    }

    private void onStopClicked() {
        mLifeFragment.stop();
    }
}
