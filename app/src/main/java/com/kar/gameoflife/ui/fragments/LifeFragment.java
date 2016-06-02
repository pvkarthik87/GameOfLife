package com.kar.gameoflife.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kar.gameoflife.R;
import com.kar.gameoflife.controllers.LifeController;
import com.kar.gameoflife.ui.adapters.LifeAdapter;
import com.kar.gameoflife.uitls.UIUtils;

/**
 * Created by Karthik on 5/31/2016.
 */
public class LifeFragment extends Fragment implements LifeController.ILifeControllerListener {

    public static LifeFragment newInstance() {
        return new LifeFragment();
    }

    private RecyclerView mLifeRecylerView;
    private LifeController mLifeController;
    private LifeAdapter mLifeAdapter;
    private Handler mHandler = new Handler();

    private static final int TIMER_INTERVAL = 1000;

    Runnable mNextStepRunner = new Runnable() {
        @Override
        public void run() {
            try {
                next(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mNextStepRunner, TIMER_INTERVAL);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_life, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpUI(view);
    }

    private void setUpUI(View view) {
        mLifeRecylerView = (RecyclerView)view.findViewById(R.id.life_list_view);

        ViewTreeObserver observer = mLifeRecylerView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                setUpAdapter();
                mLifeRecylerView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
    }

    private void setUpAdapter() {
        int totalWidth = mLifeRecylerView.getWidth();
        int totalHeight = mLifeRecylerView.getHeight();
        int columns = totalWidth / (int)getResources().getDimension(R.dimen.cell_width);
        int rows = totalHeight / (int)getResources().getDimension(R.dimen.cell_height);
        RecyclerView.LayoutManager layout = new GridLayoutManager(getContext(), columns, LinearLayoutManager.VERTICAL, false);
        mLifeRecylerView.setLayoutManager(layout);
        mLifeController = new LifeController();
        mLifeController.setListener(this);
        mLifeAdapter = new LifeAdapter(mLifeController);
        mLifeRecylerView.setAdapter(mLifeAdapter);
        mLifeController.init(rows, columns);
    }

    @Override
    public void onStop() {
        stop();
        super.onStop();
    }

    @Override
    public void onDataInitilaized() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLifeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDataChanged(int position) {
        mLifeAdapter.notifyItemChanged(position);
    }

    public void next() {
        mLifeController.next();
    }

    public void start() {
        mNextStepRunner.run();
    }

    public void stop() {
        mHandler.removeCallbacks(mNextStepRunner);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mLifeController != null) {
            mLifeController.deinit();
        }
    }
}
