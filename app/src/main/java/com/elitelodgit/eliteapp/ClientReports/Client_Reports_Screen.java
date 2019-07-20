package com.elitelodgit.eliteapp.ClientReports;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.elitelodgit.eliteapp.R;
import com.elitelodgit.eliteapp.Registerlogin.ClientForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Client_Reports_Screen extends AppCompatActivity {
    RecyclerView mRecyclerview;
    private EditText editTextId;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    RequestQueue mRequest;
    List<ReportModelList> mListitems;
    String email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__reports__screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        email_id=intent.getExtras().getString("emailid");

        mRecyclerview=(RecyclerView)findViewById(R.id.report_recycler);
        mRequest= Volley.newRequestQueue(getApplicationContext());
        mListitems=new ArrayList<>();
        request(email_id);
        mManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter=new ReportAdapterList(mListitems,Client_Reports_Screen.this);
        mRecyclerview.setAdapter(mAdapter);

    }

    private void request(String email_id){

        final String url="http://ictchops.me.ke/imageupload/getclient_report.php?emailid="+email_id;
        JsonArrayRequest requestimage=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSONResponse",response.toString());
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject data=response.getJSONObject(i);
                        //creating an object of class Modellist and then adding values to the object by calling methods
                        //getting information from the server ie id,title,kitengerani,image then passing the parameters to modellist.java
                        //using set() and get() methods to add and update data freom the server
                        ReportModelList model=new ReportModelList();
                        model.setNature(data.getString("nature"));
                        model.setDate(data.getString("date"));
                        model.setTime(data.getString("time"));
                        model.setImage(data.getString("imageurl"));
                        model.setPlace(data.getString("place"));
                        model.setParties(data.getString("parties"));
                        model.setInsurance(data.getString("insurance"));
                        model.setPolicy(data.getString("policy"));
                        model.setReference(data.getString("conclusion"));
                        //adding the object to List for storage
                        mListitems.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRORRequest","Error:"+error.getMessage());

                    }
                });
        mRequest.add(requestimage);
    }
}
