package com.xappie.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.Model;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shankar on 11/21/2017.
 */

public class ClassifiedsAddFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {

    public static final String TAG = ClassifiedsAddFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.edt_title)
    EditText edt_title;

    @BindView(R.id.edt_description)
    EditText edt_description;

    @BindView(R.id.edt_cost)
    EditText edt_cost;
    @BindView(R.id.tv_contact_details)
    TextView tv_contact_details;

    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_address)
    EditText edt_address;

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
        edt_title.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_description.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_cost.setTypeface(Utility.getOpenSansRegular(mParent));

        tv_contact_details.setTypeface(Utility.getOpenSansRegular(mParent));

        edt_name.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_mobile.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_email.setTypeface(Utility.getOpenSansRegular(mParent));
        edt_address.setTypeface(Utility.getOpenSansRegular(mParent));
    }


    @Override
    public void onComplete(Model model) {

    }

    @Override
    public void updateFile(String path) {

    }
}
