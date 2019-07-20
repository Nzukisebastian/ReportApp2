package com.elitelodgit.eliteapp.CompanyProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.elitelodgit.eliteapp.ClientProfile.AdapterList;
import com.elitelodgit.eliteapp.ClientProfile.ModelList;
import com.elitelodgit.eliteapp.ClientProfile.ShowProfileScreen;
import com.elitelodgit.eliteapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Company_Profile_Screen extends AppCompatActivity {
    RecyclerView mRecyclerview;
    private EditText editTextId;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    RequestQueue mRequest;
    List<Company_Model_List> mListitems;
    String email_id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__profile__screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        email_id=intent.getExtras().getString("emailid");
        mRecyclerview=(RecyclerView)findViewById(R.id.companyprofilerecycler);
        mRequest= Volley.newRequestQueue(getApplicationContext());
        mListitems=new ArrayList<>();
        request();
        mManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter=new Company_Adapter_List(mListitems, Company_Profile_Screen.this);
        mRecyclerview.setAdapter(mAdapter);

    }

    private void request(){
        String email="twawezatech@gmail.com";
     final String url="http://ictchops.me.ke/imageupload/get_company_profile.php?emailid="+email;
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
                        Company_Model_List model=new Company_Model_List();
                        model.setCompanyname(data.getString("companyname"));
                        model.setEmail(data.getString("email"));
                        model.setTelno(data.getString("telno"));
                        model.setRegno(data.getString("registration"));
                        //model.setImg(data.getString("image"));
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
