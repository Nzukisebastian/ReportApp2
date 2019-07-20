package com.elitelodgit.eliteapp.ClientReports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elitelodgit.eliteapp.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class Full_Details extends AppCompatActivity {
    ImageView thubnail;
    TextView nature,date,time,place,parties,insurance,policy,reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__details);
        nature=(TextView)findViewById(R.id.fullreport_nature);
        thubnail=(ImageView)findViewById(R.id.report_image);
        date=(TextView)findViewById(R.id.fullreport_date);
        time=(TextView)findViewById(R.id.report_time);
        place=(TextView)findViewById(R.id.fullreport_place);
        parties=(TextView)findViewById(R.id.report_parties);
        insurance=(TextView)findViewById(R.id.report_insurance);
        policy=(TextView)findViewById(R.id.report_policy);
        reference=(TextView)findViewById(R.id.report_reference);

        //receiving data
        Intent intent=getIntent();
        String vpic=intent.getExtras().getString("image");
        String vnature =intent.getExtras().getString("nature");
        String vdate=intent.getExtras().getString("date");
        String vtime=intent.getExtras().getString("time");
        String vplace=intent.getExtras().getString("place");
        String vparties=intent.getExtras().getString("parties");
        String vinsurance=intent.getExtras().getString("insurance");
        String vpolicy=intent.getExtras().getString("policy");
        String vreference=intent.getExtras().getString("reference");

        //setting values to the variables
        Glide.with(this).load(vpic).transition(withCrossFade()).into(thubnail);
        nature.setText(vnature);
        date.setText(vdate);
        time.setText(vtime);
        place.setText(vplace);
        parties.setText(vparties);
        insurance.setText(vinsurance);
        policy.setText(vpolicy);
        reference.setText(vreference);
    }
}
