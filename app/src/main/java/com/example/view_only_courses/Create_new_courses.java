package com.example.view_only_courses;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

public class Create_new_courses extends AppCompatActivity {

    VideoView videoView;
    Button browse,upload;
    Uri videouri;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_courses);

        videoView=(VideoView)findViewById(R.id.videoView);
        upload=(Button)findViewById(R.id.upload);
        browse=(Button)findViewById(R.id.browse);
        mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent,101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            videouri=data.getData();
            videoView.setVideoURI(videouri);

        }

    }
}