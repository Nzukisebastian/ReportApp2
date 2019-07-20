package com.elitelodgit.eliteapp.Registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elitelodgit.eliteapp.R;

public class Companyforgotpassword extends AppCompatActivity implements View.OnClickListener {
    EditText etemails;
    Button recover;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyforgotpassword);
        etemails = (EditText) findViewById(R.id.company_get_email);
        recover = (Button) findViewById(R.id.company_recover_password);
        recover.setOnClickListener(this);
    }

    private void forgot() {
        String email=etemails.getText().toString();
        String type="companyrecover";
        Company_Recover_Password_Backend backgroundtask=new Company_Recover_Password_Backend(this);
        backgroundtask.execute(type,email);
    }

    @Override
    public void onClick(View v) {
        if (v == recover) {
            forgot();
        }
    }
}