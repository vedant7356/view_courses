package com.example.view_only_courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class View_Pdf extends AppCompatActivity {

    TextView t1;
    PDFView pdfView;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    //DatabaseReference databaseReference=firebaseDatabase.getReference().child("students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pdf);

        // String fileurl=getIntent().getStringExtra("pdf_url");

        pdfView = (PDFView) findViewById(R.id.pdf_viewer);
        t1 = (TextView) findViewById(R.id.text1);

        t1.setText(getIntent().getStringExtra("PDF_URL_FINAL"));
        String url = t1.getText().toString();

        // PLS IGNORE url="https://firebasestorage.googleapis.com/v0/b/viewcoursesdata.appspot.com/o/uploads%2F1624632351770.pdf?alt=media&token=b3c53581-d970-4059-b213-694d6fa77984";

           //  PLS IGNORE Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        new RetrivePdfStream().execute(url);
    }

    class RetrivePdfStream extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try
            {

                URL url= new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            super.onPostExecute(inputStream);
        }
    }
}