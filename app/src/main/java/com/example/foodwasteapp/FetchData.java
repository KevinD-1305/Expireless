package com.example.foodwasteapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url = new URL("https://world.openfoodfacts.org/api/v0/product/5060335635228.json");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            /*while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }*/
            JSONObject jsonObject = new JSONObject(bufferedReader.toString());
            if (jsonObject.getInt("status") == 1) {
                JSONObject product = jsonObject.getJSONObject("product");
                String productName = product.getString("product_name");
                data = productName;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        URLSearch.data.setText(this.data);
    }
}
