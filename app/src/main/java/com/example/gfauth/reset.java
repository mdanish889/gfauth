package com.example.gfauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class reset extends AppCompatActivity {


    private EditText MailET;
    private Button ResetBtn;
    FirebaseAuth firebaseAuthObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Initilaization();
    }


    private void Initilaization()
    {
        MailET=findViewById(R.id.ResetMailET);
        ResetBtn=findViewById(R.id.ResetBTN);

        firebaseAuthObj=FirebaseAuth.getInstance();
        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=MailET.getText().toString().trim();
                if (MailET.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Your Email First",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuthObj.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Please Check Your Email",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
