package com.elitelodgit.eliteapp.Registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.elitelodgit.eliteapp.R;

public class InvestigatorRegister extends AppCompatActivity implements View.OnClickListener  {
    EditText cname,reg,email,telno,password;
    Button verify,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator_register);

        cname = (EditText) findViewById(R.id.etcompanyname);
        reg = (EditText) findViewById(R.id.etregister);
        email= (EditText) findViewById(R.id.etofficialemail);
        telno = (EditText) findViewById(R.id.ettelno);
        password = (EditText) findViewById(R.id.etcompanypassword);
        verify= (Button) findViewById(R.id.buttonverivy);
        login= (Button) findViewById(R.id.clogin);
        verify.setOnClickListener(this);
        login.setOnClickListener(this);


        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Type A", "Type B", "Type C","Type D"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }


    private void verivy() {
        String vcname = cname.getText().toString().trim().toLowerCase();
        String vreg = reg.getText().toString().trim().toLowerCase();
        String  vemail= email.getText().toString().trim().toLowerCase();
        String vtelno = telno.getText().toString().trim().toLowerCase();
        String vpassword = password.getText().toString().trim().toLowerCase();
        String type = "verify";
        CompanyRegisterBackend companyRegisterBackend = new CompanyRegisterBackend(this);
        companyRegisterBackend.execute(type,vcname,vreg,vemail,vtelno,vpassword);
        emptyInputEditText();
    }

    private void login() {
        startActivity(new Intent(this,InvestigatorLogin.class));

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        cname.setText(null);
        email.setText(null);
        telno.setText(null);
        password.setText(null);
    }


    @Override
    public void onClick(View view) {
        if (view == verify) {
            verivy();
        }else if(view==login){
            login();
        }
    }
}
