package com.fr.ovelaydisplay;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canDraw()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                requestPermissionLauncher2.launch(intent);
            }else {
                startOverlayService();
            }
        }

    }

    private boolean canDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(MainActivity.this);
        }
        return true;
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {

                }
            });

    private final ActivityResultLauncher<Intent> requestPermissionLauncher2 =
           registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::result);

    private void result(ActivityResult result) {
       if(canDraw()){
           startOverlayService();
       }
    }

    private void startOverlayService() {
        startService(new Intent(this,FloatingViewService.class));
    }
}