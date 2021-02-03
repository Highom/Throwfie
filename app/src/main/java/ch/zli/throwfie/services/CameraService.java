package ch.zli.throwfie.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.concurrent.TimeUnit;

import ch.zli.throwfie.R;

public class CameraService extends Service {
    private final IBinder binder = new CameraBinder();
    public class CameraBinder extends Binder {
        public CameraService getService() {
            return CameraService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {startCamera();}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Throwfie needs Camera Permissions to make Pictures! Please enable it in the Settings", Toast.LENGTH_LONG);
                            toast.show();
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(),"Throwfie needs Camera Permissions to make Pictures! Please allow to take pictures", Toast.LENGTH_LONG);
                            toast.show();
                        }}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {token.continuePermissionRequest();}
                }).check();

    }

    private void startCamera() {
        System.out.println("Camera Started");
    }

    //TODO: Implement Camera
}