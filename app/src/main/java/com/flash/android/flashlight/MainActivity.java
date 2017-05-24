package com.flash.android.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
        ImageButton imageButton;
       android.hardware.Camera camera;
        android.hardware.Camera.Parameters parameters;
    boolean isFlash = true;
    boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera= android.hardware.Camera.open();
            parameters = camera.getParameters();
            isFlash = true;



        }
        imageButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isFlash)
               {
                if(!isOn) {
                   imageButton.setImageResource(R.drawable.btn_switch_off);
                parameters.setFlashMode( android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                   camera.setParameters(parameters);
                   camera.startPreview();
                   isOn= true;

               }
               else
               {
                   imageButton.setImageResource(R.drawable.btn_switch_on);
                   parameters.setFlashMode( android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                   camera.setParameters(parameters);
                   camera.stopPreview();
                   isOn = false;

               }}
               else
               {
                   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                   builder.setTitle("Error");
                   builder.setMessage("Flash is not available on this device");
                   builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                           finish();
                       }
                   });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();

               }
            }
        }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera!=null) {
        camera.release();
        camera = null;
}}
}