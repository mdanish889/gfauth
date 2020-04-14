package com.example.gfauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity<GoogleSignInClient> extends AppCompatActivity {
    FirebaseAuth firebaseAuthObj;
    private Button googleBTN,facebookBTN;

    GoogleSignInClient googleSignInClientObj;
    private static final int Code=123;

    private TextView ResetTV;
    Intent intentObj;
    private GoogleApiClient GoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void intialization()
    {

        intentObj=new Intent(getApplicationContext(),reset.class);
        ResetTV=findViewById(R.id.ResetLinkTV);
        googleBTN=findViewById(R.id.GoogleBTN);
        googleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSlection();
            }
        });

        ResetTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(intentObj);
                return false;
            }
        });

        facebookBTN=findViewById(R.id.FaceBookBTN);

        firebaseAuthObj=FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptionsObj=new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();


        googleSignInClientObj= GoogleSignIn.getClient(this,googleSignInOptionsObj);

    }

    private void AccountSlection()
    {
        Intent intentObj=googleSignInClientObj.getSignInInt();
        startActivityForResult(intentObj,Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Code)
        {
            Task<GoogleSignInAccount> taskObj=GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                GoogleSignInAccount googleSignInAccountObj=taskObj.getResult(ApiException.class);
                if (googleSignInAccountObj!=null)
                {
                    FirebaseSignIn(googleSignInAccountObj);
                }
            }catch (Exception e)
            {
                Toast.makeText(this,"Problmes..."+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void FirebaseSignIn(GoogleSignInAccount googleSignInAccountObj)
    {
        Log.d("TAG","FirebaseAuthentication"+googleSignInAccountObj.getId());
        AuthCredential authCredentialObj= GoogleAuthProvider.getCredential(googleSignInAccountObj.getIdToken(),null);

        firebaseAuthObj.signInWithCredential(authCredentialObj)
                .addOnCompleteListener(this,task->{
                    if (task.isSuccessful())
                    {
                        Toast.makeText(this,"Your Sign In Is Successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this,"Your Sign In Failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
