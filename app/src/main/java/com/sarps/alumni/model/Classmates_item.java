package com.sarps.alumni.model;

/**
 * Created by Sarps on 12/1/2016.
 */
public class Classmates_item {
    String u_id,e_college,e_course,from_year,percent,u_fname,u_image,u_lname;

    public Classmates_item(String u_id, String e_course, String from_year, String u_fname, String u_lname, String u_image) {
        this.u_id = u_id;
        this.e_course = e_course;
        this.from_year = from_year;
        this.u_fname = u_fname;
        this.u_lname = u_lname;
        this.u_image = u_image;
    }

    public Classmates_item(String u_id, String e_college, String e_course, String from_year, String percent, String u_fname, String u_image, String u_lname) {
        this.u_id = u_id;
        this.e_college = e_college;
        this.e_course = e_course;
        this.from_year = from_year;
        this.percent = percent;
        this.u_fname = u_fname;
        this.u_image = u_image;
        this.u_lname = u_lname;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getE_college() {
        return e_college;
    }

    public void setE_college(String e_college) {
        this.e_college = e_college;
    }

    public String getE_course() {
        return e_course;
    }

    public void setE_course(String e_course) {
        this.e_course = e_course;
    }

    public String getFrom_year() {
        return from_year;
    }

    public void setFrom_year(String from_year) {
        this.from_year = from_year;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getU_fname() {
        return u_fname;
    }

    public void setU_fname(String u_fname) {
        this.u_fname = u_fname;
    }

    public String getU_image() {
        return u_image;
    }

    public void setU_image(String u_image) {
        this.u_image = u_image;
    }

    public String getU_lname() {
        return u_lname;
    }

    public void setU_lname(String u_lname) {
        this.u_lname = u_lname;
    }
}
