package com.app.kmsystem.action.util;

import com.app.kmsystem.util.JqGridUtil;
import com.app.kmsystem.util.Paging;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * File: com.app.kmsystem.action.util.BaseActionBean.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Dec 1, 2010, 5:01:29 PM
 */

public abstract class BaseActionBean implements ActionBean{
    protected BaseActionBeanContext context;

    protected JqGridUtil jqGrid;
    protected Paging paging;

    private int start;
	private int limit;

    private int page;
	private int records;
	private int total;
	private int rows;

    public BaseActionBean() {
        if(paging == null){paging = new Paging();}
        paging.setExtPaging(true);
    }

    public void prepareView() {} // no implementation by default

    @DefaultHandler
    abstract public Resolution show();

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        paging.setStart(start);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        paging.setLimit(limit);
    }

    public JqGridUtil getJqGrid() {
        return jqGrid;
    }

    public void setJqGrid(JqGridUtil jqGrid) {
        this.jqGrid = jqGrid;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public HttpServletRequest getRequest() {
        return getContext().getRequest();
    }

    public HttpServletResponse getResponse(){
		return getContext().getResponse();
	}

    public BaseActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = (BaseActionBeanContext) context;
    }

    protected String getRequestParameter(String parameterName,
			String defaultValue) {
		HttpServletRequest request = context.getRequest();
		String value = request.getParameter(parameterName);
		if (value != null) {
			return value;
		}
		return defaultValue;
	}

	protected int getRequestParameter(String parameterName, int defaultValue) {
		HttpServletRequest request = context.getRequest();
		String value = request.getParameter(parameterName);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				// ignore
			}
		}
		return defaultValue;
	}

	protected double getRequestParameter(String parameterName,
			double defaultValue) {
		HttpServletRequest request = context.getRequest();
		String value = request.getParameter(parameterName);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {} // ignore
		}
		return defaultValue;
	}

	public boolean getRequestParameter(String parameterName,
			boolean defaultValue) {
		HttpServletRequest request = context.getRequest();
		String value = request.getParameter(parameterName);
		if (value != null) {
			return Boolean.parseBoolean(value);
		}
		return defaultValue;
	}

    public Resolution getListUploadedFile(){return null;}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
