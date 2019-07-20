package com.elitelodgit.eliteapp.Registerlogin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

import com.elitelodgit.eliteapp.ClientReports.Client_Reports_Screen;
import com.elitelodgit.eliteapp.GooglePlaceApi.AutoCompleteAdapter;
import com.elitelodgit.eliteapp.ClientProfile.ShowProfileScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.elitelodgit.eliteapp.R;

public class ClientForm extends AppCompatActivity implements View.OnClickListener{

    EditText selectDate,selectTime;
    private int mYear,mMonth,mDay,mHour,mMinute;

    private final String TAG=this.getClass().getName();

    public static final int MULTIPLE_PERMISSIONS = 10;
    private String[] permissions= new String[]{Manifest.permission.CAMERA};
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    ImageView ivcamera,ivgallery,ivimage;
    GalleryPhoto galleryPhoto;
    CameraPhoto cameraPhoto;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1213;
    final int CAMERA_REQUEST=13323;
    final int GALLERY_REQUEST=22131;
    String selectedphoto;
    String email_id;
    Button upload;
    TextView nature,place,parties,insurance,policy,conclusion;
    private Calendar calendar;
    private String format = "";
    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter adapter;
    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);

        checkPermissions();

        calendar = Calendar.getInstance();
        Intent intent=getIntent();
        email_id=intent.getExtras().getString("emailid");
        cameraPhoto=new CameraPhoto(getApplicationContext());
        galleryPhoto=new GalleryPhoto(getApplicationContext());
        ivcamera=(ImageView)findViewById(R.id.ivcamera);
        ivgallery=(ImageView)findViewById(R.id.ivgallery);
        ivimage=(ImageView)findViewById(R.id.ivimage);
        nature=(TextView)findViewById(R.id.etnature);
        parties=(TextView)findViewById(R.id.etparties);
        insurance =(TextView)findViewById(R.id.etinsurance);
        policy=(TextView)findViewById(R.id.etpolicy);
        conclusion=(TextView)findViewById(R.id.etbrief);
        upload=(Button) findViewById(R.id.etupload);
        place=(TextView)findViewById(R.id.etplace);
        //showTime(hour, min);
        selectDate=(EditText)findViewById(R.id.etdate);
        selectTime=(EditText)findViewById(R.id.ettime);
        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);


        String apiKey = "AIzaSyCBQMhFdSCpppvnS2tgiBfz4xcbr6Gz0ho";
        if(apiKey.isEmpty()){
            place.setText(getString(R.string.error));
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextView();


        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                        cameraPhoto.addToGallery();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"something wrong while taking photos",Toast.LENGTH_LONG);
                    }
            }
        });
        ivgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedphoto==null || selectedphoto.equals("")){
                    Toast.makeText(getApplicationContext(),"choose an image",Toast.LENGTH_LONG).show();
                    return;
                }
                try {

                    String vnature=nature.getText().toString();
                    String vdate=selectDate.getText().toString();
                    String vtime=selectTime.getText().toString();
                    String vplace=place.getText().toString();
                    String vparty=parties.getText().toString();
                    String vinsurance=insurance.getText().toString();
                    String vpolicy=policy.getText().toString();
                    String vconclusion=conclusion.getText().toString();



                    Bitmap bitmap= ImageLoader.init().from(selectedphoto).requestSize(1024,1024).getBitmap();
                    String encodeimage= ImageBase64.encode(bitmap);
                    Log.d(TAG,encodeimage);
                    HashMap<String,String> postData=new HashMap<String, String>();
                    postData.put("image",encodeimage);
                    postData.put("nature",vnature);
                    postData.put("date",vdate);
                    postData.put("time",vtime);
                    postData.put("place",vplace);
                    postData.put("parties",vparty);
                    postData.put("insurance",vinsurance);
                    postData.put("policy",vpolicy);
                    postData.put("conclusion",vconclusion);
                    emptyInputEditText();

                    PostResponseAsyncTask task=new PostResponseAsyncTask(ClientForm.this,postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("Successfully Uploaded")){
                                Toast.makeText(getApplicationContext(),"Sent successfully",Toast.LENGTH_LONG).show();

                            }else if(s.contains("not Uploaded")){
                                Toast.makeText(getApplicationContext(),"Image not uploaded",Toast.LENGTH_LONG).show();
                            }else if(s==null){
                                Toast.makeText(getApplicationContext(),"Unable to connect to server",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    task.execute("http://ictchops.me.ke/imageupload/eliteclientform.php?emails="+email_id);
                    task.setEachExceptionsHandler(new EachExceptionsHandler() {
                        @Override
                        public void handleIOException(IOException e) {
                            Toast.makeText(getApplicationContext(),"cannot connect to server",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void handleMalformedURLException(MalformedURLException e) {
                            Toast.makeText(getApplicationContext(),"URL Error",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void handleProtocolException(ProtocolException e) {
                            Toast.makeText(getApplicationContext(),"Protocal Error",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(),"Encoding Error",Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"something wrong while encoding photos",Toast.LENGTH_LONG);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

        if (view == selectDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == selectTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            selectTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK){
            if(requestCode==CAMERA_REQUEST){
                String photoPath=cameraPhoto.getPhotoPath();
                selectedphoto=photoPath;
                Bitmap bitmap=null;
                try {
                    bitmap= ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    ivimage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"something wrong while loading photos",Toast.LENGTH_LONG);
                }

            }
            else if(requestCode==GALLERY_REQUEST){
                Uri uri=data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedphoto=photoPath;
                try {
                    Bitmap bitmap= ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    ivimage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"something wrong while choosing photos",Toast.LENGTH_LONG);
                }

            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        NestedScrollView main_view=(NestedScrollView) findViewById(R.id.main_view);
        switch(item.getItemId()){
            case R.id.menu_logout:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent logout = new Intent(this, ClientLogins.class);
                startActivity(logout);
                //main_view.setBackgroundColor(Color.RED);
                return true;
            case R.id.menu_profile:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent profile = new Intent(this, ShowProfileScreen.class);
                profile.putExtra("emailid",email_id);
                startActivity(profile);
                //main_view.setBackgroundColor(Color.RED);
                return true;

            case R.id.menu_clientreports:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent clientreport = new Intent(this, Client_Reports_Screen.class);
                clientreport.putExtra("emailid",email_id);
                startActivity(clientreport);
                //main_view.setBackgroundColor(Color.RED);
                return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }

// permissions for camera
    private  boolean checkPermissions() {

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                        }
                    }
                    //Toasty.makeText(getApplicationContext(),permissionsDenied,Toasty.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        nature.setText(null);
        //datepicker.setText(null);
        selectDate.setText(null);
        selectTime.setText(null);
        place.setText(null);
        parties.setText(null);
        insurance.setText(null);
        policy.setText(null);
        conclusion.setText(null);
    }


    // multiple permissions for gallery
    private void requestPermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {openMultipleThings();}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {}
        }).check();
    }

    private void openMultipleThings(){

        startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
        //Toast.makeText(this,"You can implement action",Toast.LENGTH_LONG).show();

    }


    private void initAutoCompleteTextView() {

        autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this, placesClient);
        autoCompleteTextView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                            place.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            place.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    /*private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) getBaseContext(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        try{
                            startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                            cameraPhoto.addToGallery();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"something wrong while taking photos",Toast.LENGTH_LONG);
                        }
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale((Activity) getBaseContext(), Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale((Activity) getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(getApplicationContext(), "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }






    //camera permissions
    private void requestcameraPermissions(){
        Dexter.withActivity(this)

                .withPermission(Manifest.permission.CAMERA)

                .withListener(new PermissionListener() {

                    @Override

                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        // the user has granted the permission so you can open the camera

                        openCamera();

                    }

                    @Override

                    public void onPermissionDenied(PermissionDeniedResponse response) {


                        if (response.isPermanentlyDenied()) {

                            // here user permanently denied the permission so you can close the app

                            closeApp();

                        }

                    }

                    @Override

                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();

                    }

                }).check();
    }

    private void openCamera(){
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
            //cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"something wrong while taking photos",Toast.LENGTH_LONG);
        }

    }

    private void closeApp(){
        finish();
        System.exit(0);
    }

    */

}
