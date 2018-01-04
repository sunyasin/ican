package org.ideacreation.can.app.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.ideacreation.can.App;
import org.ideacreation.can.R;
import org.ideacreation.can.app.security.AuthContext;
import org.ideacreation.can.app.validator.PasswordValidator;
import org.ideacreation.can.app.validator.PhoneValidator;
import org.ideacreation.can.common.model.enums.ProfileType;
import org.ideacreation.can.common.model.json.RegistrationInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private AuthContext authContext;
    private PhoneValidator phoneValidator;
    private PasswordValidator passwordValidator;
    private AlertDialog alertDialog;

    @BindView(R.id.registerLogin)
    AutoCompleteTextView mLoginView;

    @BindView(R.id.registerPassword)
    EditText mPasswordView;

    @BindView(R.id.radioPrivateId)
    RadioButton privateRadio;

    @BindView(R.id.radioShop)
    RadioButton shopRadio;

    @BindView(R.id.radioBusiness)
    RadioButton businessRadio;

    @BindView(R.id.fullName)
    EditText fullNameView;

    @BindView(R.id.address)
    EditText addressView;

    @BindView(R.id.locationButtonId)
    ImageButton locationButton;

    @BindView(R.id.registerForm)
    View mRegisterFormView;

    @BindView(R.id.registerProgress)
    View mProgressView;

    // Inject components
    @Inject
    public void setAuthContext(AuthContext authContext) {
        this.authContext = authContext;
    }

    @Inject
    public void setPhoneValidator(PhoneValidator phoneValidator) {
        this.phoneValidator = phoneValidator;
    }

    @Inject
    public void setPasswordValidator(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        App.getInjector().inject(this);
        ButterKnife.bind(this);
        authContext.injectRegisterActivity(this);

/*
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
*/
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
//        mGoogleApiClient.disconnect();
        super.onStop();
    }

    // Listeners
    @OnClick(R.id.locationButtonId)
    public void selectLocation() {
/*
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
*/
    }

    @OnCheckedChanged({R.id.radioPrivateId, R.id.radioBusiness, R.id.radioShop})
    void onGenderSelected(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.radioPrivateId:
                    addressView.setVisibility(View.GONE);
                    locationButton.setVisibility(View.GONE);
                    break;
                case R.id.radioShop:
                case R.id.radioBusiness:
                    addressView.setVisibility(View.VISIBLE);
                    locationButton.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @OnClick(R.id.registerButtonId)
    public void afterRegister() {
        Intent intent = new Intent("org.ideacreation.can.app.activity.MainTabActivity");
        startActivity(intent);
    }

    //    @OnClick(R.id.registerButtonId)
    public void doRegister() {
        if (!authContext.hasActiveRegisterTask()) {
            // Reset errors.
            mLoginView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String login = mLoginView.getText().toString();
            String password = mPasswordView.getText().toString();
            ProfileType profileType = privateRadio.isChecked() ? ProfileType.PERSON
                    : shopRadio.isChecked() ? ProfileType.SHOP : ProfileType.BUSINESSMAN;
            String fullName = fullNameView.getText().toString();
            String address = addressView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !passwordValidator.validate(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(login)) {
                mLoginView.setError(getString(R.string.error_field_required));
                focusView = mLoginView;
                cancel = true;
            } else if (!phoneValidator.validate(login)) {
                mLoginView.setError(getString(R.string.error_invalid_email));
                focusView = mLoginView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true);

                authContext.doRegister(new RegistrationInfo(login, password, profileType, fullName, address, null));
            }
        }
    }

    //Google maps callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/*
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                // TODO: google map closes immediatly
                StringBuilder stBuilder = new StringBuilder();
                Place place = PlacePicker.getPlace(data, this);
                String placename = String.format("%s", place.getName());
                String address = String.format("%s", place.getAddress());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");

                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
//                tvPlaceDetails.setText(stBuilder.toString());
            }
        }
*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        System.out.println("Point changed");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("Connection failed");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    // Callbacks
    public void registerCallback(boolean success) {
        if (success) {
            showAlert("Register", "Success");
//            finish();
        } else {
            Log.e(RegisterActivity.class.getName(), "Register failed");
            showAlert("Register", "Failed");
//            mPasswordView.setError(getString(R.string.error_incorrect_password));
//            mPasswordView.requestFocus();
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        alertDialog = builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        alertDialog.hide();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
