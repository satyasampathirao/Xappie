package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.Model;
import com.xappie.utils.Constants;

import butterknife.ButterKnife;

/**
 * Created by Shankar on 11/21/2017.
 */

public class ClassifiedsAddFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {

    public static final String TAG = ClassifiedsAddFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    private String mID;

    private static IUpdateSelectedFile iUpdateSelectedFile;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;
        if (getArguments() != null && getArguments().containsKey(Constants.EVENT_ID)) {
            mID = getArguments().getString(Constants.EVENT_ID);
        } else {
            mID = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classifieds_add, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {

    }


    @Override
    public void onComplete(Model model) {

    }

    @Override
    public void updateFile(String path) {

    }
}
