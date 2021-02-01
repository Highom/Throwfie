package ch.zli.throwfie;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class TrackingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (accelerometer != null){
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getName() == "gyroscope-icm20690"){
//            gyroX = event.values[0];
//            gyroY = event.values[1];
//            gyroZ = event.values[2];
        }else{
//            accelX = event.values[0];
//            accelY = event.values[1];
//            accelZ = event.values[2];
        }
        //use Service to trigger image
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}