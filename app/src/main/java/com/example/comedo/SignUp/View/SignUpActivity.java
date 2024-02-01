package com.example.comedo.SignUp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comedo.R;
import com.example.comedo.SignIn.View.SignInActivity;
import com.example.comedo.SignUp.Presenter.SignUpPresenter;
import com.example.comedo.SignUp.Presenter.SignUpPresenterInterface;
import com.google.firebase.FirebaseApp;

public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface {

    private EditText userNameEditText, passwordEditText;
    private Button signUpButton;
    private TextView signInLink;
    private SignUpPresenterInterface signUpPresenterInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseApp.initializeApp(SignUpActivity.this);

        signUpPresenterInterface = new SignUpPresenter(this);
        userNameEditText = findViewById(R.id.user_name_text);
        passwordEditText = findViewById(R.id.password_text);
        signUpButton = findViewById(R.id.signup_button);
        signInLink = findViewById(R.id.signin_link);
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataSignUp();
            }
        });
    }



    public void getDataSignUp() {
        String email = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        signUpPresenterInterface.signUp(email, password);

    }

    @Override
    public void signUpSuccess() {
        Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void signUpFailed(String message) {
        Toast.makeText(SignUpActivity.this, "Sign up failed. " + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
    }
}
