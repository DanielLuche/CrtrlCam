package com.dluche.crtrlcam;

import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dluche.crtrlcam.ctrl.CtrlCamera;
import com.dluche.crtrlcam.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CamActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Context context;
    private String path;
    private String prefix;
    private String ctrlID;
    private File newImage;
    private Bundle recoverBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        initVars();
        //
        recoverIntentInfo();
        //
        dispatchTakePictureIntent();
    }

    private void initVars() {
        context = getBaseContext();
        path = "";
        prefix = "";
        ctrlID = "";
        newImage = null;
    }

    private void recoverIntentInfo() {
        recoverBundle = getIntent().getExtras();
        //
        if(recoverBundle != null){
            if(recoverBundle.containsKey(CtrlCamera.DEFAULT_PATH)){
                path = recoverBundle.getString(CtrlCamera.DEFAULT_PATH,"");
                prefix = recoverBundle.getString(CtrlCamera.PICTURE_PREIX,"");
                ctrlID = recoverBundle.getString(CtrlCamera.CTRL_ID,"");
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            //
            newImage = createFile();
            //
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newImage));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    private File createFile() {
        String imageFileName = prefix +"_"+
                new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date())
                        + ".png"
                ;
        //
        File image = new File(path,imageFileName);
        //
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                //proceedSavePictureV2(data);
                //generateThumbnail(data);

                //loadPictureList();

                Toast.makeText(context, "Sucesso ao salvar foto\n", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Erro ao salvar foto\n", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(context, "Erro ao receber foto\n", Toast.LENGTH_LONG).show();
        }
        //
        CtrlCamera.sendBRCtrlCam(context,ctrlID);
        //
        finish();
    }
    //
    private void generateThumbnail(Intent data) throws IOException {
        File thumbnailFile = new File(path +Constants.THUMBNAIL_PATH + "/" + Constants.PREFIX_THUMBNAIL + newImage.getName());
        //
        //Bitmap thumbnail = BitmapFactory.decodeFile(newImage.getAbsolutePath());
        ExifInterface exifOrigin = new ExifInterface(newImage.getAbsolutePath());
        //
        FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFile);
        fileOutputStream.write(exifOrigin.getThumbnail());
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
