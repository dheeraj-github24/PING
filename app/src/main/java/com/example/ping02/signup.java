package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class signup extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button dateText;
    Button submit;

    EditText fname,lname,email,new_password,con_password;
    FirebaseAuth auth;
    DatabaseReference reference;

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser fu=auth.getCurrentUser();
        if(fu!=null){
            Intent intent=new Intent(getApplicationContext(),welcomepage.class);
            startActivity(intent);
        }
    }

    private static final String TAG = "MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        fname=findViewById(R.id.editTextTextPersonName);
        lname=findViewById(R.id.editTextTextPersonName2);
        email=findViewById(R.id.editTextTextEmailAddress);
        new_password=findViewById(R.id.editTextTextPassword);
        con_password=findViewById(R.id.editTextTextPassword2);

        auth=FirebaseAuth.getInstance();

        dateText=findViewById(R.id.Date_input);

        findViewById(R.id.Date_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        submit = findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_fname=fname.getText().toString();
                String txt_lname=lname.getText().toString();
                String txt_dob=dateText.getText().toString();
                String txt_email=email.getText().toString();
                String txt_newpassword=new_password.getText().toString();
                String txt_conpassword=con_password.getText().toString();

                if(TextUtils.isEmpty(txt_fname) || TextUtils.isEmpty(txt_lname) || TextUtils.isEmpty(txt_dob) || TextUtils.isEmpty(txt_email)
                        || TextUtils.isEmpty(txt_newpassword) || TextUtils.isEmpty(txt_conpassword)){
                    Toast.makeText(signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if(txt_newpassword.length()<8){
                    Toast.makeText(signup.this, "Password must be of at least 8 characters", Toast.LENGTH_SHORT).show();
                }else if(!txt_conpassword.equals(txt_newpassword)){
                    Toast.makeText(signup.this, "Passwords are not matching!", Toast.LENGTH_SHORT).show();
                }else {
                    register(txt_fname,txt_lname,txt_dob,txt_email,txt_conpassword);
                }
            }
        });

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);


    }
    

    private void signIn(){
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Sign in Failed!", Toast.LENGTH_SHORT).show();;
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent=new Intent(getApplicationContext(),welcomepage.class);
                            startActivity(intent);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(signup.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "M/D/Y " + month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void register(String fname, String lname, String dateText, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(signup.this, "Email verication has been sent to your Email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: Email not sent "+e.getMessage());
                                }
                            });

                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("Id",userid);
                            hashMap.put("Firstname",fname);
                            hashMap.put("Lastname",lname);
                            hashMap.put("Date of Birth",dateText);
                            hashMap.put("Email",email);
                            hashMap.put("Password",password);
                            hashMap.put("ImageURL","default");
                            hashMap.put("Designation","");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(signup.this, login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(signup.this, "This ID is already taken!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}