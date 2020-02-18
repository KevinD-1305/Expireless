package com.example.foodwasteapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLSearch extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    String product;
    ProgressDialog pd;
    String Players[] = new String[100];
    String url = "https://world.openfoodfacts.org/api/v0/product/5060335635228.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsearch);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new jsonParse().execute("https://world.openfoodfacts.org/api/v0/product/5060335635228.json");
            }
        });
    }

   /* private void jsonParse() {
        Log.d("Starting_parse", "parsed");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = new JSONObject(url);
                            // fetch JSONObject named employee
                            JSONObject employee = obj.getJSONObject("product_name");
                            product = employee.getString("product_name");

                            mTextViewResult.append(product +"\n\n");
                            Log.d("Fail", "Failed");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Catch", "Catched");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
    */
   private class jsonParse extends AsyncTask<String, String, String> {

       protected void onPreExecute() {
           super.onPreExecute();

           pd = new ProgressDialog(URLSearch.this);
           pd.setMessage("Please wait");
           pd.setCancelable(false);
           pd.show();
       }

       protected String doInBackground(String... params) {


           HttpURLConnection connection = null;
           BufferedReader reader = null;

           try {
               URL url = new URL(params[0]);
               connection = (HttpURLConnection) url.openConnection();
               connection.connect();


               InputStream stream = connection.getInputStream();

               reader = new BufferedReader(new InputStreamReader(stream));

               StringBuffer buffer = new StringBuffer();
               String line = "";

               while ((line = reader.readLine()) != null) {
                   buffer.append(line+"\n");
                   Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

               }

               return buffer.toString();


           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               if (connection != null) {
                   connection.disconnect();
               }
               try {
                   if (reader != null) {
                       reader.close();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           return null;
       }

       @Override
       protected void onPostExecute(String result) {
           super.onPostExecute(result);
           try {
               JSONObject jsonObject = new JSONObject(result);
               JSONObject objStandard = jsonObject.getJSONObject("product");
               JSONArray jsonArray = objStandard.getJSONArray("product_name");
               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject JO = jsonArray.getJSONObject(i);
                   Players[i] = JO.getString("firstName");
                   mTextViewResult.append(Players[i] + "\n");
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }
           if (pd.isShowing()){
               pd.dismiss();
           }
           mTextViewResult.setText(result);
       }
   }


}