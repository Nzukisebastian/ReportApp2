package com.elitelodgit.eliteapp.Registerlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elitelodgit.eliteapp.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Link extends AppCompatActivity {

    Button login,regi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    startActivity(new Intent(this,ClientLogins.class));
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    startActivity(new Intent(this,InvestigatorLogin.class));
                    break;
        }
    }

}


