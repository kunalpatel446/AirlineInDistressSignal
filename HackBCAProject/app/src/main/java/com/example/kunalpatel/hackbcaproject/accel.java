package com.example.kunalpatel.hackbcaproject;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class accel extends Service implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private int data_index = 0;
    private static final int FFT_SIZE = 64;
    //private Complex[] xdata;
    //private Complex[] ydata;
    //private Complex[] zdata;
    //private Complex[][] alldata;
    private final IBinder myBinder = new MyLocalBinder();
    private int data_ready = 0;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return myBinder;
    }
    public int onStartCommand (Intent intent, int flags, int startId){
        Log.i("New Tag","Service Called");
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }
    /*
    public Complex[][] getData() {
        if (data_ready == 0){
            Log.i("New Tag","getStatus Called, Data not ready");
            return null;
        }else {
            Log.i("New Tag","getStatus Called, Data is ready");
            alldata[0] = xdata;
            alldata[1] = ydata;
            alldata[2] = zdata;
            data_ready = 0;
            data_index = 0;
            return alldata;
        }
    }
    */
    public class MyLocalBinder extends Binder {
        accel getService() {
            return accel.this;
        }
    }

    public int getStatus(){
        Log.i("New Tag","getStatus Called");
        return data_ready;
    }
    public void onSensorChanged(SensorEvent sensorEvent) {
        long nowTime = System.currentTimeMillis();
        if ((nowTime - lastUpdate) > 100){
            lastUpdate = nowTime;
            Sensor mySensor = sensorEvent.sensor;
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                if (data_index < FFT_SIZE) {

                    //xdata[data_index] = new Complex(x);
                    //ydata[data_index] = new Complex(y);
                    //zdata[data_index] = new Complex(z);
                    data_index++;
                    Log.i("New Tag","Data not Ready");
                }
                else if (data_index == FFT_SIZE)
                {
                    data_ready = 1;
                    Log.i("New Tag","Data is Ready");
                    Toast.makeText(getApplicationContext(), "Data Reading Complete", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}