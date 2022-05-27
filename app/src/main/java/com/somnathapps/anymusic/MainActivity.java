package com.somnathapps.anymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultAllocator;

import java.io.File;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            File directory = new File(Environment.getExternalStorageDirectory()+"/"+"anyvideo");
            if(!directory.exists()){
                directory.mkdirs();
            }

        }catch (Exception e){
            System.out.println(e);

        }

        progressBar = findViewById(R.id.load);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!Python.isStarted()){
                    Python.start(new AndroidPlatform(MainActivity.this));
                }
                Python py = Python.getInstance();
                PyObject pyObject2 = py.getModule("myscript");
                PyObject obj = pyObject2.callAttr("init", Environment.getExternalStorageDirectory().toString()+"/anyvideo/");
                String f = obj.toString();
                Log.i("o", f);
            }
        }).start();



    }

    public void Download(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Python py2 = Python.getInstance();
                PyObject pyObject = py2.getModule("myscript");
                PyObject obj = pyObject.callAttr("init");
                String f = obj.toString();
                if(f.equals("downloaded")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Video downloaded", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Could not download video", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        }).start();
    }
}