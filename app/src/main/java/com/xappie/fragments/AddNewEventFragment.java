package com.xappie.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xappie.R;
import com.xappie.activities.DashBoardActivity;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.interfaces.IUpdateSelectedFile;
import com.xappie.models.AddEventModel;
import com.xappie.models.EventsModel;
import com.xappie.models.Model;
import com.xappie.parser.AddEventSuccessParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.io.File;
import java.util.Calendar;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shankar on 7/28/2017.
 */
public class AddNewEventFragment extends Fragment implements IAsyncCaller, IUpdateSelectedFile {

    public static final String TAG = AddNewEventFragment.class.getSimpleName();
    private DashBoardActivity mParent;

    @BindView(R.id.tv_about_event)
    TextView tv_about_event;
    @BindView(R.id.tv_when_event_taking_place)
    TextView tv_when_event_taking_place;
    @BindView(R.id.tv_where_is_the_event_taking_place)
    TextView tv_where_is_the_event_taking_place;

    @BindView(R.id.edt_name_of_the_event)
    EditText edt_name_of_the_event;
    @BindView(R.id.edt_tag_line)
    EditText edt_tag_line;
    @BindView(R.id.edt_description)
    EditText edt_description;
    @BindView(R.id.edt_upload_image)
    EditText edt_upload_image;
    @BindView(R.id.edt_cost)
    EditText edt_cost;
    @BindView(R.id.edt_dress_code)
    EditText edt_dress_code;
    @BindView(R.id.et_type_of_event)
    EditText et_type_of_event;

    @BindView(R.id.edt_start_date)
    EditText edt_start_date;
    @BindView(R.id.edt_end_date)
    EditText edt_end_date;
    @BindView(R.id.edt_start_time)
    EditText edt_start_time;
    @BindView(R.id.edt_end_time)
    EditText edt_end_time;

