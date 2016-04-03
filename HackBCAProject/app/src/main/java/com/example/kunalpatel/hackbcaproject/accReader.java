package com.example.kunalpatel.hackbcaproject;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * Created by kunalpatel on 4/3/16.
 */
public abstract class accReader extends Activity implements SensorEventListener {
    private final SensorManager sensorManager;
    private final Sensor acc;
    public accReader()
    {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }
    public void onSensorChanged(SensorEvent event){
    }
}