package com.app.kmsystem.action.util;

import net.sourceforge.stripes.action.ActionBeanContext;
import javax.servlet.http.HttpSession;

/**
 * File: com.app.kmsystem.action.util.BaseActionBeanContext.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Dec 1, 2010, 5:00:39 PM
 */

public class BaseActionBeanContext extends ActionBeanContext {
    public HttpSession getSession() {
		return getRequest().getSession();
	}
}
