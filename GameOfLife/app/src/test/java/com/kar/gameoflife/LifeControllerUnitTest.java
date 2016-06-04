package com.kar.gameoflife;

import com.kar.gameoflife.controllers.LifeController;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;

/**
 * Created by Karthik on 6/2/2016.
 */
public class LifeControllerUnitTest {

    private boolean mIsCallbackCame;
    private CountDownLatch mSignal;

    @Before
    public void setup() throws Exception {
        mIsCallbackCame = false;
        mSignal = new CountDownLatch(1);
    }

    @Test
    public void checkForAsyncInit() throws Exception {
        LifeController lc = new LifeController();
        LifeController.ILifeControllerListener l = new LifeControllerImpl();
        lc.setListener(l);
        lc.init(5, 5);
        mSignal.await();
        assertEquals(mIsCallbackCame, true);
        assertEquals(lc.isAlive(1), false);
        lc.toggleState(1);
        assertEquals(lc.isAlive(1), true);
        lc.next();
        assertEquals(lc.isAlive(1), false);
    }

    private class LifeControllerImpl implements LifeController.ILifeControllerListener {

        @Override
        public void onDataInitilaized() {
            mIsCallbackCame = true;
            mSignal.countDown();
        }

        @Override
        public void onDataChanged(int position) {

        }
    }

}
