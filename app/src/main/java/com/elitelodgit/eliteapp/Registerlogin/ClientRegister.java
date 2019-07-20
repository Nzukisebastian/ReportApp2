package com.elitelodgit.eliteapp.Registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.elitelodgit.eliteapp.R;

public class ClientRegister extends AppCompatActivity implements View.OnClickListener {
    EditText etemail,etpassword,etphone,etfullname;
    private Button buttonSubmit,buttonlogin;
    private ProgressDialog progressDialog;
    //URL to RegisterDevice.php
    private static final String URL_REGISTER_DEVICE = "http://ictchops.me.ke/FcmExample/RegisterDevice.php";

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);
        //VALIDATION OF THE FORM
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        etemail = (EditText) findViewById(R.id.etemail);
        etphone = (EditText) findViewById(R.id.etphone);
        etfullname = (EditText) findViewById(R.id.etname);
        etpassword = (EditText) findViewById(R.id.etpassword);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonlogin = (Button) findViewById(R.id.ilogin);


        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.etname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etemail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.etpassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.etphone, "^[0-9]{2}[0-9]{8}$", R.string.mobileerror);
        //adding listener to view
        buttonSubmit.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                   // startActivity(new Intent(this,ClientLogin.class));
                break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    startActivity(new Intent(this,InvestigatorRegister.class));
                break;
        }
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        etemail.setText(null);
        etphone.setText(null);
        etfullname .setText(null);
        etpassword.setText(null);
    }


    private void submitForm() {

        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            String email=etemail.getText().toString().trim().toLowerCase();
            String password=etpassword.getText().toString().trim().toLowerCase();
            String fullname=etfullname.getText().toString().trim().toLowerCase();
            String phone=etphone.getText().toString().trim().toLowerCase();
            String type="register";
            Backgroundtask backgroundtask=new Backgroundtask(this);
            backgroundtask.execute(type,email,password,phone,fullname);
            emptyInputEditText();
        }
    }

    private void login() {
        startActivity(new Intent(this,ClientLogins.class));

    }


    @Override
    public void onClick(View view) {
        if (view == buttonSubmit) {
            submitForm();
        }else if(view==buttonlogin){
            login();
        }
    }
}
