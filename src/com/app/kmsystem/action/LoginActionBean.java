package com.app.kmsystem.action;

import com.app.kmsystem.action.util.BaseActionBean;
import com.app.kmsystem.util.PageConstants;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * File: com.app.kmsystem.action.LoginActionBean.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Dec 2, 2010, 10:58:02 AM
 */

public class LoginActionBean extends BaseActionBean{
    private static final String LOGIN_PAGE = PageConstants.LOGIN.LOGIN_PAGE;

    @Override
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(LOGIN_PAGE);
    }

    public Resolution doLogin(){
        return null;
    }
}
