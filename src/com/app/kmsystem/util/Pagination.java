package com.app.kmsystem.util;

/**
 * File: com.app.kmsystem.util.Pagination.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 25, 2010, 2:00:03 PM
 */

public class Pagination {
    private int start;
    private int end;
    private int page;
    private int records;
    private int rows;

    public Pagination(int start, int end) {
        this.setStart(start);
        this.setEnd(end);
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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
