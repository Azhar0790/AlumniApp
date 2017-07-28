package com.sarps.alumni.model;

/**
 * Created by Sarps on 11/17/2016.
 */
public class Experience_item {
    private String eCollege,percent,from_year,is_continue,to_year;
    private String eCourse;


    public Experience_item(String eCollege, String percent, String from_year, String is_continue, String eCourse,String to_year) {
        this.eCollege = eCollege;
        this.percent = percent;
        this.from_year = from_year;
        this.is_continue = is_continue;
        this.eCourse = eCourse;
        this.to_year = to_year;
    }

    public Experience_item(String eCollege, String eCourse) {
        this.eCollege = eCollege;
        this.eCourse = eCourse;
    }

    public String getTo_year() {
        return to_year;
    }

    public void setTo_year(String to_year) {
        this.to_year = to_year;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getFrom_year() {
        return from_year;
    }

    public void setFrom_year(String from_year) {
        this.from_year = from_year;
    }

    public String getIs_continue() {
        return is_continue;
    }

    public void setIs_continue(String is_continue) {
        this.is_continue = is_continue;
    }

    public String geteCollege() {
        return eCollege;
    }

    public void seteCollege(String eCollege) {
        this.eCollege = eCollege;
    }

    public String geteCourse() {
        return eCourse;
    }

    public void seteCourse(String eCourse) {
        this.eCourse = eCourse;
    }

}
