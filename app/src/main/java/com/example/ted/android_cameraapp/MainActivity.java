package com.example.ted.android_cameraapp;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.PictureCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new ViewCamera(this));
  }
}

class ViewCamera extends LinearLayout implements View.OnClickListener {


  final static String logtag = "ViewCamera";

  Camera camera =  null;
  SurfaceView surfaceView = null;

  public ViewCamera(Context context) {
    super(context);

    surfaceView = new SurfaceView(context);
    setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    try {
      camera = Camera.open();
      camera.setPreviewDisplay(surfaceView.getHolder());
      camera.takePicture(shuttercb, rawcb, jpgcb);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  ShutterCallback shuttercb = new Camera.ShutterCallback() {
    @Override
    public void onShutter() {
      // Put some codes when the shutter clicks. Maybe silence the shutter
    }
  };

  PictureCallback jpgcb = new PictureCallback() {
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
      String filename = String.format("/sdcard/%s.jpg", System.currentTimeMillis());
      try {
        FileOutputStream out = new FileOutputStream(filename);
        out.write(data);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
      Log.d(logtag, "JPG Callback");
    }
  };

  PictureCallback rawcb = new PictureCallback() {
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.d(logtag, "Raw Callback");
    }
  };
}
