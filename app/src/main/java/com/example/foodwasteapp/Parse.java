package com.example.foodwasteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;

public class Parse extends AppCompatActivity {
    Button parse;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsearch);


        parse = findViewById(R.id.button_parse);
        data = findViewById(R.id.text_view_result);

        parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute();
            }
        });

            }
}
