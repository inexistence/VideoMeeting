package com.jb.vmeeting.mvp.presenter;

import com.jb.vmeeting.mvp.model.biz.ILoginBiz;
import com.jb.vmeeting.mvp.model.biz.impl.LoginBiz;
import com.jb.vmeeting.mvp.model.eventbus.event.LoginEvent;
import com.jb.vmeeting.mvp.view.ILoginView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Jianbin on 16/3/14.
 */
public class LoginPresenter {
    ILoginView loginView;
    ILoginBiz loginBiz;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        loginBiz = new LoginBiz();
        EventBus.getDefault().register(this);
    }

    public void login() {
        loginView.preLogin();
        loginBiz.login(loginView.getUsername(), loginView.getPassword());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {
        if (loginEvent.success) {
            loginView.onLoginSuccess(loginEvent.user);
        } else {
            loginView.onLoginFailed(loginEvent.msg);
        }
    }

    /**
     * should call it when activity is about to be destroyed(Activity.onDestroy),
     * or memory leak may happens.
     *
     * Release obj here if necessary.
     */
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }
}
