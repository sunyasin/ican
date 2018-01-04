package org.ideacreation.can.di;

import org.ideacreation.can.Constants;
import org.ideacreation.can.app.provider.AuthHttpProvider;
import org.ideacreation.can.app.provider.DataProvider;
import org.ideacreation.can.app.security.AuthContext;
import org.ideacreation.can.app.service.MainTabsService;
import org.ideacreation.can.app.validator.PasswordValidator;
import org.ideacreation.can.app.validator.PhoneValidator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Root dagger2 module
 */

@Module
public class RootModule {

    @Provides
    @Singleton
    public AuthContext getAuthContext() {
        return new AuthContext();
    }

    @Provides
    @Singleton
    public PhoneValidator getEmailValidator() {
        return new PhoneValidator();
    }

    @Provides
    @Singleton
    public PasswordValidator getPasswordValidator() {
        return new PasswordValidator();
    }

    @Provides
    @Singleton
    public MainTabsService getMainTabService() {
        return new MainTabsService();
    }

    // Http injections
    @Provides
    @Singleton
    public AuthHttpProvider getAuthHttpProvider() {
        return getRetrofit().create(AuthHttpProvider.class);
    }

    @Provides
    @Singleton
    public DataProvider getDataProvider() {
        return getRetrofit().create(DataProvider.class);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BACK_URL)
                .addConverterFactory(JacksonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}

