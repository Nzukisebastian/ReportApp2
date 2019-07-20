package com.elitelodgit.eliteapp.Registerlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elitelodgit.eliteapp.R;

public class Recoverpassword extends AppCompatActivity implements View.OnClickListener {

    EditText etemails;
    Button recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoverpassword);
        etemails = (EditText) findViewById(R.id.client_get_email);
        recover = (Button) findViewById(R.id.client_recover_password);
        recover.setOnClickListener(this);
    }

    private void forgot() {
        String email=etemails.getText().toString();
        String type="clientrecover";
        Company_Recover_Password_Backend background=new Company_Recover_Password_Backend(this);
        background.execute(type,email);
    }

    @Override
    public void onClick(View v) {
        if (v == recover) {
            forgot();
        }
    }
}
