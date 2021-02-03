package ch.zli.throwfie.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import ch.zli.throwfie.services.CameraService;
import ch.zli.throwfie.R;

public class TrackingActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private CameraService mService;
    private boolean mBound = false;
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
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, CameraService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
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
            if (mBound) {
                //Make picture
            }
            thresholdReached = false;
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            CameraService.CameraBinder binder = (CameraService.CameraBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}