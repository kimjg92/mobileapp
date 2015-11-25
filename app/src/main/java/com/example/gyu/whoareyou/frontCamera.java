package com.example.gyu.whoareyou;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GYU on 2015-11-06.
 */
public class frontCamera extends Activity implements Serializable{
    String TAG = "CAMERA";
    private Context mContext;
    private Camera mCamera;
    private String Picture_path;
    private String Picture_name;
    private Uri Picture_Uri;
    public frontCamera (Context cont){
        mContext = cont;
        // 파일명을 적당히 생성. 여기선 시간으로 파일명 중복을 피한다.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Picture_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/MyCameraApp/IMG_"+timeStamp+".jpg"; // 파일 경로 및 파일 명의 기본값
        Picture_Uri = Uri.parse(Picture_path);
        Picture_name = "IMG_"+timeStamp+".jpg";
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // JPEG 이미지가 byte[] 형태로 들어옵니다

            File pictureFile = getOutputMediaFile();
            //setPath(pictureFile.getAbsolutePath());

            if (pictureFile == null) {
                Toast.makeText(mContext, "Error saving!!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //Thread.sleep(500);
                mCamera.startPreview();

            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };
    public String getPicture_name(){
        return Picture_name;
    }
    public void captureCamera(){
        //mContext = this;
        // 카메라 인스턴스 생성
        if (checkCameraHardware(mContext)) {
            mCamera = getCameraInstance();
            System.out.println(mPicture);
            mCamera.takePicture(null, null, mPicture);
            //mCamera.release(); <- 여기서 바로 release하면 사진 저장이 안됨
        }
        else{
            Toast.makeText(mContext, "no camera on this device!", Toast.LENGTH_SHORT).show();
        }

    }


    /** 카메라 하드웨어 지원 여부 확인 */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // 카메라가 최소한 한개 있는 경우 처리
            Log.i(TAG, "Number of available camera : " + Camera.getNumberOfCameras());
            return true;
        } else {
            // 카메라가 전혀 없는 경우
            Toast.makeText(mContext, "No camera found!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /** 카메라 인스턴스를 안전하게 획득합니다 */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(1);
            System.out.println("카메라 사용 가능 return : " + c);
        }
        catch (Exception e){
            System.out.println("카메라 사용 불가 return : " + c);
            // 사용중이거나 사용 불가능 한 경우
        }
        return c;
    }


    /** 이미지를 저장할 파일 객체를 생성합니다 */
    private File getOutputMediaFile(){
        // SD카드가 마운트 되어있는지 먼저 확인해야합니다
        // Environment.getExternalStorageState() 로 마운트 상태 확인 가능합니다

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // 굳이 이 경로로 하지 않아도 되지만 가장 안전한 경로이므로 추천함.

        // 없는 경로라면 따로 생성한다.
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCamera", "failed to create directory");
                return null;
            }
        }


        File mediaFile;

        mediaFile = new File(Picture_path);

        Log.i("MyCamera", "Saved at" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        //setPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/MyCameraApp/IMG_"+timeStamp+".jpg");
        //System.out.println(mediaFile.getAbsolutePath());

        return mediaFile;
    }

    public void setPath (String path_name){
        Picture_path = path_name;
        System.out.println("frontCamera Class : setPath method called , input : " + path_name);
        System.out.println("frontCamera Class : setPath method called , set Picture_path : " + Picture_path);
    }

    public String getPath (){
        System.out.println("frontCamera Class : getPath method called , return : " + Picture_path);
        return Picture_path;
    }

    public Uri getPathUri(){
        return Picture_Uri;
    }
    @Override
    public void onPause(){
        super.onPause();
        // 보통 안쓰는 객체는 onDestroy에서 해제 되지만 카메라는 확실히 제거해주는게 안전하다.
        mCamera.release();
        mCamera=null;
    }
    @Override
    public void onStop() {
        super.onStop();
        mCamera.release();
        mCamera=null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
        mCamera=null;
    }

    public void releaseCamera(){
        if(mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}