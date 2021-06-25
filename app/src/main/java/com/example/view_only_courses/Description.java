package com.example.view_only_courses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ui.PlayerView;

public class Description extends AppCompatActivity {


    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;

    TextView tv1,tv2,tv3;
    Button b1;
    VideoView v1;
    String s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        tv1=(TextView)findViewById(R.id.course_title);
        tv2=(TextView)findViewById(R.id.desciption);
        tv3=(TextView)findViewById(R.id.creator);
        s1=getIntent().getStringExtra("video_url");
        s2=getIntent().getStringExtra("pdf_url");
        b1=(Button)findViewById(R.id.view_pdf_control);
        v1=(VideoView)findViewById(R.id.video_id_view);


        tv1.setText(getIntent().getStringExtra("name"));
        tv3.setText(getIntent().getStringExtra("course"));
        tv2.setText(getIntent().getStringExtra("email"));

        v1.setVideoURI(Uri.parse(s1));
        v1.setMediaController(new MediaController(this));
        v1.requestFocus();

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(5000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*50/(5000/1000));

            }

            @Override
            public void onFinish() {
                v1.start();
                Toast.makeText(Description.this, "Playing VIDEO.....", Toast.LENGTH_SHORT).show();
                i++;
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.GONE);
            }
        };
        mCountDownTimer.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Description.this,View_Pdf.class );
               intent.putExtra("PDF_URL_FINAL",s2);
               startActivity(intent);
            }
        });


    }
}