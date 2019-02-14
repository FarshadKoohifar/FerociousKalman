package com.koohifar.ferociouskalman;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends  FragmentActivity implements SensorEventListener  { /*AppCompatActivity,*/

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView quaternionTextView, httpTextView;
    private RequestQueue queue;
    private static final String url = "http://www.koohifar.com/api/rotation";// ;"http://httpbin.org/post"
    private static final String TAG = "Ferocious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quaternionTextView = (TextView) findViewById(R.id.textView);
        httpTextView = (TextView) findViewById(R.id.textView3);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        quaternionTextView.setText(String.format("%.3f",event.values[0])+"\t\t"+String.format("%.3f",event.values[1])+"\t\t"+String.format("%.3f",event.values[2])+"\t\t"+String.format("%.3f",event.values[3]));
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("x",event.values[0]);
            jsonBody.put("y",event.values[1]);
            jsonBody.put("z",event.values[2]);
            jsonBody.put("w",event.values[3]);
            Log.d(TAG, jsonBody.toString());
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,jsonBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d(TAG, response.toString());
                        httpTextView.setText(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, error.getMessage() == null? "NULL":error.getMessage());
                        httpTextView.setText(error.getMessage());
                    }
                }
            );
            postRequest.setTag(TAG);
            queue.add(postRequest);
        }
        catch (    JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

}