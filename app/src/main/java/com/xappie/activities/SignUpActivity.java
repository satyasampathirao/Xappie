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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.xappie.models.ImageUploadModel;
import com.xappie.models.Model;
import com.xappie.models.SignupLoginSuccessModel;
import com.xappie.models.SignupSuccessModel;
import com.xappie.parser.ImageUpdateParser;
import com.xappie.parser.SignUpLoginSuccessParser;
import com.xappie.parser.SignUpSuccessParser;
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

public class SignUpActivity extends BaseActivity implements IAsyncCaller, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = SignUpActivity.class.getSimpleName();

    private CallbackManager callbackManager;
    private String mFaceBookUniqueId = "";
    public TwitterAuthClient mTwitterAuthClient;

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
    private SignupLoginSuccessModel mSignupLoginSuccessModel;
    private GoogleApiClient mGoogleApiClient;

    private ImageUploadModel mImageUploadModel;

    private String mProfileImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initUI();
        Utility.sendGoogleAnalytics(this, TAG);
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
        tv_privacy.setTypeface(Utility.getOpenSansRegular(this));
        tv_or_sign_social.setTypeface(Utility.getOpenSansRegular(this));

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


    /**
     * This method is call facebook
     */
    @OnClick(R.id.imageButton_facebook)
    void callFacebook() {
        setUpFacebookLogin(true);
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

                                    if (object.has("picture")) {
                                        JSONObject jsonObject = object.getJSONObject("picture");
                                        JSONObject dataObject = jsonObject.getJSONObject("data");
                                        if (dataObject.has("url"))
                                            mProfileImage = dataObject.getString("url");
                                    }

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
                parameters.putString("fields", "name,email,picture");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
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

    @OnClick(R.id.tv_sign_up_show)
    public void showAndHidePassword() {
        if (tv_sign_up_show.getText().toString().equalsIgnoreCase("Show")) {
            tv_sign_up_show.setText("Hide");
            et_sign_up_password.setTransformationMethod(null);
        } else {
            tv_sign_up_show.setText("Show");
            et_sign_up_password.setTransformationMethod(new PasswordTransformationMethod());
        }
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
        /*Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);*/
        signInWithGoogle();
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
            } else if (model instanceof SignupLoginSuccessModel) {
                mSignupLoginSuccessModel = (SignupLoginSuccessModel) model;
                if (mSignupLoginSuccessModel.isStatus()) {
                    Utility.showToastMessage(SignUpActivity.this, mSignupLoginSuccessModel.getMessage());
                    //Intent signUpOtpIntent = new Intent(this, DashBoardActivity.class);
                    //startActivity(signUpOtpIntent);
                    if (!Utility.isValueNullOrEmpty(mProfileImage)) {
                        updateImageToServer();
                    } else {
                        Intent signUpOtpIntent = new Intent(this, LoginActivity.class);
                        startActivity(signUpOtpIntent);
                    }
                } else {
                    Utility.showToastMessage(SignUpActivity.this, mSignupLoginSuccessModel.getMessage());
                }
            } else if (model instanceof ImageUploadModel) {
                mImageUploadModel = (ImageUploadModel) model;
                if (mImageUploadModel.isStatus()) {
                    Intent signUpOtpIntent = new Intent(this, LoginActivity.class);
                    startActivity(signUpOtpIntent);
                } else {
                    Intent signUpOtpIntent = new Intent(this, LoginActivity.class);
                    startActivity(signUpOtpIntent);
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
            /*GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Utility.showLog("statusCode : ", "statusCode " + statusCode);
            handleSignInResult(result);*/
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                if (acct != null) {
                    if (acct.getPhotoUrl() != null) {
                        mProfileImage = acct.getPhotoUrl().toString();
                        Utility.showLog("url", "" + mProfileImage);
                    } else {
                        Utility.showLog("Logging error", "Logging error");
                    }
                    Utility.showLog("Logging Success", "Logging Success" + acct.getDisplayName() + " " + acct.getId() + " " + acct.getEmail());
                }
                fireBaseAuthWithGoogle(acct);
            } else {
                Toast.makeText(SignUpActivity.this, "There was a trouble signing in-Please try again", Toast.LENGTH_SHORT).show();
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
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
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
            if (acct != null) {
                if (acct.getPhotoUrl() != null) {
                    mProfileImage = acct.getPhotoUrl().toString();
                    Utility.showLog("url", "" + mProfileImage);
                } else {
                    Utility.showLog("Logging error", "Logging error");
                }
                Utility.showLog("Logging Success", "Logging Success" + acct.getDisplayName() + " " + acct.getId() + " " + acct.getEmail());
                saveDetailsInDb(acct.getId(), acct.getEmail(), acct.getDisplayName(), "google");
            }
        } else {
            Utility.showLog("Logging error", "Logging error");
        }
    }

    /**
     * This method is used to save the details in the sever db
     */
    private void saveDetailsInDb(String id, String email, String displayName, String type) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.AUTH_TYPE, type);
        paramMap.put(Constants.FIRST_NAME, displayName);
        paramMap.put(Constants.LAST_NAME, "");
        paramMap.put(Constants.EMAIL, email);
        paramMap.put(Constants.AUTH_TOKEN, id);
        paramMap.put(Constants.GENDER, "");
        paramMap.put(Constants.MOBILE, "");
        SignUpLoginSuccessParser mSignUpLoginSuccessParser = new SignUpLoginSuccessParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.SIGN_UP, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mSignUpLoginSuccessParser);
        Utility.execute(serverIntractorAsync);
    }

    /**
     * This method is used to update the profile pic
     */
    private void updateImageToServer() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("photo_url", mProfileImage);
        paramMap.put("photo_name", "facebook_image");
        ImageUpdateParser imageUpdateParser = new ImageUpdateParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.UPDATE_PROFILE_PHOTO, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, imageUpdateParser);
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
