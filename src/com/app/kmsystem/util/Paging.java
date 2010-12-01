package com.app.kmsystem.util;

import java.io.Serializable;

/**
 * File: com.app.kmsystem.util.Paging.java
 * 
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 25, 2010, 11:36:32 AM
 */

public class Paging implements Serializable{
    private static final long serialVersionUID = 8565421748646229795L;
    private static final int INITED_CURRENT_PAGE = 1;
    private static final int DEFAULT_MAX_RESULT = 10;
    private static final int DEFAULT_PAGING_LINK = 10;
    private static final int PAGING_LINK_STYLE_STATIC = 0;
    private static final int PAGING_LINK_STYLE_DYNAMIC = 1;
    private boolean inited = false;
    private int currentPage = INITED_CURRENT_PAGE;
    private int maxResults = DEFAULT_MAX_RESULT;
    private int pagingLinks = DEFAULT_PAGING_LINK;
    private int pagingLinksStyle = PAGING_LINK_STYLE_STATIC;
    private int totalResults = 0;
    private int start = 0;
    private int end = 0;
    private int limit = 10;
    private boolean extPaging;
    private int totalPage;

    public Paging() {
        this(DEFAULT_MAX_RESULT, DEFAULT_PAGING_LINK, PAGING_LINK_STYLE_STATIC);
    }

    public Paging(int maxResults) {
        this(maxResults, DEFAULT_PAGING_LINK, PAGING_LINK_STYLE_STATIC);
    }

    public Paging(int maxResults, int pagingLinks, int pagingLinkStyle) {
        if(maxResults < 1){throw new IllegalArgumentException("maxResults must not smaller than 1 but " + maxResults);}
        if(pagingLinks < 1){throw new IllegalArgumentException("pagingLinks must not smaller than 1 but " + pagingLinks);}
        if(pagingLinkStyle != PAGING_LINK_STYLE_STATIC && pagingLinkStyle != PAGING_LINK_STYLE_DYNAMIC){
            throw new IllegalArgumentException
                    ("pagingLinkStyle must be " + PAGING_LINK_STYLE_STATIC + " or " + PAGING_LINK_STYLE_DYNAMIC + " but " + pagingLinkStyle);
        }
        this.maxResults = maxResults;
        this.pagingLinks = pagingLinks;
        this.pagingLinksStyle = pagingLinkStyle;
    }

    public int getFirstResult(){
        if(extPaging){return Math.max((getCurrentPage() - 1) * maxResults, 0);}
        else {return Math.max((getCurrentPage() - 1) * maxResults, 0);} 
    }

    public int getFirstRowNum(){
        return getFirstResult() + 1;
    }

    public int getLastRowNum(){
        return getFirstRowNum() + maxResults;
    }

    public int getFirstPage(){
        return Math.min(totalResults, 1);
    }

    public int getLastPage(){
        return (int) Math.ceil((double) totalResults / maxResults);
    }

    public int getCurrentResultNum(){
        return getTotalResults() - ((getCurrentPage() -1) * getMaxResults());
    }

    public int getStartPagingLink(){
        if(pagingLinksStyle == PAGING_LINK_STYLE_DYNAMIC){
            return Math.max(getCurrentPage() - pagingLinks, getFirstPage());
        }
        return (getCurrentPage() - 1) / pagingLinks * pagingLinks + 1;
    }

    public int getEndPagingLink(){
        if(pagingLinksStyle == PAGING_LINK_STYLE_DYNAMIC){
            return Math.min(getCurrentPage() - pagingLinks, getLastPage());
        }
        return Math.min(getStartPagingLink() + pagingLinks - 1, getLastPage());
    }

    public int getPrelinkPage(){
        return Math.min(getCurrentPage() - 1, getFirstPage());
    }

    public int getPostlinkPage(){
        return Math.min(getCurrentPage() + 1, getLastPage());
    }

    public int getCurrentPage() {
        if(inited){return Math.min(currentPage, getLastPage());}
        else return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 1){return;}
        this.currentPage = currentPage;
    }

    public int getMaxResults() {
        if(extPaging){return getLimit();}
        else return maxResults;
    }

    public void setMaxResults(int maxResults) {
        if(maxResults < 1){return;}
        this.maxResults = maxResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        this.inited = true;
    }

    public int getPagingLinks() {
        return pagingLinks;
    }

    public void setPagingLinks(int pagingLinks) {
        if(pagingLinks < 1){return;}
        this.pagingLinks = pagingLinks;
    }

    public int getPagingLinksStyle() {
        return pagingLinksStyle;
    }

    public void setPagingLinksStyle(int pagingLinksStyle) {
        if(pagingLinksStyle != PAGING_LINK_STYLE_STATIC && pagingLinksStyle != PAGING_LINK_STYLE_DYNAMIC){return;}
        this.pagingLinksStyle = pagingLinksStyle;
    }

    public boolean isInited() {
        return inited;
    }

    public void setInited(boolean inited) {
        this.inited = inited;
    }

    @Override
    public String toString(){
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "(firstPage=" + "'" + getFirstPage() + "'" + ", " +
                "lastPage=" + "'" + getLastPage() + "'" + ", " +
                "currentPage=" + "'" + getCurrentPage() + "'" + ", " +
                "startPagingLink=" + "'" + getStartPagingLink() + "'" + ", " +
                "endPagingLink=" + "'" + getEndPagingLink() + "')";
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isExtPaging() {
        return extPaging;
    }

    public void setExtPaging(boolean extPaging) {
        this.extPaging = extPaging;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int rows) {
        if(rows == 0) rows = 10;
        if(getTotalResults() > 0){
            if(getTotalResults() % rows > 0){totalPage = (getTotalResults() / rows) + 1;}
            else {totalPage = (getTotalResults() / rows);}
        } else {totalPage = 1;}
    }

    public int getFirstResult2(){
        if(getTotalResults() > 0){return ((getCurrentPage() - 1) * 10) + 1;}
        else {return 0;}
    }

    public int getLastResult(){
        int lastResult = getFirstResult() + 10;
        if(lastResult > getTotalResults()){return getTotalResults();}
        else {return lastResult;}
    }
}
