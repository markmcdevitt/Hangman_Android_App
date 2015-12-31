package com.mcdevitt.myfinalapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Created by Mark on 24/11/2015.
 */
public class ScoreActivity extends Activity {

    String insertUrl = "http://192.168.56.1//webapp/get.php";//10.0.2.2---->emulator //192.168.56.1---->ipaddress for the maps
    RequestQueue mRequestQueue;
    Button showAll;
    TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        text = (TextView) findViewById(R.id.textView6);
        showAll = (Button) findViewById(R.id.showAll);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, insertUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray scores = response.getJSONArray("scores");
                            for (int i = 0; i < scores.length(); i++) {
                                JSONObject score1 = scores.getJSONObject(i);

                                String name = score1.getString("name");
                                String score = score1.getString("score");


                                text.append(name + " with " + score + " points\n");

                                showAll.setEnabled(false);
                                showAll.setClickable(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                mRequestQueue.add(request);


            }
        });


    }

}
