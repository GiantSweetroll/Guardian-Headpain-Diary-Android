package com.gardyanakbar.guardianheadpaindiary.datadrivers;

import giantsweetroll.date.Date;

public class TableSettings
{
    //Fields
    private Date dateFrom, dateTo;
    private String filterCategory, filterKeyword;
    private int filterCategoryIndex;

    //Constructor
    public TableSettings()
    {
        this.dateFrom = new Date();
        this.dateTo = new Date();
        this.filterCategoryIndex = 0;
        this.filterCategory = "";
        this.filterKeyword = "";
    }

    //Setters and Getters
    public String getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(String filterCategory) {
        this.filterCategory = filterCategory;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getFilterCategoryIndex() {
        return filterCategoryIndex;
    }

    public void setFilterCategoryIndex(int filterCategoryIndex) {
        this.filterCategoryIndex = filterCategoryIndex;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }
}
