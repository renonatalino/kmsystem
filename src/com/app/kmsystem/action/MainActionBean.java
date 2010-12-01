package com.app.kmsystem.action;

import com.app.kmsystem.action.util.BaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * File: com.app.kmsystem.action.MainActionBean.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Dec 1, 2010, 5:02:07 PM
 */

public class MainActionBean extends BaseActionBean{
    private static final String MAIN_PAGE = "";

    @Override
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(MAIN_PAGE);
    }

    public Resolution doLoadData(){
        return null;
    }
}
