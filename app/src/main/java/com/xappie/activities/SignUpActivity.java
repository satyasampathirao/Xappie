package com.xappie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.Model;
import com.xappie.models.SignupSuccessModel;
import com.xappie.parser.SignUpSuccessParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements IAsyncCaller, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.tv_sign_up_cancel)
    TextView tv_sign_up_cancel;
    @BindView(R.id.tv_sign_up_signUp)
    TextView tv_sign_up_signup;
    @BindView(R.id.tv_sign_up_login)
    TextView tv_sign_up_login;

    @BindView(R.id.et_sign_up_email)
    EditText et_sign_up_email;
    @BindView(R.id.et_sign_up_mobile)
    EditText et_sign_up_mobile;
    @BindView(R.id.et_sign_up_password)
    EditText et_sign_up_password;
    @BindView(R.id.tv_sign_up_show)
    TextView tv_sign_up_show;
    @BindView(R.id.checkbox_sign_up)
    CheckBox chb_sign_up;

    @BindView(R.id.linear_sign_up_privacy)
    LinearLayout ll_sign_up_privacy;
    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_and)
    TextView tv_and;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_or_sign_social)
    TextView tv_or_sign_social;
    @BindView(R.id.imageButton_facebook)
    ImageButton im_facebook;
    @BindView(R.id.imageButton_google)
    ImageButton im_google;
    @BindView(R.id.imageButton_twitter)
    ImageButton im_twitter;

    private SignupSuccessModel mSignupSuccessModel;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        btn_check.setTypeface(Utility.getMaterialIconsRegular(this));

        tv_sign_up_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_signup.setTypeface(Utility.getOpenSansBold(this));
        tv_sign_up_login.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_email.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_mobile.setTypeface(Utility.getOpenSansRegular(this));
        et_sign_up_password.setTypeface(Utility.getOpenSansRegular(this));
        tv_sign_up_show.setTypeface(Utility.getOpenSansRegular(this));
        chb_sign_up.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_and.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }


    /**
     * This method is call the service with validations checks
     */
    @OnClick(R.id.btn_check)
    public void navigateSignUpOtp() {
        if (isValidFields()) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
            paramMap.put("email", et_sign_up_email.getText().toString());
            paramMap.put("password", et_sign_up_password.getText().toString());
            paramMap.put("mobile", et_sign_up_mobile.getText().toString());
            SignUpSuccessParser mSignUpSuccessParser = new SignUpSuccessParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.SIGN_UP, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignUpSuccessParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    @OnClick(R.id.tv_sign_up_login)
    public void navigateLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.tv_sign_up_cancel)
    public void navigateCancel() {
        this.onBackPressed();
    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_sign_up_email.getText().toString())) {
            Utility.setSnackBar(this, et_sign_up_email, "Please enter email");
            et_sign_up_email.requestFocus();
            isValid = false;
        } else if (!et_sign_up_email.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,4})$")) {
            Utility.setSnackBar(this, et_sign_up_email, "Please enter valid email");
            et_sign_up_email.requestFocus();
            isValid = false;
        } else if (et_sign_up_password.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_sign_up_password, "Please enter password");
            et_sign_up_password.requestFocus();
            isValid = false;
        } else if (!chb_sign_up.isChecked()) {
            Utility.setSnackBar(this, et_sign_up_password, "Please accept terms & condition");
            et_sign_up_password.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @OnClick(R.id.imageButton_google)
    void googleSignUp() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SignupSuccessModel) {
                mSignupSuccessModel = (SignupSuccessModel) model;
                if (mSignupSuccessModel.isStatus()) {
                    Intent signUpOtpIntent = new Intent(this, SignUpOtpActivity.class);
                    signUpOtpIntent.putExtra(Constants.SIGN_UP_MAIL_ID, et_sign_up_email.getText().toString());
                    signUpOtpIntent.putExtra(Constants.SIGN_UP_PASSWORD, et_sign_up_password.getText().toString());
                    signUpOtpIntent.putExtra(Constants.SIGN_UP_MOBILE, et_sign_up_mobile.getText().toString());
                    signUpOtpIntent.putExtra(Constants.SIGN_UP_UUID, mSignupSuccessModel.getUuid());
                    startActivity(signUpOtpIntent);
                } else {
                    Utility.showToastMessage(SignUpActivity.this, mSignupSuccessModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Utility.showLog("statusCode : ", "statusCode " + statusCode);
            handleSignInResult(result);
        }
    }

    /**
     * This method is used to handle the intent
     */
    private void handleSignInResult(GoogleSignInResult result) {
        Utility.showLog("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Utility.showLog("Logging Success", "Logging Success" + acct.getDisplayName() + " " + acct.getId() + " " + acct.getEmail());
            saveDetailsInDb(acct.getId(), acct.getEmail(), acct.getDisplayName());
        } else {
            Utility.showLog("Logging error", "Logging error");
        }
    }

    /**
     * This method is used to save the details in the sever db
     */
    private void saveDetailsInDb(String id, String email, String displayName) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.AUTH_TYPE, "google");
        paramMap.put(Constants.FIRST_NAME, displayName);
        paramMap.put(Constants.LAST_NAME, "");
        paramMap.put(Constants.EMAIL, email);
        paramMap.put(Constants.AUTH_TOKEN, id);
        paramMap.put(Constants.GENDER, "");
        paramMap.put(Constants.MOBILE, "");
        SignUpSuccessParser mSignUpSuccessParser = new SignUpSuccessParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.SIGN_UP, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mSignUpSuccessParser);
        Utility.execute(serverIntractorAsync);
    }
}
