package com.elitelodgit.eliteapp.ClientProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.elitelodgit.eliteapp.R;

public class ShowProfileScreen extends AppCompatActivity {
    RecyclerView mRecyclerview;
    private EditText editTextId;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    RequestQueue mRequest;
    List<ModelList> mListitems;
    String email_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        email_id=intent.getExtras().getString("emailid");
        mRecyclerview=(RecyclerView)findViewById(R.id.recyclerTemp);
        mRequest= Volley.newRequestQueue(getApplicationContext());
        mListitems=new ArrayList<>();
        mManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter=new AdapterList(mListitems,ShowProfileScreen.this);
        mRecyclerview.setAdapter(mAdapter);
        request(email_id);
    }

    private void request(String email_id){

        final String url="http://ictchops.me.ke/imageupload/getprofile.php?emailid="+email_id;
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
                        ModelList model=new ModelList();
                        model.setName(data.getString("name"));
                        model.setEmail(data.getString("email"));
                        model.setPhone(data.getString("phone"));
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
