package com.dluche.crtrlcam;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.dluche.crtrlcam.ctrl.CtrlCamera;

public class GalleryViewer extends AppCompatActivity {

    private Context context;
    private WebView wv_image;
    private Bundle recoverBundle;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery_viewer);

        recoverIntentInfo();

        initVar();

    }

    private void initVar() {
        context = getBaseContext();
        //
        wv_image = findViewById(R.id.gallery_viewer_wv_main);
        //
        String pathUrl = "file:///"+path;
        wv_image.getSettings().setBuiltInZoomControls(true);
        wv_image.getSettings().setDisplayZoomControls(false);
        wv_image.setInitialScale(50);
        wv_image.loadUrl(pathUrl);
    }

    private void recoverIntentInfo() {
        recoverBundle = getIntent().getExtras();
        //
        if(recoverBundle != null){
            if(recoverBundle.containsKey(CtrlCamera.DEFAULT_PATH)){
                path = recoverBundle.getString(CtrlCamera.DEFAULT_PATH,"");
            }
        }
    }
}
