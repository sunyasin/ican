package org.ideacreation.can.app.security;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.ideacreation.can.app.provider.AuthHttpProvider;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.OwnProfileInfo;
import org.ideacreation.can.common.model.json.RegistrationInfo;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.id.message;

/**
 * Register task
 */

public class RegisterTask extends AsyncTask<Void, Void, Boolean> {
    private final AuthContext authContext;
    private final RegistrationInfo registrationInfo;

    RegisterTask(AuthContext authContext, RegistrationInfo registrationInfo) {
        this.authContext = authContext;
        this.registrationInfo = registrationInfo;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i(AuthHttpProvider.class.getName(), "Try to register user. Message: " + message);
        Call<ApiResponse<OwnProfileInfo>> loginCall = authContext.authHttpProvider.register(registrationInfo);
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Response<ApiResponse<OwnProfileInfo>> response = loginCall.execute();
                Log.i(AuthHttpProvider.class.getName(), response.toString());
                if (response.body() != null && response.isSuccessful()) { //body().getResult().getId() != null) {
                    OwnProfileInfo profile = response.body().getResult();
                    //authContext.setjSessionToken(response.body().getToken());
                    Log.i(AuthHttpProvider.class.getName(), "Register success");
                    Log.i(AuthHttpProvider.class.getName(), "got own profile " + profile);
                    return true;
                } else {
//                    authContext.setAuthPrincipal(null);
                    return false;
                }
            }
        } catch (IOException e) {
            Log.e(AuthHttpProvider.class.getName(), e.getMessage(), e);
            return false;
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        authContext.stopRegister();
        authContext.sendRegisterCallback(success);
    }

    @Override
    protected void onCancelled() {
        authContext.stopRegister();
    }
}
