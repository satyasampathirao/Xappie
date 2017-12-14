package com.xappie.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.adapters.SpinnerAdapter;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.CategoryModel;
import com.xappie.models.Model;
import com.xappie.models.SpinnerModel;
import com.xappie.parser.CategoryParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsSearchFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = JobsSearchFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.et_select_job_category)
    EditText et_select_job_category;
    @BindView(R.id.edt_company_name)
    EditText edt_company_name;
    @BindView(R.id.edt_city_name)
    EditText edt_city_name;
    @BindView(R.id.btn_search)
    Button btn_search;


    private Typeface mTypefaceOpenSansRegular;
    private CategoryModel categoryModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mTypefaceOpenSansRegular = Utility.getOpenSansRegular(mParent);

        edt_city_name.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        edt_company_name.setTypeface(mTypefaceOpenSansRegular);
        btn_search.setTypeface(mTypefaceOpenSansRegular);

        getJobCategoryData();
    }


    /**
     * This method is used to get the jobs category
     */
    private void getJobCategoryData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        CategoryParser mCategoryParser = new CategoryParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.JOB_CATEGORY_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mCategoryParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.btn_search)
    public void navigateFindJobs() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FILTER_CATEGORY, getCategoryId(et_select_job_category.getText().toString()));
        bundle.putString(Constants.FILTER_FROM, Constants.FILTER);
        bundle.putString(Constants.FILTER_CITY, edt_city_name.getText().toString());
        bundle.putString(Constants.FILTER_SEARCH, edt_company_name.getText().toString());
        Utility.navigateAllJobsFragment(new FindJobsListFragment(), FindJobsListFragment.TAG, bundle, mParent);
    }

    private String getCategoryId(String s) {
        String mCategoryId = "";
        for (int i = 0; i < categoryModel.getCategoryModels().size(); i++) {
            if (categoryModel.getCategoryModels().get(i).getName().equals(s)) {
                mCategoryId = categoryModel.getCategoryModels().get(i).getId();
            }
        }
        return mCategoryId;
    }


    @OnClick(R.id.et_select_job_category)
    void setDataToTheSpinner() {
        if (categoryModel != null)
            showSpinnerDialog(mParent, "Job Category", et_select_job_category, categoryModel.getmCategorySpinnerModels(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof CategoryModel) {
                categoryModel = (CategoryModel) model;
            }
        }
    }
}
