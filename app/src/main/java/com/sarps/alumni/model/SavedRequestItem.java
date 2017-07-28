package com.sarps.alumni.model;

/**
 * Created by Sarps on 2/25/2017.
 */
public class SavedRequestItem {
    String img,name,course,req_amt,desc,stud_id,heading,number;


    public SavedRequestItem(String desc, String heading, String req_amt, String number) {
        this.desc = desc;
        this.heading = heading;
        this.req_amt = req_amt;
        this.number = number;
    }

    public SavedRequestItem(String img, String name, String course, String req_amt, String desc, String stud_id, String heading) {
        this.img = img;
        this.name = name;
        this.course = course;
        this.req_amt = req_amt;
        this.desc = desc;
        this.stud_id = stud_id;
        this.heading = heading;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getReq_amt() {
        return req_amt;
    }

    public void setReq_amt(String req_amt) {
        this.req_amt = req_amt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
