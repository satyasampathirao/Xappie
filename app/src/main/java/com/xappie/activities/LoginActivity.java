package com.xappie.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.BuildConfig;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;
import com.xappie.R;
import com.xappie.aynctaskold.IAsyncCaller;
import com.xappie.aynctaskold.ServerIntractorAsync;
import com.xappie.models.DevicePreferencesUpdateModel;
import com.xappie.models.DeviceTokenUpdateModel;
import com.xappie.models.LoginModel;
import com.xappie.models.Model;
import com.xappie.parser.DevicePreferencesUpdateParser;
import com.xappie.parser.DeviceTokenUpdateParser;
import com.xappie.parser.LoginParser;
import com.xappie.utils.APIConstants;
import com.xappie.utils.Constants;
import com.xappie.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.xappie.utils.Constants.RC_SIGN_IN;

public class LoginActivity extends BaseActivity implements IAsyncCaller, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.linear_login)
    LinearLayout ll_login;
    @BindView(R.id.tv_log_cancel)
    TextView tv_log_cancel;
    @BindView(R.id.tv_log_login)
    TextView tv_log_login;
    @BindView(R.id.tv_log_sign_up)
    TextView tv_log_sign_up;
    @BindView(R.id.relative_login)
    RelativeLayout rl_login_email;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_show)
    TextView tv_show;
    @BindView(R.id.tv_trouble_getting)
    TextView tv_trouble_getting;
    @BindView(R.id.linear_login_privacy)
    LinearLayout ll_login_privacy;
    @BindView(R.id.tv_by_logging_agree)
    TextView tv_by_logging_agree;
    @BindView(R.id.tv_t_c)
    TextView tv_t_c;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.view_log_in)
    View view_log_in;
    @BindView(R.id.tv_or_sign_social)
    TextView tv_or_sign_social;
    @BindView(R.id.relative_login_bottom)
    LinearLayout rl_login_bottom;
    @BindView(R.id.imageButton_facebook)
    ImageButton im_facebook;
    @BindView(R.id.imageButton_google)
    ImageButton im_google;
    @BindView(R.id.imageButton_twitter)
    ImageButton im_twitter;


    private CallbackManager callbackManager;
    private String mFaceBookUniqueId = "";
    private LoginModel mLoginModel;
    private GoogleApiClient mGoogleApiClient;
    public TwitterAuthClient mTwitterAuthClient;

    private String mAuthType = "";
    private String mAuthToken = "";

    private String mFromData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
    }

    private void initUI() {
        btn_check.setTypeface(Utility.getMaterialIconsRegular(this));
        tv_show.setTypeface(Utility.getOpenSansRegular(this));
        tv_trouble_getting.setTypeface(Utility.getOpenSansRegular(this));
        tv_by_logging_agree.setTypeface(Utility.getOpenSansRegular(this));
        tv_t_c.setTypeface(Utility.getOpenSansRegular(this));
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));
        tv_log_cancel.setTypeface(Utility.getOpenSansRegular(this));
        tv_log_login.setTypeface(Utility.getOpenSansBold(this));
        tv_log_sign_up.setTypeface(Utility.getOpenSansRegular(this));

        et_email.setTypeface(Utility.getOpenSansRegular(this));
        et_password.setTypeface(Utility.getOpenSansRegular(this));

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();*/

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    "com.xappie",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String s = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Utility.showLog("Key Hash", "" + s);
                Log.d("KeyHash:", s);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

       /* et_email.setText("santosh@nevexa.com");
        et_password.setText("nevexa");*/
    }

    /**
     * This method is call facebook
     */
    @OnClick(R.id.imageButton_facebook)
    void callFacebook() {
        setUpFacebookLogin(true);
    }

    /**
     * This method calls the twitter
     */
    @OnClick(R.id.imageButton_twitter)
    void twitterSignUp() {
        mTwitterAuthClient = new TwitterAuthClient();
        mTwitterAuthClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                final String accessToken = result.data.getAuthToken().toString();
                AccountService service = TwitterCore.getInstance().getApiClient().getAccountService();
                Call<User> user = service.verifyCredentials(false, false, true);
                user.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        String userId = String.valueOf(userResult.data.id);
                        String name = userResult.data.name;
                        String email = userResult.data.email;
                        saveDetailsInDb(userId, email, name, "twitter");
                    }

                    @Override
                    public void failure(TwitterException exc) {

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    protected void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            /*GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Utility.showLog("statusCode : ", "statusCode " + statusCode);
            handleSignInResult(result);*/
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                fireBaseAuthWithGoogle(acct);
            } else {
                Toast.makeText(LoginActivity.this, "There was a trouble signing in-Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (mTwitterAuthClient != null) {
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = auth.getCurrentUser();
                            Utility.showLog("User uid", user.getUid());
                            Utility.showLog("User email", user.getEmail());
                            saveDetailsInDb(user.getUid(), user.getEmail(), user.getDisplayName(), "google");
                        }
                    }
                });
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
            saveDetailsInDb(acct.getId(), acct.getEmail(), acct.getDisplayName(), "google");
        } else {
            Utility.showLog("Logging error", "Logging error");
        }
    }

    private void setUpFacebookLogin(boolean isLogin) {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Collections.singletonList("email"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = "";
                                    String email = "";
                                    if (object.has("name"))
                                        name = object.getString("name");
                                    if (object.has("email"))
                                        email = object.getString("email");
                                    mFaceBookUniqueId = object.getString("id");

                                    String token = loginResult.getAccessToken().getToken();
                                    Utility.showLog("name", "name" + name);
                                    Utility.showLog("token", "token" + token);
                                    saveDetailsInDb(mFaceBookUniqueId, email, name, "facebook");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
            }
        });
    }


    /**
     * This method is login with social intigation
     */
    private void saveDetailsInDb(String id, String email, String displayName, String type) {
        mFromData = type;
        mAuthType = type;
        mAuthToken = id;
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.AUTH_TYPE, type);
        paramMap.put("email", email);
        paramMap.put(Constants.AUTH_TOKEN, id);
        paramMap.put("password", "");
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }


    @OnClick(R.id.tv_log_sign_up)
    public void navigateSignUp() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    @OnClick(R.id.tv_show)
    public void showAndHidePassword() {
        if (tv_show.getText().toString().equalsIgnoreCase("Show")) {
            tv_show.setText("Hide");
            et_password.setTransformationMethod(null);
        } else {
            tv_show.setText("Show");
            et_password.setTransformationMethod(new PasswordTransformationMethod());
        }
    }


    @OnClick(R.id.imageButton_google)
    void googleSignUp() {
        /*Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);*/
        signInWithGoogle();
    }

    @OnClick(R.id.btn_check)
    public void navigateLogin() {/*
        Utility.setSharedPrefBooleanData(LoginActivity.this, Constants.IS_LOGIN_COMPLETED, true);
        finish();
        Intent signUpIntent = new Intent(this, DashBoardActivity.class);
        startActivity(signUpIntent);*/
        if (isValidFields()) {
            mAuthType = Constants.XAPPIE;
            mFromData = Constants.XAPPIE;
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
            paramMap.put(Constants.AUTH_TYPE, Constants.XAPPIE);
            paramMap.put("email", et_email.getText().toString());
            paramMap.put("password", et_password.getText().toString());
            LoginParser mLoginParser = new LoginParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                    R.string.please_wait), true,
                    APIConstants.LOGIN, paramMap,
                    APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
            Utility.execute(serverIntractorAsync);

            Utility.showLog("USER NAME", "" + et_email.getText().toString());
            Utility.showLog("PASSWORD ", "" + et_password.getText().toString());
        }

    }

    private boolean isValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_email.getText().toString())) {
            Utility.setSnackBar(this, et_email, "Please enter username");
            et_email.requestFocus();
            isValid = false;
        } else if (et_password.getText().toString().length() < 4) {
            Utility.setSnackBar(this, et_password, "Please enter password");
            et_password.requestFocus();
            isValid = false;
        }
        return isValid;
    }

    @OnClick(R.id.tv_trouble_getting)
    public void navigateForgotPassword() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }

    @OnClick(R.id.tv_log_cancel)
    public void navigateCancel() {
        this.onBackPressed();
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LoginModel) {
                mLoginModel = (LoginModel) model;
                if (mLoginModel.isStatus()) {
                    Utility.showToastMessage(LoginActivity.this, Utility.capitalizeFirstLetter(mLoginModel.getMessage()));
                    Utility.setSharedPrefBooleanData(LoginActivity.this, Constants.IS_LOGIN_COMPLETED, true);
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.IS_FB_LOGIN, mFromData);
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_FIRST_NAME, mLoginModel.getFirst_name());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_LAST_NAME, mLoginModel.getLast_name());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_DISPLAY_NAME, mLoginModel.getDisplay_name());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_MAIL_ID, mLoginModel.getEmail());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_PHOTO, mLoginModel.getPhoto());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_UUID, mLoginModel.getUuid());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.LOGIN_SESSION_ID, mLoginModel.getCi_session());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.JOIN_DATE, Utility.getJoiningDate(mLoginModel.getJoined_date()));
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.TOKEN, mLoginModel.getCi_session());
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.AUTH_TYPE, mAuthType);
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.AUTH_TOKEN, mAuthToken);
                    Utility.setSharedPrefStringData(LoginActivity.this, Constants.PASSWORD, et_password.getText().toString());
                   /* Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                    startActivity(signUpIntent);*/
                    updateDeviceData();
                    //Utility.setSharedPrefStringData(LoginActivity.this, Constants.SIGN_UP_CURRENT_DATE, Utility.getDate());
                } else {
                    Utility.showToastMessage(LoginActivity.this, Utility.capitalizeFirstLetter(mLoginModel.getMessage()));
                }
            } else if (model instanceof DeviceTokenUpdateModel) {
               /* Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                startActivity(signUpIntent);
                LoginActivity.this.finish();*/
                updateLoggedUserPreferences();
            } else if (model instanceof DevicePreferencesUpdateModel) {
                Intent signUpIntent = new Intent(this, DashBoardActivity.class);
                startActivity(signUpIntent);
                LoginActivity.this.finish();
            }
        }
    }

    private void updateLoggedUserPreferences() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("country", Utility.getSharedPrefStringData(this, Constants.SELECTED_COUNTRY_ID));
        paramMap.put("state", Utility.getSharedPrefStringData(this, Constants.SELECTED_STATE_ID));
        paramMap.put("city", Utility.getSharedPrefStringData(this, Constants.SELECTED_CITY_ID));
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.SELECTED_LOCALITY_ID)))
            paramMap.put("locality", Utility.getSharedPrefStringData(this, Constants.SELECTED_LOCALITY_ID));
        paramMap.put("language", Utility.getSharedPrefStringData(this, Constants.SELECTED_LANGUAGE_ID));
        paramMap.put("modules", Utility.getSharedPrefStringData(this, Constants.HOME_PAGE_CONTENTS)
                + "," + "ads,banners," + Utility.getSharedPrefStringData(this, Constants.HOME_PAGE_EVENTS_CONTENTS)
                + Utility.getSharedPrefStringData(this, Constants.HOME_PAGE_JOBS_CONTENTS));
        paramMap.put("notifications", Constants.HOME_PAGE_CONTENTS_DATA + "," + Constants.EVENTS_CLASSIFIEDS_JOBS);

        DevicePreferencesUpdateParser mDevicePreferencesUpdateParser = new DevicePreferencesUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.UPDATE_PREFERENCE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDevicePreferencesUpdateParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateDeviceData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("device_type", Constants.DEVICE_TYPE);
        paramMap.put("device_model", Constants.DEVICE_TYPE);
        paramMap.put("token", Utility.getSharedPrefStringData(LoginActivity.this, Constants.KEY_FCM_TOKEN));
        paramMap.put("latitude", "0.0");
        paramMap.put("longitude", "0.0");
        paramMap.put("city", "");

        DeviceTokenUpdateParser mDeviceTokenUpdateParser = new DeviceTokenUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), false,
                APIConstants.UPDATE_DEVICE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mDeviceTokenUpdateParser);
        Utility.execute(serverIntractorAsync);
    }

    @OnClick(R.id.tv_privacy)
    void navigatePrivacyPolicy() {
        String url = "http://www.xappie.com/pages/privacy_policy";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    @OnClick(R.id.tv_t_c)
    void navigateTerms()
    {
        String url = "http://www.xappie.com/pages/terms_conditions";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
