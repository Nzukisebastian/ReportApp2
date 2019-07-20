package com.elitelodgit.eliteapp.UserTasks;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elitelodgit.eliteapp.ClientReports.Client_Reports_Screen;
import com.elitelodgit.eliteapp.CompanyProfile.Company_Profile_Screen;
import com.elitelodgit.eliteapp.CompanyReports.Company_Reports_Screen;
import com.elitelodgit.eliteapp.MainActivity;
import com.elitelodgit.eliteapp.Registerlogin.InvestigatorLogin;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
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

public class InvestigatorForms extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;
    private String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}; // List of permissions required};

    private final String TAG=this.getClass().getName();
    ImageView ivcamera,ivgallery,ivimage;
    GalleryPhoto galleryPhoto;
    CameraPhoto cameraPhoto;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1213;
    final int CAMERA_REQUEST=13323;
    final int GALLERY_REQUEST=22131;
    String selectedphoto;
    Button upload;
    String email_id;
    TextView exec,evidence,findings,scene,document,subject,observation,liability,recommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_forms);
        Intent intent=getIntent();
        email_id=intent.getExtras().getString("emailid");


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        cameraPhoto=new CameraPhoto(getApplicationContext());
        galleryPhoto=new GalleryPhoto(getApplicationContext());
        ivcamera=(ImageView)findViewById(R.id.ivcamera);
        ivgallery=(ImageView)findViewById(R.id.ivgallery);
        ivimage=(ImageView)findViewById(R.id.ivimage);
        exec=(TextView)findViewById(R.id.etexec);
        findings=(TextView)findViewById(R.id.etfindings);
        evidence=(TextView)findViewById(R.id.etevidence);
        document=(TextView)findViewById(R.id.etdocument);
        observation=(TextView)findViewById(R.id.etobservation);
        liability=(TextView)findViewById(R.id.etliability);
        recommendation =(TextView)findViewById(R.id.etrecommendation);
        scene=(TextView)findViewById(R.id.etscene);
        subject=(TextView)findViewById(R.id.etsubject);
        upload=(Button) findViewById(R.id.etupload);

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


                    String vexec=exec.getText().toString();
                    String vevidence=evidence.getText().toString();
                    String vfindings=findings.getText().toString();
                    String vscene=scene.getText().toString();
                    String vdocument=document.getText().toString();
                    String vsubject=subject.getText().toString();
                    String vobservation=observation.getText().toString();
                    String vliability=liability.getText().toString();
                    String vrecommendation=recommendation.getText().toString();


                    Bitmap bitmap= ImageLoader.init().from(selectedphoto).requestSize(1024,1024).getBitmap();
                    String encodeimage= ImageBase64.encode(bitmap);
                    Log.d(TAG,encodeimage);
                    HashMap<String,String> postData=new HashMap<String, String>();
                    postData.put("image",encodeimage);
                    postData.put("excutive",vexec);
                    postData.put("evidence",vevidence);
                    postData.put("findings",vfindings);
                    postData.put("scene",vscene);
                    postData.put("document",vdocument);
                    postData.put("subject",vsubject);
                    postData.put("observation",vobservation);
                    postData.put("liability",vliability);
                    postData.put("recommendation",vrecommendation);
                    emptyInputEditText();

                    PostResponseAsyncTask task=new PostResponseAsyncTask(InvestigatorForms.this,postData, new AsyncResponse() {
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
                    task.execute("http://ictchops.me.ke/imageupload/eliteinvestigatorform.php?emails="+email_id);
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
        inflater.inflate(R.menu.companymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        NestedScrollView main_view=(NestedScrollView) findViewById(R.id.company_view);
        switch(item.getItemId()){
            case R.id.menu_companylogout:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent found = new Intent(this,InvestigatorLogin.class);
                startActivity(found);
                //main_view.setBackgroundColor(Color.RED);
                return true;

                case R.id.menu_companyprofile:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent profile = new Intent(this,Company_Profile_Screen.class);
                profile.putExtra("emailid",email_id);
                startActivity(profile);
                //main_view.setBackgroundColor(Color.RED);
                return true;

            case R.id.menu_companyreports:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent reports = new Intent(this,Company_Reports_Screen.class);
                reports.putExtra("emailid",email_id);
                startActivity(reports);
                //main_view.setBackgroundColor(Color.RED);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        exec.setText(null);
        evidence.setText(null);
        findings.setText(null);
        scene.setText(null);
        document.setText(null);
        observation.setText(null);
        liability.setText(null);
        recommendation.setText(null);
    }


    //multiple permissions
    private void requestPermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
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

    //camera permissions

    //multiple permissions
    private void requestPermissionsWithCamera(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {openMultipleThingsWithCamera();}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {}
        }).check();
    }

    private void openMultipleThingsWithCamera(){

        try {

            startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"something wrong while taking photos",Toast.LENGTH_LONG);
        }
        //Toast.makeText(this,"You can implement action",Toast.LENGTH_LONG).show();

    }


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
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"something wrong while taking photos",Toast.LENGTH_LONG);
        }

    }

    private void closeApp(){
        finish();
        System.exit(0);
    }




}
