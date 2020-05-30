package com.gardyanakbar.guardianheadpaindiary.datadrivers;

import giantsweetroll.date.Date;

public class GraphSettings
{
    //Fields
    private Date dateFrom, dateTo;
    private String category;
    private int categoryIndex;
    private boolean showDataValues, showDataVoid, showDataPoints;

    //Constructor
    public GraphSettings()
    {
        this.dateFrom = new Date();
        this.dateTo = new Date();
        this.category = "";
        this.categoryIndex = 0;
        this.showDataPoints = false;
        this.showDataValues = false;
        this.showDataVoid = true;
    }

    //Setters and Getters
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public boolean isShowDataValues() {
        return showDataValues;
    }

    public void setShowDataValues(boolean showDataValues) {
        this.showDataValues = showDataValues;
    }

    public boolean isShowDataVoid() {
        return showDataVoid;
    }

    public void setShowDataVoid(boolean showDataVoid) {
        this.showDataVoid = showDataVoid;
    }

    public boolean isShowDataPoints() {
        return showDataPoints;
    }

    public void setShowDataPoints(boolean showDataPoints) {
        this.showDataPoints = showDataPoints;
    }
}
