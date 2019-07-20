package com.elitelodgit.eliteapp.Registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elitelodgit.eliteapp.R;

public class InvestigatorLogin extends AppCompatActivity  implements View.OnClickListener {
    EditText etemails,etpasswords;
    Button login,register;
    TextView cforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_login);
        etemails = (EditText) findViewById(R.id.etemail);
        etpasswords = (EditText) findViewById(R.id.etpassword);
        cforgot = (TextView) findViewById(R.id.cforgetpass);
        login=(Button)findViewById(R.id.btn_logi);
        register=(Button)findViewById(R.id.btn_cregister);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        cforgot.setOnClickListener(this);
    }

    private void login() {
        String email=etemails.getText().toString();
        String password=etpasswords.getText().toString();
        String type="login";
        InvestigatorLoginBackend investigatorLoginBackend=new InvestigatorLoginBackend(this);
        investigatorLoginBackend.execute(type,email,password);
    }
    private void register() {
        startActivity(new Intent(this,InvestigatorRegister.class));

    }

    private void forgot() {
        startActivity(new Intent(this,Companyforgotpassword.class));

    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            login();
        }else if(v==register){
            register();
        }else if(v==cforgot){
            forgot();
        }
    }
}
