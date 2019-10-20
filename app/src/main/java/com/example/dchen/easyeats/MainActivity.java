package com.example.dchen.easyeats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // ArrayList<String> ingredients = new ArrayList<String>();

    protected void processResponseFromAPI() {

    }


    protected void sendRequestToAPI(ArrayList<String> ingredients) {
        // sends a GET request to Edamam's recipe search API
        // API documentation: https://developer.edamam.com/edamam-docs-recipe-api

        String q = "";
        int numIngredients = ingredients.size();
        for (int i = 0; i < numIngredients; i++) {
            q += ingredients.get(i);
            if (i != numIngredients - 1) q += ",";
        }

        // TODO: Move to .env variables
        String edamamRecipeSearchURL = "https://api.edamam.com/search";
        String app_id = "8cdf0887";
        String app_key = "c5390f5d66013b6cd7b3f14922412b82";

        String ingr = Integer.toString(numIngredients + 2);

        String charset = java.nio.charset.StandardCharsets.UTF_8.name();
        String query = null;
        try {
            query = String.format("q=%s&app_id=%s&app_key=%s&ingr=%s",
                    URLEncoder.encode(q, charset),
                    URLEncoder.encode(app_id, charset),
                    URLEncoder.encode(app_key, charset),
                    URLEncoder.encode(ingr, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URLConnection connection = null;
        try {
            connection = new URL(edamamRecipeSearchURL + "?" + query).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.setRequestProperty("Accept-Encoding", "application/gzip");

        try {
            InputStream response = connection.getInputStream();
            processResponseFromAPI(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
