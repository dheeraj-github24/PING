package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ping02.Adapter.User_Adapter;
import com.example.ping02.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.UUID;

import static java.security.AccessController.getContext;

public class settings extends AppCompatActivity {

    private ImageView designationedit;
    private EditText infoDesignation;
    private Button logout;
    private ImageButton profilepic;
    private Button UpdateDesignation;
    private TextView fname;
    private TextView email;


    private Uri uri;
    private FirebaseStorage storage;
    private StorageReference sr;
    private FirebaseAuth fbAuth;

    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        fname=findViewById(R.id.info_fullname);
        email=findViewById(R.id.info_email);
        
        storage=FirebaseStorage.getInstance();
        sr=storage.getReference();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String userid=firebaseUser.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        profilepic=findViewById(R.id.profilepicture);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePicture();
            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this, MainActivity.class));
                finish();
                FirebaseAuth.getInstance().signOut();
            }
        });

        designationedit=findViewById(R.id.designationedit);
        infoDesignation=findViewById(R.id.info_designation);
        designationedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDesignation.setVisibility(View.VISIBLE);
                showSoftKeyboard(designationedit);
            }
        });

        UpdateDesignation=findViewById(R.id.UpdateButton);
        UpdateDesignation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_designation=infoDesignation.getText().toString();
                updateDesignation(txt_designation);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                fname.setText(user.getFirstname());
                email.setText(user.getEmail());
                infoDesignation.setText(user.getDesignation());
                if(user.getImageURL().equals("default")){
                    profilepic.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(settings.this).load(user.getImageURL()).into(profilepic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateDesignation(String txt_designation) {
        HashMap<String, Object> map=new HashMap<>();
        map.put("Designation",txt_designation);
        reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.updateChildren(map);
        Snackbar.make(findViewById(android.R.id.content), "Designation Updated!", Snackbar.LENGTH_LONG).show();
    }

    private void showSoftKeyboard(ImageView designationedit) {
        if(designationedit.requestFocus()){

            InputMethodManager imm=(InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(designationedit,InputMethodManager.SHOW_IMPLICIT);
        }
    }


    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri=data.getData();
            profilepic.setImageURI(uri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading....");
        pd.show();

        if(uri!=null) {
            final String randomkey = UUID.randomUUID().toString();
            StorageReference riversRef = sr.child("images/" + randomkey);
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Uri downloadUri=task.getResult();
                                        String mUri=downloadUri.toString();

                                        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                                        HashMap<String, Object> map=new HashMap<>();
                                        map.put("ImageURL",mUri);
                                        reference.updateChildren(map);
                                    }
                                }
                            });
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded!", Snackbar.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(settings.this, "Failed to Upload!", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progresspercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Progress: " + (int) progresspercent + "%");
                        }
                    });
        }
    }
}
