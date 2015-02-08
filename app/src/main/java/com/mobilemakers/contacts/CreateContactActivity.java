package com.mobilemakers.contacts;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by salvador on 07/02/15.
 */
public class CreateContactActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact_activity);
        CreateContactFragment createTaskFragment = new CreateContactFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_task, createTaskFragment).commit();

    }



}
