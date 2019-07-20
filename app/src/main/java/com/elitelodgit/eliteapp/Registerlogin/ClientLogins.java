package com.elitelodgit.eliteapp.Registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elitelodgit.eliteapp.R;

public class ClientLogins extends AppCompatActivity implements View.OnClickListener{
    EditText etemails,etpasswords;
    Button login,register;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_logins);
        etemails = (EditText) findViewById(R.id.etemail);
        etpasswords = (EditText) findViewById(R.id.etpassword);
        forgot = (TextView) findViewById(R.id.iforgot);
        login=(Button)findViewById(R.id.btn_logi);
        register=(Button) findViewById(R.id.btn_iregister);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot.setOnClickListener(this);
    }

    private void login() {
        String email=etemails.getText().toString();
        String password=etpasswords.getText().toString();
        String type="login";
        Backgroundtask backgroundtask=new Backgroundtask(this);
        backgroundtask.execute(type,email,password);
    }

    private void register() {
        startActivity(new Intent(this,ClientRegister.class));

    }

    private void forgot() {
        startActivity(new Intent(this,Recoverpassword.class));

    }
    @Override
    public void onClick(View v) {
        if(v==login){
            login();
        }
        else if(v==register){
            register();
        }
        else if(v==forgot){
            forgot();
        }
    }
}
