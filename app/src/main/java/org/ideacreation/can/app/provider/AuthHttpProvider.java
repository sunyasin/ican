package org.ideacreation.can.app.provider;


import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.AuthPrincipal;
import org.ideacreation.can.common.model.json.GroupInfo;
import org.ideacreation.can.common.model.json.OwnProfileInfo;
import org.ideacreation.can.common.model.json.RegistrationInfo;
import org.ideacreation.can.common.model.json.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by haspel on 10/1/17.
 */

public interface AuthHttpProvider {
    @POST("/backend/api/login")
    Call<ApiResponse<Token>> login(@Body AuthPrincipal authPrincipal);

    @POST("/backend/api/registration")
    Call<ApiResponse<OwnProfileInfo>> register(@Body RegistrationInfo registrationInfo);

    @GET("/api/grouped")
    Call<ApiResponse<List<GroupInfo>>> grouped();

}
