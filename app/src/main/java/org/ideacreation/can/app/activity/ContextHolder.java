package org.ideacreation.can.app.activity;

import android.content.Context;

import org.ideacreation.can.app.activity.fragment.TabState;
import org.ideacreation.can.common.model.json.ApiResponse;

/**
 * Created by yas on 15.10.2017.
 */

public interface ContextHolder {
    Context getContext();

    void stateChanged(TabState currentState);

    void processErrorResponse(ApiResponse result);
}
