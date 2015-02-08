package com.mobilemakers.contacts;

import android.net.Uri;


import com.j256.ormlite.field.DatabaseField;

import java.net.URI;

/**
 * Created by root on 6/02/15.
 */
public class SignalContactData {

    public final static String ID = "_ID";
    public final static String NAME = "Name";
    public final static String NICK_NAME = "Nick_Name";
    public final static String LAST_NAME = "Last_Name";
    public final static String URI_PICTURE = "Uri_Picture";
    public final static String CREATION_DATE = "Date";

    @DatabaseField(generatedId = true, columnName = ID) private int id_contact;
    @DatabaseField (columnName = NAME) private String contactName;
    @DatabaseField (columnName = LAST_NAME) private String contactLasName;
    @DatabaseField (columnName = NICK_NAME) private String contactNickName;
    @DatabaseField (columnName = URI_PICTURE) private String uriPicture;


    public SignalContactData(){
     }



    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public String getUriPicture() {
        return uriPicture;
    }

    public void setUriPicture(String uriPicture) {
        this.uriPicture = uriPicture;
    }
    public String getContactLasName() {
        return contactLasName;
    }

    public void setContactLasName(String contactLasName) {
        this.contactLasName = contactLasName;
    }

    public String getContactNickName() {
        return contactNickName;
    }

    public void setContactNickName(String contactNickName) {
        this.contactNickName = contactNickName;
    }



}
