package com.mobilemakers.contacts;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by salvador on 08/02/15.
 */
public class ContactAdapter extends ArrayAdapter<SignalContactData> {

    DatabaseHelper databaseHelper;
    List<SignalContactData> listContacts;

    public ContactAdapter(Context context, DatabaseHelper databaseHelper, List<SignalContactData> listContacts) {
        super(context,R.layout.templete_layout,listContacts);
        this.databaseHelper=databaseHelper;
        this.listContacts=listContacts;
    }

    @Override
    public void add(SignalContactData contact) {
        super.add(contact);
        try{
            Dao<SignalContactData,Integer> contactDao = databaseHelper.getContactDao();
            contactDao.create(contact);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
