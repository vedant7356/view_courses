package com.example.view_only_courses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Description extends AppCompatActivity {

    TextView tv1,tv2,tv3;
    Button b1;
    VideoView v1;
    String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        tv1=(TextView)findViewById(R.id.course_title);
        tv2=(TextView)findViewById(R.id.desciption);
        tv3=(TextView)findViewById(R.id.creator);
        s1=getIntent().getStringExtra("video_url");
        b1=(Button)findViewById(R.id.view_pdf_control);
        v1=(VideoView)findViewById(R.id.video_id_view);


        tv1.setText(getIntent().getStringExtra("name"));
        tv3.setText(getIntent().getStringExtra("course"));
        tv2.setText(getIntent().getStringExtra("email"));

        v1.setVideoURI(Uri.parse(s1));
        v1.setMediaController(new MediaController(this));
        v1.requestFocus();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Description.this,View_Pdf.class));
            }
        });


    }
}