package com.example.foodwasteapp;


import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class URLSearch extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    Button buttonParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsearch);

        mTextViewResult = findViewById(R.id.text_view_result);
        buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

    }

    private void jsonParse() {

        String url = "https://world.openfoodfacts.org/api/v0/product/5060335635228.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject product = response.getJSONObject("product");
                            String productName = product.get("product_name").toString();
                            String servingSize = product.get("serving_quantity").toString();
                            mTextViewResult.append(productName + "\n\n" + servingSize + "\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
