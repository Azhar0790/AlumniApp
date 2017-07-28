package com.sarps.alumni.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nitish on 9/29/2016.
 */
public class User implements Parcelable {


    public User()
    {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String name;
    String email;
    String profile_img;
    String type;

    public User(String name, String email, String profile_img, String type) {
        this.name = name;
        this.email = email;
        this.profile_img = profile_img;
        this.type = type;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        profile_img = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(profile_img);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profile_img='" + profile_img + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
