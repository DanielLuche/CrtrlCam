package com.dluche.crtrlcam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dluche.crtrlcam.ctrl.CtrlCamera;

public class MainActivity extends AppCompatActivity {

    private CtrlCamera ctrlCamera;
    private CtrlCamera ctrlCamera2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctrlCamera = findViewById(R.id.main_ctrl_cam);
        ctrlCamera2 = findViewById(R.id.main_ctrl_cam2);
        //
    }
}
