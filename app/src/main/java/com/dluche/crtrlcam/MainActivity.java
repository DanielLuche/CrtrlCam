package com.dluche.crtrlcam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.dluche.crtrlcam.ctrl.CtrlCamera;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private CtrlCamera ctrlCamera;
    private CtrlCamera ctrlCamera2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FABRIC
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //btn.setText("Ronaldo");

        //ctrlCamera = findViewById(R.id.main_ctrl_cam);
        ctrlCamera = //findViewById(R.id.main_ctrl_cam);
        ctrlCamera2 = findViewById(R.id.main_ctrl_cam2);
        //
        //ctrlCamera.setOnClickListener(null);
    }
}
