package com.example.view_only_courses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

public class Create_new_courses extends AppCompatActivity {

    VideoView videoView;
    Button browse,upload,pdf_upload;
    Uri videouri,filepath;
    MediaController mediaController;
    StorageReference storageReference;
    EditText et1,et2,et3;
    DatabaseReference databaseReference;
    String pdf,image,pdf_data;
   Integer flag1=0,flag2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_courses);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("students");

        image="https://firebasestorage.googleapis.com/v0/b/viewcoursesdata.appspot.com/o/Pictures%2Fprofile.jpg?alt=media&token=bff270bf-a7b9-4ab1-bea2-07625ddbb427";
        et1=(EditText)findViewById(R.id.Enter_title_here);
        et2=(EditText)findViewById(R.id.Enter_username_here);
        et3=(EditText)findViewById(R.id.Enter_desc_here);
        videoView=(VideoView)findViewById(R.id.videoView);
        pdf_upload=(Button)findViewById(R.id.upload_pdf);
        upload=(Button)findViewById(R.id.upload);
        browse=(Button)findViewById(R.id.browse);
        mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        if (flag1==0 && flag2==0){
            upload.setEnabled(false);

        }
        else if(flag1==1 && flag2==2) {
            upload.setEnabled(true);
        }
        else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
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

        pdf_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Pdf Files"),1);
                upload.setEnabled(true);

                //startActivity(new Intent(Create_new_courses.this, PDF_UPLOAD.class));

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title,bywhom,desc;
                title=et1.getText().toString();
                bywhom=et2.getText().toString();
                desc=et3.getText().toString();

                if(TextUtils.isEmpty(title)){
                    et1.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(bywhom)){
                    et2.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    et3.setError("Password is Required.");
                    return;
                }
                processvideouploading();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 101:
                videouri = data.getData();
                videoView.setVideoURI(videouri);
                Toast.makeText(this, "Video Updated", Toast.LENGTH_SHORT).show();
                flag1=1;
                break;
            case 1:
                filepath=data.getData();
                Toast.makeText(this, "PDF Updated", Toast.LENGTH_SHORT).show();
                flag2=2;
                break;

            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }

    }
    public String getExtension()
    {
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videouri));
    }

    public void processvideouploading()
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Media Uploader");
        pd.show();


        final StorageReference reference=storageReference.child("pdf_files/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pdf_data=uri.toString();
                    }
                });

            }
        });

        final StorageReference uploader=storageReference.child("videos/"+System.currentTimeMillis()+"."+getExtension());
        uploader.putFile(videouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                               model model=new model(et2.getText().toString(),et3.getText().toString(),et1.getText().toString(),image,uri.toString(),pdf_data);
                                databaseReference.child(databaseReference.push().getKey()).setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(getApplicationContext(),"Successfully uploaded", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(Create_new_courses.this, MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :"+(int)per+"%");
                    }
                });

    }



}