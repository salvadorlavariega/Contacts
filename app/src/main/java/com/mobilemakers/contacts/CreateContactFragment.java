package com.mobilemakers.contacts;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateContactFragment extends Fragment {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
     public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "contacts_pictures";
    private static final int RESULT_OK=-1;
    private static  final int RESULT_CANCELED=0;

    private final static String LOG_TAG=CreateContactFragment.class.getSimpleName();

    public static final String KEY_NAME = "key_name";
    public static final String KEY_LAST_NAME = "key_last_name";
    public static final String KEY_NICKNAME = "key_nick_name";
    public static final String KEY_URI_PICTURE = "key_uri_picture";

    EditText editTextContacName;
    EditText editTextLastName;
    EditText editTextNickName;
    Button buttonAddContact;
    Button buttonTakePicture;
    Uri uriPicture;
    ImageView previewPicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_fragment,container,false);
        prepareWidgets(rootView);
        prepareButton(rootView);
        return rootView;
    }

    private void prepareWidgets(View rootView ) {
        editTextContacName = (EditText)rootView.findViewById(R.id.edit_text_new_contact_name);
        editTextLastName = (EditText)rootView.findViewById(R.id.edit_text_new_contact_last_name);
        editTextNickName = (EditText)rootView.findViewById(R.id.edit_text_new_contact_nick_name);

        previewPicture = (ImageView)rootView.findViewById(R.id.image_pre);
    }

    private void prepareButton(View rootView ) {
        buttonAddContact = (Button)rootView.findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View v) {
                                                if(validateFields()) {
                                                    Log.i(LOG_TAG, "Antes del clic");
                                                    Intent intent = new Intent();
                                                    intent.putExtra(KEY_NAME, editTextContacName.getText().toString().trim());
                                                    intent.putExtra(KEY_LAST_NAME, editTextLastName.getText().toString().trim());
                                                    intent.putExtra(KEY_NICKNAME, editTextNickName.getText().toString().trim());
                                                    intent.putExtra(KEY_URI_PICTURE, uriPicture.getPath());
                                                    getActivity().setResult(Activity.RESULT_OK, intent);
                                                    getActivity().finish();
                                                }
                                             }
                                         }

        );

        buttonTakePicture = (Button)rootView.findViewById(R.id.button_take_picture);
        buttonTakePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

private boolean validateFields(){
    if(editTextContacName.getText().toString().trim().isEmpty()){
        Toast.makeText(getActivity(),
                "Please fill the Name field", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    if(editTextLastName.getText().toString().trim().isEmpty()){
        Toast.makeText(getActivity(),
                "Please fill the Last Name field", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    if(editTextNickName.getText().toString().trim().isEmpty()){
        Toast.makeText(getActivity(),
                "Please fill the NickName field", Toast.LENGTH_SHORT)
                .show();
        return false;
    }
    if(uriPicture==null){
        Toast.makeText(getActivity(),
                "Please take a picture", Toast.LENGTH_SHORT)
                .show();
        return false;
    }
    return true;
}
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        uriPicture = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPicture);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }


    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image

       // Toast.makeText(getActivity(), "resultCode camara="+resultCode+"", Toast.LENGTH_LONG).show();
        Log.i(CreateContactFragment.class.getSimpleName(),"Resultado camara="+requestCode+"");
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {


            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                previewPicture.setVisibility(View.INVISIBLE);

                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                previewPicture.setVisibility(View.INVISIBLE);
                // failed to capture image
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


   private void previewCapturedImage() {
        try {
            // hide video preview


            previewPicture.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 1;

            final Bitmap bitmap = BitmapFactory.decodeFile(uriPicture.getPath(),
                    options);

            previewPicture.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
