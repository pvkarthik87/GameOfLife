package com.kar.gameoflife;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

/**
 * Created by Karthik on 6/4/2016.
 */
public class LifeActivityFT extends ActivityInstrumentationTestCase2<LifeActivity> {

    private LifeActivity mLifeActivity;
    private View mNextStepMenuItem;

    public LifeActivityFT() {
        super(LifeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Starts the activity under test using
        // the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mLifeActivity = getActivity();
        mNextStepMenuItem = mLifeActivity
                .findViewById(R.id.nextStep);

    }

    /**
     * Test if your test fixture has been set up correctly.
     * You should always implement a test that
     * checks the correct setup of your test fixture.
     * If this tests fails all other tests are
     * likely to fail as well.
     */
    public void testPreconditions() {
        // Try to add a message to add context to your assertions.
        // These messages will be shown if
        // a tests fails and make it easy to
        // understand why a test failed
        assertNotNull("mLifeActivity is null", mLifeActivity);
        assertNotNull("mNextStepMenuItem is null", mNextStepMenuItem);
    }
}
