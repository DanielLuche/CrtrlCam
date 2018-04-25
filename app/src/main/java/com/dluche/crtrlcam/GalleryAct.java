package com.dluche.crtrlcam;

import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dluche.crtrlcam.adapter.GalleryPagerAdapter;
import com.dluche.crtrlcam.adapter.GalleryThumbPagerAdapter;
import com.dluche.crtrlcam.ctrl.CtrlCamera;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryAct extends AppCompatActivity {

    private Context context;
    private ViewPager vpPicture;
    private FloatingActionButton fabNewPicture;
    private String path;
    private String prefix;
    private String ctrlID;
    private Bundle recoverBundle;
    private GalleryPagerAdapter mAdapter;
    private GalleryThumbPagerAdapter mAdapter2;
    private boolean firstResume = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);
        //
        initVars();
        //
        initActions();

    }

    private void initVars() {
        context = getBaseContext();
        //
        recoverIntentInfo();
        //
        vpPicture = findViewById(R.id.gallery_vp_pictures);
        //
        fabNewPicture = findViewById(R.id.gallery_fab_new_picture);
        //
        //buildAdapter();
        buildAdapterThumb();
        //
    }

    private void buildAdapter() {
        mAdapter = new GalleryPagerAdapter(
                context,
                CtrlCamera.getPictureListByPrefix(prefix),
                R.layout.gallery_cell
        );
        //
        vpPicture.setAdapter(mAdapter);
    }

    private void buildAdapterThumb() {
        mAdapter2 = new GalleryThumbPagerAdapter(
                context,
                getThumbs()
        );
        //
        vpPicture.setAdapter(mAdapter2);
    }

    private void recoverIntentInfo() {
        recoverBundle = getIntent().getExtras();
        //
        if (recoverBundle != null) {
            if (recoverBundle.containsKey(CtrlCamera.DEFAULT_PATH)) {
                path = recoverBundle.getString(CtrlCamera.DEFAULT_PATH, "");
                prefix = recoverBundle.getString(CtrlCamera.PICTURE_PREIX, "");
                ctrlID = recoverBundle.getString(CtrlCamera.CTRL_ID, "");
            }
        }
    }

    private ArrayList<byte[]> getThumbs() {
        ArrayList<byte[]> thumbs = new ArrayList<>();
        try {
            for (String s : CtrlCamera.getPictureListByPrefix(prefix)) {

                ExifInterface exifOrigin = new ExifInterface(s);
                //
                thumbs.add(exifOrigin.getThumbnail());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbs;
    }

    private void initActions() {
        //
        fabNewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCamActivity();
            }
        });
    }

    private void callCamActivity() {
        Intent mIntent = new Intent(context, CamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CtrlCamera.DEFAULT_PATH, path);
        bundle.putString(CtrlCamera.PICTURE_PREIX, prefix);
        bundle.putString(CtrlCamera.CTRL_ID, ctrlID);
        mIntent.putExtras(bundle);
        context.startActivity(mIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        if(!firstResume) {
            //buildAdapter();
            buildAdapterThumb();
            vpPicture.setCurrentItem(mAdapter2.getCount() -1);
        }
        //
        firstResume = false;
    }
}
