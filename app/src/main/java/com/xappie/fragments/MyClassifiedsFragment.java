package com.xappie.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.AllMyClassifiedsListAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.ClassifiedsListModel;
import com.xappie.models.ClassifiedsModel;
import com.xappie.models.IAmGoingModel;
import com.xappie.models.Model;
import com.xappie.parser.ClassifiedsParser;
import com.xappie.parser.IAmGoingParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shankar on 11/22/2017.
 */

public class MyClassifiedsFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = MyClassifiedsFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    /**
     * AllClassifieds List set up
     */
    @BindView(R.id.listView)
    SwipeMenuListView listView;

    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    private AllMyClassifiedsListAdapter allMyClassifiedsListAdapter;
    private ClassifiedsListModel classifiedsListModel;
    private int mDeletePosition = -1;
    private IAmGoingModel iAmGoingModel;

    private String mCat_Id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();

        if (getArguments() != null && getArguments().containsKey(Constants.CLASSIFIEDS_CATEGORY_ID)) {
            mCat_Id = getArguments().getString(Constants.CLASSIFIEDS_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_classifieds, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }


    private void initUI() {
        getMyClassifiedsData("" + 1);
    }

    /**
     * This method is used to get the all the classifieds data from the server
     */
    private void getMyClassifiedsData(String pageNo) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            //linkedHashMap.put("type", "Public");
            linkedHashMap.put(Constants.PAGE_NO, pageNo);
            linkedHashMap.put(Constants.PAGE_SIZE, Constants.PAGE_SIZE_VALUE);
            ClassifiedsParser classifiedsParser = new ClassifiedsParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_MY_CLASSIFIEDS, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, classifiedsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete classifieds
     */
    private void getDeleteData(String delete_id) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            IAmGoingParser eventsListParser = new IAmGoingParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.DELETE_CLASSIFIED + delete_id, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, eventsListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the list view data
     */
    private void setGridViewData() {
        allMyClassifiedsListAdapter = new AllMyClassifiedsListAdapter(mParent, classifiedsListModel.getClassifiedsModels());
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF,
                        0xFF)));
                // set item width
                openItem.setWidth(dp2px(70));
                // set item title
                //openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(15);
                openItem.setIcon(R.drawable.ic_mode_edit_white_24dp);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);


                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF,
                        0xFF)));
                // set item width
                deleteItem.setWidth(dp2px(70));
                // set item title
                //deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(15);
                deleteItem.setIcon(R.drawable.ic_delete_sweep_white_24dp);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        listView.setAdapter(allMyClassifiedsListAdapter);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CLASSIFIEDS_ID, classifiedsListModel.getClassifiedsModels().get(position).getId());
                        Utility.navigateAllEventsFragment(new ClassifiedsAddFragment(), ClassifiedsAddFragment.TAG, bundle, mParent);
                        break;
                    case 1:
                        mDeletePosition = position;
                        getDeleteData(classifiedsListModel.getClassifiedsModels().get(position).getId());
                        break;
                }
                return false;
            }
        });
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ClassifiedsListModel) {
                classifiedsListModel = (ClassifiedsListModel) model;
                if (classifiedsListModel != null && classifiedsListModel.getClassifiedsModels().size() > 0) {
                    ll_no_data.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    setGridViewData();
                } else {
                    ll_no_data.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            } else if (model instanceof IAmGoingModel) {
                iAmGoingModel = (IAmGoingModel) model;
                Utility.showToastMessage(mParent, iAmGoingModel.getMessage());
                if (iAmGoingModel.isStatus()) {
                    ArrayList<ClassifiedsModel> classifiedsModels = classifiedsListModel.getClassifiedsModels();
                    classifiedsModels.remove(mDeletePosition);
                    classifiedsListModel.setClassifiedsModels(classifiedsModels);
                    allMyClassifiedsListAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
