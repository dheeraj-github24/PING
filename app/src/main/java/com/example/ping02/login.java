package com.example.ping02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    Button submit;
    TextView forgotpass;
    TextView signupfromlogin;

    EditText email,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();

        email=findViewById(R.id.emailid);
        password=findViewById(R.id.password);

        forgotpass=findViewById(R.id.forgot_password);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotpass_page();
            }
        });

        signupfromlogin=findViewById(R.id.signup_shortcut);
        signupfromlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_shortcut();
            }
        });

        submit=(Button)findViewById(R.id.Submit_buttonlogin);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(login.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(txt_email,txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if(auth.getCurrentUser().isEmailVerified()){
                                            Intent intent=new Intent(login.this, welcomepage.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(login.this, "Please verify your email to login.", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(login.this, "Breach failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }


    public void forgotpass_page(){
        Intent intenttoforgotpass = new Intent(this, forgotpassword.class);
        startActivity(intenttoforgotpass);
    }

    public void signup_shortcut(){
        Intent intenttosignupfromlogin = new Intent(this, signup.class);
        startActivity(intenttosignupfromlogin);

    }
}