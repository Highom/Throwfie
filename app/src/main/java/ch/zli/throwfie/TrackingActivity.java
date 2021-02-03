package ch.zli.throwfie;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TrackingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean thresholdReached = false;
    private final int minThreshold = 60;
    private final int captureThreshold = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accel != null) {
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float X = event.values[0];
        float Y = event.values[1];
        float Z = event.values[2];
        double speed = Math.sqrt(X * X + Y * Y + Z * Z);
        if ( speed >  minThreshold){
            thresholdReached = true;
        }
        if (thresholdReached && speed < captureThreshold){
            //Take Pictures
            thresholdReached = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}