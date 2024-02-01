package com.example.comedo.SignIn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedo.HomePage.HomePageActivity;
import com.example.comedo.R;
import com.example.comedo.SignIn.Presenter.SignInPresenter;
import com.example.comedo.SignIn.Presenter.SignInPresenterInterface;
import com.example.comedo.SignUp.View.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements SignInViewInterface{


    EditText emailText;
    EditText passwordText;
    Button signInButton;
    TextView signUpLink;
    SignInPresenterInterface signInPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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
    public void showToast() {
        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
    }
}