    @BindView(R.id.edt_name_of_the_location)
    EditText edt_name_of_the_location;
    @BindView(R.id.edt_address)
    EditText edt_address;


    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_upload)
    Button btn_upload;

    private Typeface mTypefaceOpenSansRegular;
    private Typeface mTypefaceOpenSansBold;
    public static File mYourFile;
    private AddEventModel addEventModel;
    private EventsModel eventsModel;

    private static IUpdateSelectedFile iUpdateSelectedFile;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashBoardActivity) getActivity();
        iUpdateSelectedFile = this;
        if (getArguments() != null && getArguments().containsKey(Constants.EVENT_MODEL)) {
            eventsModel = (EventsModel) getArguments().get(Constants.EVENT_MODEL);
        } else {
            eventsModel = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_event, container, false);
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
        mTypefaceOpenSansBold = Utility.getOpenSansBold(mParent);

        tv_about_event.setTypeface(mTypefaceOpenSansBold);
        tv_when_event_taking_place.setTypeface(mTypefaceOpenSansBold);
        tv_where_is_the_event_taking_place.setTypeface(mTypefaceOpenSansBold);

        edt_name_of_the_event.setTypeface(mTypefaceOpenSansRegular);
        edt_tag_line.setTypeface(mTypefaceOpenSansRegular);
        edt_description.setTypeface(mTypefaceOpenSansRegular);
        edt_upload_image.setTypeface(mTypefaceOpenSansRegular);
        edt_cost.setTypeface(mTypefaceOpenSansRegular);
        edt_dress_code.setTypeface(mTypefaceOpenSansRegular);
        et_type_of_event.setTypeface(mTypefaceOpenSansRegular);

        edt_start_time.setTypeface(mTypefaceOpenSansRegular);
        edt_end_time.setTypeface(mTypefaceOpenSansRegular);

        edt_name_of_the_location.setTypeface(mTypefaceOpenSansRegular);
        edt_address.setTypeface(mTypefaceOpenSansRegular);

        btn_submit.setTypeface(mTypefaceOpenSansRegular);
        btn_upload.setTypeface(mTypefaceOpenSansRegular);

        setPreData();
    }

    /**
     * This method is used to set old data
     */
    private void setPreData() {
        if (eventsModel != null) {
            edt_name_of_the_event.setText(eventsModel.getName());
            edt_tag_line.setText(eventsModel.getTag());
            edt_description.setText(eventsModel.getDescription());
            edt_upload_image.setText(eventsModel.getImage());
            edt_cost.setText(eventsModel.getCost());
            edt_dress_code.setText(eventsModel.getDress_code());
            edt_start_date.setText(eventsModel.getStart_time().substring(0, 10));
            edt_start_time.setText(eventsModel.getStart_time().substring(11, eventsModel.getStart_time().length()));
            edt_end_date.setText(eventsModel.getEnd_time().substring(0, 10));
            edt_end_time.setText(eventsModel.getEnd_time().substring(11, eventsModel.getStart_time().length()));
            edt_name_of_the_location.setText(eventsModel.getCity());
            edt_address.setText(eventsModel.getAddress());
        }
    }


    @OnClick(R.id.btn_submit)
    void submitDetails() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put("event_name", edt_name_of_the_event.getText().toString());
            paramMap.put("tag_line", edt_tag_line.getText().toString());
            paramMap.put("description", edt_description.getText().toString());
            paramMap.put("cost", edt_cost.getText().toString());
            paramMap.put("dress_code", edt_dress_code.getText().toString());
            paramMap.put("start_time", edt_start_date.getText().toString() + " " + edt_start_time.getText().toString());
            paramMap.put("end_time", edt_end_date.getText().toString() + " " + edt_end_time.getText().toString());
            paramMap.put("location", edt_name_of_the_location.getText().toString());
            paramMap.put("address", edt_address.getText().toString());
            paramMap.put("country", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_COUNTRY_ID));
            paramMap.put("state", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_STATE_ID));
            paramMap.put("city", Utility.getSharedPrefStringData(mParent, Constants.SELECTED_CITY_ID));
            paramMap.put("photo", Utility.convertFileToByteArray(mYourFile));
            paramMap.put("photo_name", edt_upload_image.getText().toString());
            AddEventSuccessParser mAddEventSuccessParser = new AddEventSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                    R.string.please_wait), true,
                    APIConstants.ADD_EVENT, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mAddEventSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    /**
     * This method is used to upload
     */
    @OnClick(R.id.btn_upload)
    void eventDialog() {
        showEventDialog();
    }

    private static int mStartYear;
    private static int mStartMonth;
    private static int mStartDay;


    @OnClick(R.id.edt_start_date)
    void selectStartDate() {
        DialogFragment newFragment = null;
        newFragment = new SelectDateFragment(edt_start_date, "start_date");
        newFragment.show(mParent.getSupportFragmentManager(), "DatePicker");
        edt_end_time.setText("");
    }

    @OnClick(R.id.edt_start_time)
    void selectStartTime() {
        TimePickerFragment timePickerFragment = null;
        timePickerFragment = new TimePickerFragment(edt_start_time);
        timePickerFragment.show(mParent.getSupportFragmentManager(), "TimePicker");
    }

    @OnClick(R.id.edt_end_time)
    void selectEndTime() {
        TimePickerFragment timePickerFragment = null;
        timePickerFragment = new TimePickerFragment(edt_end_time);
        timePickerFragment.show(mParent.getSupportFragmentManager(), "TimePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        EditText editText;

        public TimePickerFragment() {
        }

        @SuppressLint("ValidFragment")
        public TimePickerFragment(EditText mEditText) {
            this.editText = mEditText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            editText.setText((hourOfDay < 10 ? ("0" + hourOfDay)
                    : hourOfDay) + ":" + (minute < 10 ? ("0" + minute)
                    : minute));
        }
    }

    @OnClick(R.id.edt_end_date)
    void selectEndDate() {
        if (!edt_start_date.getText().toString().equals("")) {
            DialogFragment newFragment = null;
            newFragment = new SelectDateFragment(edt_end_date, "end_date");
            newFragment.show(mParent.getSupportFragmentManager(), "DatePicker");
        } else {
            Utility.setSnackBar(mParent, edt_end_date, "Please select start date first");
        }
    }


    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        EditText editText;
        String mFrom;

        public SelectDateFragment() {
        }

        @SuppressLint("ValidFragment")
        public SelectDateFragment(EditText mEditText, String mFrom) {
            this.editText = mEditText;
            this.mFrom = mFrom;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            if (mFrom.equals("start_date")) {
                //mDatePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, yy);
                mCalendar.set(Calendar.MONTH, mm);
                mCalendar.set(Calendar.DAY_OF_MONTH, dd);
                mCalendar.set(Calendar.HOUR_OF_DAY, 0);
                mCalendar.set(Calendar.MINUTE, 0);
                mCalendar.set(Calendar.SECOND, 0);
                mDatePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
            }
            Calendar mCalendar = Calendar.getInstance();
            if (mFrom.equals("end_date")) {
                mCalendar.set(Calendar.YEAR, mStartYear);
                mCalendar.set(Calendar.MONTH, mStartMonth);
                mCalendar.set(Calendar.DAY_OF_MONTH, mStartDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, 0);
                mCalendar.set(Calendar.MINUTE, 0);
                mCalendar.set(Calendar.SECOND, 0);
                mDatePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
            }
            return mDatePickerDialog;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            if (mFrom.equals("start_date")) {
                mStartDay = day;
                mStartYear = year;
                mStartMonth = month - 1;
            }
            editText.setText(year + "-" + (month < 10 ? ("0" + month)
                    : month) + "-" + (day < 10 ? ("0" + day)
                    : day));
        }
    }

    /**
     * This method is used to show the edit dialog
     */
    private void showEventDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select event image"), Constants.FROM_POST_FORUM_ADD_EVENT_ID);
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(edt_name_of_the_event.getText().toString())) {
            Utility.setSnackBar(mParent, edt_name_of_the_event, "Please enter name of the event");
            edt_name_of_the_event.requestFocus();
            isValid = false;
        }/* else if (edt_tag_line.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, edt_tag_line, "Please enter tag line");
            edt_tag_line.requestFocus();
            isValid = false;
        } */ else if (edt_description.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, edt_description, "Please enter description");
            edt_description.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_upload_image.getText().toString())) {
            Utility.setSnackBar(mParent, edt_upload_image, "Please choose event image");
            edt_upload_image.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_cost.getText().toString())) {
            Utility.setSnackBar(mParent, edt_cost, "If cost is not there mention it as Zero");
            edt_cost.requestFocus();
            isValid = false;
        } /*else if (Utility.isValueNullOrEmpty(edt_dress_code.getText().toString())) {
            Utility.setSnackBar(mParent, edt_dress_code, "Please enter dress code");
            edt_dress_code.requestFocus();
            isValid = false;
        } */ else if (Utility.isValueNullOrEmpty(edt_start_date.getText().toString())) {
            Utility.setSnackBar(mParent, edt_start_date, "Please select start date");
            edt_start_date.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_start_time.getText().toString())) {
            Utility.setSnackBar(mParent, edt_start_time, "Please select start time");
            edt_start_time.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_end_date.getText().toString())) {
            Utility.setSnackBar(mParent, edt_end_date, "Please select end date");
            edt_end_date.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_end_time.getText().toString())) {
            Utility.setSnackBar(mParent, edt_end_time, "Please enter end time");
            edt_end_time.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_end_time.getText().toString())) {
            Utility.setSnackBar(mParent, edt_end_time, "Please enter end time");
            edt_end_time.requestFocus();
            isValid = false;
        } else if (!isValidStartDatesAndTime()) {
            Utility.setSnackBar(mParent, edt_end_time, "Enter future start time");
            edt_end_time.requestFocus();
            isValid = false;
        } else if (!isValidDatesAndTime()) {
            Utility.setSnackBar(mParent, edt_end_time, "End date should be after the start date and time");
            edt_end_time.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_name_of_the_location.getText().toString())) {
            Utility.setSnackBar(mParent, edt_name_of_the_location, "Please enter name of the location");
            edt_name_of_the_location.requestFocus();
            isValid = false;
        } else if (Utility.isValueNullOrEmpty(edt_address.getText().toString())) {
            Utility.setSnackBar(mParent, edt_address, "Please enter address");
            edt_address.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidStartDatesAndTime() {
        Calendar mCal = Calendar.getInstance();
        int mCurrentDate = mCal.get(Calendar.DAY_OF_MONTH);
        int mCurrentMonth = mCal.get(Calendar.MONTH) + 1;
        int mCurrentYear = mCal.get(Calendar.YEAR);
        int mCurrentHour = mCal.get(Calendar.HOUR_OF_DAY);
        int mCurrentMinute = mCal.get(Calendar.MINUTE);

        String startTime = edt_start_time.getText().toString();
        String startTimeFormatted = startTime.replace(":", "");

        String mCurrentDateAsString = (mCurrentMonth < 10 ? ("0" + mCurrentMonth)
                : mCurrentMonth) + "-" + (mCurrentDate < 10 ? ("0" + mCurrentDate)
                : mCurrentDate) + "-" + mCurrentYear;
        if (mCurrentDateAsString.equalsIgnoreCase(edt_start_date.getText().toString())) {
            int startTimeCurrent = Integer.parseInt(String.valueOf((mCurrentHour < 10 ? ("0" + mCurrentHour)
                    : mCurrentHour)) + String.valueOf((mCurrentMinute < 10 ? ("0" + mCurrentMinute)
                    : mCurrentMinute)));
            if (startTimeCurrent < Integer.parseInt(startTimeFormatted)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * This method is will check the is dates and time after
     */
    private boolean isValidDatesAndTime() {
        if (edt_start_date.getText().toString().equalsIgnoreCase(edt_end_date.getText().toString())) {
            String startTime = edt_start_time.getText().toString();
            String endTime = edt_end_time.getText().toString();
            String startTimeFormatted = startTime.replace(":", "");
            String endTimeFormatted = endTime.replace(":", "");
            if (Integer.parseInt(startTimeFormatted) < Integer.parseInt(endTimeFormatted)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            //if (model.isStatus()) {
            if (model instanceof AddEventModel) {
                addEventModel = (AddEventModel) model;
                Utility.showToastMessage(mParent, addEventModel.getMessage());
                clearData();
            }
            //}
        }
    }

    /**
     * This method is used to clear data
     */
    private void clearData() {
        edt_name_of_the_event.setText("");
        edt_tag_line.setText("");
        edt_description.setText("");
        edt_upload_image.setText("");
        mYourFile = null;
        edt_cost.setText("");
        edt_dress_code.setText("");

        edt_start_date.setText("");
        edt_end_date.setText("");
        edt_start_time.setText("");
        edt_end_time.setText("");

        edt_name_of_the_location.setText("");
        edt_address.setText("");
    }

    @Override
    public void updateFile(String path) {
        mYourFile = new File(path);
        edt_upload_image.setText(mYourFile.getName());
    }
}
