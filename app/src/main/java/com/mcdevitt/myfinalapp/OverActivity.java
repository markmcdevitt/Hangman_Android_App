package com.mcdevitt.myfinalapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 21/11/2015.
 */

public class OverActivity extends Activity {

    int mPoints;
    String insertUrl = "http://192.168.56.1//webapp/post.php";
    RequestQueue mRequestQueue;
    Button saveScore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        /////////////////////////////////////////////////////

        int points = getIntent().getIntExtra("POINTS_IDENTIFIER", 0);
        TextView textViewpoints = (TextView) findViewById(R.id.textViewPoints);
        textViewpoints.setText(String.valueOf(points));
        mPoints = points;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        saveScore = (Button) findViewById(R.id.button);

        saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        EditText editText = (EditText) findViewById(R.id.editTextName);

                        String name = editText.getText().toString();
                        String points = String.valueOf(mPoints);
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("name", name);
                        parameters.put("score", points);
                        finish();
                        return parameters;
                    }
                };

                mRequestQueue.add(request);
            }
        });
    }
}