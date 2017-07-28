package com.sarps.alumni.fragment;

/**
 * Created by sarps-preeti on 11/3/2016.
 */

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.sarps.alumni.DiyApproachActivity;
import com.sarps.alumni.Login_Email;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.OtpActivity;
import com.sarps.alumni.ProfileFillUpActivity;
import com.sarps.alumni.Profile_form_twitter;
import com.sarps.alumni.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.sarps.alumni.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    ImageView iv_fb, iv_linked, iv_twitter, iv_login;
    ImageView iv_google, fb;
    private CallbackManager callbackManager;
    private TextView textView;
    String username, email;
    private static final String ARG_PARAM = "param1";
    private String mParam;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    //Signing Options
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;
    RequestToken requestToken = null;
    twitter4j.auth.AccessToken accessToken;
    String oauth_url, oauth_verifier, profile_url;
    Dialog auth_dialog;
    WebView web;
    ProgressDialog progress;
    Twitter twitter;
    Bitmap bitmap;
    SharedPreferences pref;
    private static String CONSUMER_KEY = "FnSugYYjakpkFBVQwyVyntBfO";
    private static String CONSUMER_SECRET = "pX6y21sTpiy4vc4yQySvrqYPYSGvp0aI0AlX1gdPaZI3dKYHx0";

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

//                            Bundle bFacebookData = getFacebookData(object);
                            try {
                                String id = object.getString("id");
                                URL profile_pic = null;
                                try {
                                    profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                                    Log.i("profile_pic :-", profile_pic + "");
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                username = object.optString("name");
                                email = object.optString("email");
//                                String birthday = object.optString("birthday"); // 01/31/1980 format

                                System.out.println("email :- " + email);
                                Intent i = new Intent(getActivity().getApplicationContext(), ProfileFillUpActivity.class);
                                i.putExtra("email", email);
                                i.putExtra("username", username);
                                i.putExtra("profile_pic", "" + profile_pic);
                                i.putExtra("login_type", "fb");
                                System.out.println("profile_pic :-" + profile_pic);
                                startActivity(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email,picture");
            request.setParameters(parameters);
            request.executeAsync();
            displayMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        twitter = new TwitterFactory().getInstance();
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fb);
        iv_linked = (ImageView) view.findViewById(R.id.iv_linked);
        iv_twitter = (ImageView) view.findViewById(R.id.iv_twitter);
        iv_google = (ImageView) view.findViewById(R.id.iv_google);
        iv_login = (ImageView) view.findViewById(R.id.iv_login);
        //Initializing google api client


        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), ProfileFillUpActivity.class));
            }
        });
        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login_Email.class));
            }
        });
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TokenGet().execute();
            }
        });
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DiyApproachActivity) getActivity()).login();
            }
        });
        loginButton.setBackgroundResource(R.drawable.facebook);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);
        disconnectFromFacebook();
    }

    //This function will option signing intent
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            System.out.println("result :- " + result);
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent i = new Intent(getActivity().getApplicationContext(), ProfileFillUpActivity.class);
            i.putExtra("email", acct.getEmail());
            i.putExtra("username", acct.getDisplayName());
            i.putExtra("profile_pic", "" + acct.getPhotoUrl().toString());
            i.putExtra("login_type", "google_plus");
            startActivity(i);

        } else {
            //If login fails
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }


    }

    //After the signing we are calling this function


    private void displayMessage(Profile profile) {
        if (profile != null) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    private class TokenGet extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                requestToken = twitter.getOAuthRequestToken();
                oauth_url = requestToken.getAuthorizationURL();
                System.out.println("oauth_url :- " + oauth_url);
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return oauth_url;
        }

        @Override
        protected void onPostExecute(String oauth_url) {
            if (oauth_url != null) {
                Log.e("URL", oauth_url);
                auth_dialog = new Dialog(getActivity());
                auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                auth_dialog.setContentView(R.layout.auth_dialog);
                web = (WebView) auth_dialog.findViewById(R.id.webv);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl(oauth_url);
                web.setWebViewClient(new WebViewClient() {
                    boolean authComplete = false;

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        if (url.contains("oauth_verifier") && authComplete == false) {
                            authComplete = true;
                            Log.e("Url", url);
                            Uri uri = Uri.parse(url);
                            oauth_verifier = uri.getQueryParameter("oauth_verifier");

                            auth_dialog.dismiss();
                            new AccessTokenGet().execute();
                        } else if (url.contains("denied")) {
                            auth_dialog.dismiss();
                            Toast.makeText(getActivity(), "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                auth_dialog.show();
                auth_dialog.setCancelable(true);
            } else {

                Toast.makeText(getActivity(), "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AccessTokenGet extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Fetching Data ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }
        @Override
        protected Boolean doInBackground(String... args) {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("ACCESS_TOKEN", accessToken.getToken());
                edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());
                User user = twitter.showUser(accessToken.getUserId());
                profile_url = user.getOriginalProfileImageURL();

                Intent i = new Intent(getActivity().getApplicationContext(), Profile_form_twitter.class);

                i.putExtra("username", user.getName() + "  ");
                i.putExtra("profile_pic", "" + user.getOriginalProfileImageURL());
                i.putExtra("login_type", "twitter");
                startActivity(i);
                edit.commit();
                Log.d("ACCESS_TOKEN", accessToken.getToken());
                Log.d("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());
                Log.d("Name", user.getName());
                Log.d("IMAGE_URL", user.getOriginalProfileImageURL());

            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            if (response) {
                progress.hide();
            }
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
