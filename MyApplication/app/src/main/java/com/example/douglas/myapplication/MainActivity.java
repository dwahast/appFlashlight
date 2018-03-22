package com.example.douglas.myapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

     CameraManager mCameraManager;
     String mCameraId;
     Button mTorchOnOffButton;
     Boolean isTorchOn;
     MediaPlayer mp;



    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("FlashLightActivity", "onCreate()");
            setContentView(R.layout.activity_main);
            mTorchOnOffButton = (Button) findViewById(R.id.ButtonLanterna);
            isTorchOn = false;

        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mTorchOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isTorchOn) {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mCameraManager.setTorchMode(mCameraId, false);
                                Button textoButton = (Button)findViewById(R.id.ButtonLanterna);
                                textoButton.setText("OFF");
                                textoButton.setTextColor(0xFFE53935);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isTorchOn = false;
                    } else {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mCameraManager.setTorchMode(mCameraId, true);
                                Button textoButton = (Button)findViewById(R.id.ButtonLanterna);
                                textoButton.setText("ON");
                                textoButton.setTextColor(0xFF8BC34A);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isTorchOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }




