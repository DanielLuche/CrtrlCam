package com.dluche.crtrlcam.ctrl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dluche.crtrlcam.CamActivity;
import com.dluche.crtrlcam.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by d.luche on 24/10/2017.
 */

public class CtrlCamera extends LinearLayout implements View.OnClickListener {
    public static final String DEFAULT_PATH = "DEFAULT_PATH";
    public static final String PICTURE_PREIX = "PICTURE_PREIX";
    public static final String PICTURE_RECEIVER_TYPE = "PICTURE_RECEIVER_TYPE";
    public static final String PICTURE_RECEIVER_FILTER_PREFIX = "CRL_CAM_PICTURE_RECEIVER_FILTER";
    public static final String CTRL_ID = "CTRL_ID";
    public static String PICTURE_RECEIVER_FILTER = "";

    private Context context;
    private ArrayList<String> pictureList;
    private int pictureLimit;
    private String defaultPath;
    private File newImage;
    private int selfIcon;
    private String prefix;
    private ImageView iv_icon;
    private TextView tv_qty;
    private PictureReceiver pictureReceiver;

    public CtrlCamera(Context context) {
        super(context);
        //
        initialize(context);
    }

    public CtrlCamera(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public CtrlCamera(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setDefaultValues(context);
        //
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_camera_layout, this,true);
        //
        iv_icon = findViewById(R.id.ctrl_cam_iv_icon);
        //
        tv_qty = findViewById(R.id.ctrl_cam_tv_qty);
        //
        initReceiver();
        //
        initAction();
        //
        updateIconState();

    }

    private void initReceiver() {
        pictureReceiver = new PictureReceiver();
        IntentFilter pictureIFilter = new IntentFilter(PICTURE_RECEIVER_FILTER);
        pictureIFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(context).registerReceiver(pictureReceiver, pictureIFilter);
    }

    private void initAction() {
        iv_icon.setOnClickListener(this);
    }

    public void setDefaultValues(Context context) {
        this.context = context;
        this.pictureList = new ArrayList<>();
        this.pictureLimit = 5;
        this.defaultPath = Environment
                .getExternalStorageDirectory() + "/testCameraCtrlDir";
        this.newImage = null;
        this.selfIcon = -1;
        //this.prefix = "123";
        this.prefix = String.valueOf(this.getId());
        this.setClickable(true);
        this.PICTURE_RECEIVER_FILTER = this.PICTURE_RECEIVER_FILTER_PREFIX + this.getId();
    }

    public File getNewImage() {
        return newImage;
    }

    public void setNewImage(File newImage) {
        this.newImage = newImage;
    }

    public int getSelfIcon() {
        return selfIcon;
    }

    public void setSelfIcon(int selfIcon) {
        this.selfIcon = selfIcon;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onClick(View view) {
        callCamActivity();
    }

    private void callCamActivity() {
        Intent mIntent = new Intent(context,CamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DEFAULT_PATH,defaultPath);
        bundle.putString(PICTURE_PREIX,prefix);
        bundle.putString(CTRL_ID, String.valueOf(getId()));
        mIntent.putExtras(bundle);
        context.startActivity(mIntent);
    }

    public int getCountPictureAssociated(){
        File fileList = new File(defaultPath);
        File[] files = fileList.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.startsWith(prefix)) {
                    return true;
                }
                return false;
            }
        });
        //
        if (files != null) {
            Arrays.sort(files);
            return files.length;
        }
        //
        return 0;
    }

    private void updateIconState(){
        int qty = getCountPictureAssociated();
        if(qty == 0){
            iv_icon.setColorFilter(context.getResources().getColor(R.color.colorAccent));
            tv_qty.setText("");
            tv_qty.setVisibility(GONE);
        }else{
            iv_icon.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
            tv_qty.setText(String.valueOf(qty > 9 ? qty : " " + qty + " "));
            tv_qty.setVisibility(VISIBLE);
        }
    }

    public static void sendBRCtrlCam(Context context, String ctrl_id){
        Intent mIntent = new Intent(CtrlCamera.PICTURE_RECEIVER_FILTER_PREFIX +ctrl_id);
        mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mIntent.putExtra(CtrlCamera.PICTURE_RECEIVER_TYPE, "");
        //
        LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //
        LocalBroadcastManager.getInstance(context).unregisterReceiver(pictureReceiver);
    }

    /**
     * Class BroadCastReceiver
     *
     */
    public class PictureReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(PICTURE_RECEIVER_TYPE);
            //
            updateIconState();
        }
    }
}
