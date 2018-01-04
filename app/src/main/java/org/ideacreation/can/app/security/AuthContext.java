package org.ideacreation.can.app.security;

import org.ideacreation.can.App;
import org.ideacreation.can.app.activity.LoginActivity;
import org.ideacreation.can.app.activity.RegisterActivity;
import org.ideacreation.can.app.provider.AuthHttpProvider;
import org.ideacreation.can.common.model.json.AuthPrincipal;
import org.ideacreation.can.common.model.json.RegistrationInfo;

import javax.inject.Inject;

/**
 * Authorization context
 */

public class AuthContext {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private RegisterTask mRegTask = null;
    private LoginActivity loginActivity = null;
    private RegisterActivity registerActivity = null;
    private AuthPrincipal authPrincipal = null;
    private String jSessionToken = null;

    AuthHttpProvider authHttpProvider;

    public AuthContext() {
        App.getInjector().inject(this);
    }

    @Inject
    void setAuthHttpProvider(AuthHttpProvider authHttpProvider) {
        this.authHttpProvider = authHttpProvider;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(String email, String password) {
        authPrincipal = new AuthPrincipal(email, password);
        mAuthTask = new UserLoginTask(this, authPrincipal);
        mAuthTask.execute((Void) null);
    }

    public void doRegister(RegistrationInfo registerRequest) {
        mRegTask = new RegisterTask(this, registerRequest);
        mRegTask.execute((Void[]) null);
    }

    // Util methods

    /**
     * Check active login task
     *
     * @return has active login
     */
    public boolean hasActiveLoginTask() {
        return mAuthTask != null;
    }

    /**
     * Check active register task
     *
     * @return has active registration
     */
    public boolean hasActiveRegisterTask() {
        return mRegTask != null;
    }

    /**
     * Get current JSESSIONTOKEN
     *
     * @return JSESSIONTOKEN
     */
    public String getjSessionToken() {
        return jSessionToken;
    }

    /**
     * Set JSESSIONTOKEN
     *
     * @param jSessionToken JSESSIONTOKEN
     */
    void setjSessionToken(String jSessionToken) {
        this.jSessionToken = jSessionToken;
    }

    /**
     * Is authorized
     *
     * @return is authorized
     */
    public boolean isAuthorized() {
        return jSessionToken != null;
    }

    /**
     * Get user info
     *
     * @return {@link AuthPrincipal}
     */
    public AuthPrincipal getAuthPrincipal() {
        return authPrincipal;
    }

    /**
     * Set user info
     *
     * @param authPrincipal {@link AuthPrincipal}
     */
    void setAuthPrincipal(AuthPrincipal authPrincipal) {
        this.authPrincipal = authPrincipal;
    }

    // Activity injection

    /**
     * Inject login activity
     *
     * @param loginActivity - {@link LoginActivity}
     */
    public void injectLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    /**
     * Inject register activity
     *
     * @param registerActivity - {@link RegisterActivity}
     */
    public void injectRegisterActivity(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    // Callbacks

    /**
     * Stop authorization
     */
    void stopAuth() {
        mAuthTask = null;
        loginActivity.showProgress(false);
    }

    /**
     * Stop registration
     */
    void stopRegister() {
        mRegTask = null;
        registerActivity.showProgress(false);
    }


    /**
     * Send auth callback to login activity
     *
     * @param success - result
     */
    void sendAuthCallback(Boolean success) {
        loginActivity.authCallback(success);
    }

    /**
     * Send register callback to login activity
     *
     * @param success - result
     */
    void sendRegisterCallback(Boolean success) {
        registerActivity.registerCallback(success);
    }
}
