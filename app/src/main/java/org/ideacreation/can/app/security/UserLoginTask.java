package org.ideacreation.can.app.security;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.ideacreation.can.app.provider.AuthHttpProvider;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.AuthPrincipal;
import org.ideacreation.can.common.model.json.Token;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.id.message;

/**
 * AuthTask
 */

class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final AuthContext authContext;
    private final AuthPrincipal authPrincipal;

    UserLoginTask(AuthContext authContext, AuthPrincipal authPrincipal) {
        this.authContext = authContext;
        this.authPrincipal = authPrincipal;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i(AuthHttpProvider.class.getName(), "Try to send message. Message: " + message);
        Call<ApiResponse<Token>> loginCall = authContext.authHttpProvider.login(authPrincipal);
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Response<ApiResponse<Token>> response = loginCall.execute();
                Log.i(AuthHttpProvider.class.getName(), response.toString());
                if (response.body() != null && response.body().getResult().getToken() != null) {
                    authContext.setjSessionToken(response.body().getResult().getToken());
                    return true;
                } else {
                    authContext.setAuthPrincipal(null);
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
        authContext.stopAuth();
        authContext.sendAuthCallback(success);
    }

    @Override
    protected void onCancelled() {
        authContext.stopAuth();
    }
}
