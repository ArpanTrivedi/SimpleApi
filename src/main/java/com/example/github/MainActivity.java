package com.example.github;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.tvText);

        requestQueue = Volley.newRequestQueue(this);

        fetch();


    }

    private void fetch() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://api.github.com/users/ArpanTrivedi/repos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        progressDialog.dismiss();
                        try {
                            String x = "";
                            JSONArray array = new JSONArray(response);
                            for ( int i = 0 ; i < array.length() ; i++ ) {
                                JSONObject obj = array.getJSONObject(i);
                                JSONObject ob = obj.getJSONObject("owner");
                                x += ob.getString("type");
                            }
                            text.setText(x);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MainActivity.this,"Error is " + error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);

    }


}