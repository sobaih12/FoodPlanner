package com.example.comedo.SignIn.View;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comedo.HomePage.HomePageActivity;
import com.example.comedo.R;
import com.example.comedo.SignIn.Presenter.SignInPresenter;
import com.example.comedo.SignIn.Presenter.SignInPresenterInterface;
import com.example.comedo.SignUp.View.SignUpActivity;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity implements SignInViewInterface{

    static String TAG = "TAG";
    EditText emailText;
    EditText passwordText;
    Button signInButton,googleSignIN;
    TextView signUpLink;
    SignInPresenterInterface signInPresenterInterface;
    GoogleSignInClient googleSignInClient;
    ShapeableImageView imageView;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
//        FirebaseApp.initializeApp(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("86288114919-vp6d9tqi4ujbkd0oj5beunb8u4kmsboa.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(SignInActivity.this,signInOptions);

        googleSignIN = findViewById(R.id.google_botton);
        googleSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,100);
                
            }
        });

        emailText = findViewById(R.id.user_name_text);
        passwordText = findViewById(R.id.password_text);
        signInButton = findViewById(R.id.signin_button);
        signUpLink = findViewById(R.id.signup_link);
        signInPresenterInterface = new SignInPresenter(this);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getSignIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        signInPresenterInterface.checkIfSignedIn();
    }

    @Override
    public void getSignIn() {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            signInPresenterInterface.signIn(email, password);
    }

    @Override
    public void successSignUp() {

        Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failedSignUp(String message) {

        Toast.makeText(SignInActivity.this, "Authentication failed. " + message,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == 100) {
                Log.i(TAG, "onActivityResult: ============================" + GoogleSignIn.getSignedInAccountFromIntent(data).getResult().toString());
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                Log.i(TAG, "onActivityResult: ============================" + signInAccountTask.getResult().getDisplayName());

                if (signInAccountTask.isSuccessful()) {

                    String s = "Google sign in successful";
                    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            auth.signInWithCredential(authCredential).addOnCompleteListener(this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(SignInActivity.this, s, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(SignInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "mike", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "michael hena", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
    }
//    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult activityResult) {
//            if (activityResult.getResultCode() == RESULT_OK){
//
//                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(activityResult.getData());
//                try {
//                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
//                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
//                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                auth = FirebaseAuth.getInstance();
//                                Intent intent = new Intent(SignInActivity.this,HomePageActivity.class);
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                } catch (ApiException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    });
}
