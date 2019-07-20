package com.elitelodgit.eliteapp.Registerlogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.elitelodgit.eliteapp.UserTasks.InvestigatorForms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class CompanyRegisterBackend extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    ProgressDialog loading;
    String email;

    CompanyRegisterBackend(Context ctx) {
        context = ctx;
    }

    @Override

    protected void onPreExecute() {
        // loading = ProgressDialog.show(context,"Downloading images...","Please wait...",true,true);
        progressDialog = ProgressDialog.show(context, "Verifying credentials...", "Please wait...", true, true);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String company_url = "http://ictchops.me.ke/companyclient.php";
        if(type.equals("verify")){
            try{
                String cname = params[1];
                String reg = params[2];
                String email=params[3];
                String telno=params[4];
                String password=params[5];

                URL url = new URL(company_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("cname","UTF-8")+"="+URLEncoder.encode(cname,"UTF-8")+"&"
                        +URLEncoder.encode("reg","UTF-8")+"="+URLEncoder.encode(reg,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                        +"&"+URLEncoder.encode("telno","UTF-8")+"="+URLEncoder.encode(telno,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) result += line;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        if(result!=null && result.equals("login success")){
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            Intent i=new Intent(context, InvestigatorForms.class);
            i.putExtra("emailid",email);
            context.startActivity(i);

        }else if(result!=null && result.equals("successfully registred and Confirmation status has been sent to your email.")){
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,InvestigatorLogin.class);
            context.startActivity(i);
        }

        else if(result==null){
            Toast.makeText(context,"check your network connection",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

}
