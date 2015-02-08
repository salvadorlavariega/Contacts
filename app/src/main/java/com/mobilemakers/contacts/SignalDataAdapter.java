package com.mobilemakers.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 6/02/15.
 */
public class SignalDataAdapter extends ArrayAdapter<SignalContactData> {

    Context context;
    List<SignalContactData> signaltaskData;
    public SignalDataAdapter(Context context,List<SignalContactData> signaltaskData ){
        super(context, R.layout.templete_layout,signaltaskData);
        this.context=context;
        this.signaltaskData=signaltaskData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView != null)
            rowView = convertView;
        else{
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.templete_layout,parent, false);
        }
        displayContentInRowiew(position,rowView);
        return  rowView;
    }

    private void displayContentInRowiew(int position, View rowView) {
        if(rowView != null){
            TextView textViewContactName = (TextView)rowView.findViewById(R.id.text_view_name_on_list);
            TextView textViewContactnickName = (TextView)rowView.findViewById(R.id.text_view_nikname_on_list);
            ImageView imageViewPictureContact = (ImageView)rowView.findViewById(R.id.image_view_contact_on_list);
            String completename = signaltaskData.get(position).getContactName() +" "+ signaltaskData.get(position).getContactLasName();
            String nickName =  signaltaskData.get(position).getContactNickName();
            textViewContactName.setText(completename);
            textViewContactnickName.setText(nickName);
            imageViewPictureContact.setImageBitmap(getImageBitmap(signaltaskData.get(position).getUriPicture()));

        }
    }

    private Bitmap getImageBitmap(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        return  BitmapFactory.decodeFile(path, options);

    }

}
