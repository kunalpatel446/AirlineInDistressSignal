package com.example.kunalpatel.hackbcaproject;
import com.example.kunalpatel.hackbcaproject.R;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
public class main extends AppCompatActivity implements SensorEventListener, LocationListener {
    public static final String ACCOUNT_SID = "AC5607d209a04727de0a57924629510064";
    public static final String AUTH_TOKEN = "a9bcb8bdbdf7f53d83d893fed0465177";
    boolean isEnabled = false;
    Button btn;
    float lat = 0, lng = 0, alt = 0;
    Sensor accel;
    SensorManager manager;
    float x = 0, y = 0, z = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.crash);
        gui(isEnabled);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        }
    }
    public void onCrash(View view) {
        isEnabled = !isEnabled;
        gui(isEnabled);
        boolean doneOnce = false;
        if(!doneOnce){
            new PostClass(this, "https://api.twilio.com/2016-4-3/Accounts/AC5607d209a04727de0a57924629510064/Messages.json",
                    "Body", "Lucas has been involved in a plane crash, his plane is being rescued by emergency crews.");
            doneOnce = true;
        }
    }
    public void gui(boolean state){
        if(state){
            btn.setText("Enabled");
        }
        else{
            btn.setText("Disabled");
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        manager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        x = lat;
        y = lng;
        z = alt;
        //Demo values
        //For actual deployment, use lat, alt, and lng variables
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onLocationChanged(Location location) {
        lat = (float)location.getLatitude();
        lng = (float)location.getLongitude();
        alt = (float)location.getAltitude();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private String stringURL;
        private String key, value;

        public PostClass(Context c, String stringURL, String key, String value){
            this.context = c;
            this.stringURL = stringURL;
            this.key = key;
            this.value = value;
        }

//        "http://d36bb8c4.ngrok.io/swear"

        protected void onPreExecute(){
//            progress= new ProgressDialog(this.context);
//            progress.setMessage("Loading");
//            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

//                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL(stringURL);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "fizz=buzz";
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes("To"+ "=+18562755212");
                dStream.writeBytes("From"+"=+8563515234");
                dStream.writeBytes(key + "=" + value);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator")  + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        outputView.setText(output);
//                        progress.dismiss();
//                    }
//                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}