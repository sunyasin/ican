package org.ideacreation.can.di;

import org.ideacreation.can.app.activity.GroupContentActivity;
import org.ideacreation.can.app.activity.LoginActivity;
import org.ideacreation.can.app.activity.RegisterActivity;
import org.ideacreation.can.app.activity.fragment.CommonStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaGroupedStateFragment;
import org.ideacreation.can.app.activity.fragment.LentaTabFragment;
import org.ideacreation.can.app.security.AuthContext;
import org.ideacreation.can.app.service.MainTabsService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dependency injection component
 */

@Singleton
@Component(modules = {RootModule.class})
public interface DiComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(AuthContext authContext);

    void inject(MainTabsService mainTabsService);

    void inject(GroupContentActivity groupContentActivity);

    void inject(CommonStateFragment commonStateFragment);

    void inject(LentaTabFragment lentaMainStateFragment);

    void inject(LentaGroupedStateFragment lentaGroupedStateFragment);
}
