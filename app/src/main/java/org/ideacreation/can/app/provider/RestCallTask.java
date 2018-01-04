package org.ideacreation.can.app.provider;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Register task
 */

public class RestCallTask<T> extends AsyncTask<Void, Void, T> {
    //    private final AuthContext authContext;
    private DataProvider dataProvider;
    private OnResultCallback<T> callback;
    private Call call;

    public RestCallTask(DataProvider dataProvider, Call<T> call, OnResultCallback<T> callback) {
//        this.authContext = authContext;
        this.dataProvider = dataProvider;
        this.callback = callback;
        this.call = call;
    }

    @Override
    protected T doInBackground(Void... voids) {
        Log.d(this.getClass().getName(), "---> doInBackground: start");
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Response<T> response = call.execute();

                Log.d(this.getClass().getName(), response.toString());

                return response.body();

            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
            } finally {
                Log.d(this.getClass().getName(), "<---- doInBackground: end");
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        Log.d(this.getClass().getName(), " onPostExecute - obj=" + result);
        if (callback != null) {
            callback.onAsyncResult(result);
        }
    }

    @Override
    protected void onCancelled() {

    }
}
