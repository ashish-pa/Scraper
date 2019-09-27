package com.daobeans;

public class MongoBean {
    private String _id;
    private String showLinks;
    private String showName;
    private String showDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getShowLinks() {
        return showLinks;
    }

    public void setShowLinks(String showLinks) {
        this.showLinks = showLinks;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public MongoBean(){
        this._id = "";
        this.showDate = "";
        this.showLinks = "";
        this.showName = "";
    }

    public MongoBean(String _id, String showDate, String showLinks, String showName){
        this._id = _id;
        this.showDate = showDate;
        this.showLinks = showLinks;
        this.showName = showName;
    }
}
