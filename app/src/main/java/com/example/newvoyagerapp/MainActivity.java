package com.example.newvoyagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.android.volley.AuthFailureError;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView osebe;
    private final String url = "https://newvoyager.azurewebsites.net/api/VoyagersApi";
    public static final String EXTRA_MESSAGE = "com.example.newvoyagerapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        osebe = (TextView) findViewById(R.id.osebe);
    }

    public  void prikaziOsebe(View view){
        if (view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener)
            {
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ApiKey", "MaliIstiMater");
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }
    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object =response.getJSONObject(i);
                    String name = object.getString("firstName");
                    String surname = object.getString("lastName");
                    data.add(name + " " + surname);

                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
            }
            osebe.setText("");
            for (String row: data){
                String currentText = osebe.getText().toString();
                osebe.setText(currentText + "\n\n" + row);
            }

        }

    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    public void addStudentActivity (View view) {
        Intent intent = new Intent(this,AddVoyagerActivity.class);
        String message = "Dodaj osebo v seznam.";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}