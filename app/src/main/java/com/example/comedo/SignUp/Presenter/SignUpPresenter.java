package com.example.comedo.SignUp.Presenter;

import androidx.annotation.NonNull;

import com.example.comedo.SignUp.View.SignUpViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter implements SignUpPresenterInterface {

    private FirebaseAuth firebaseAuth;
    private SignUpViewInterface signUpViewInterface;

    public SignUpPresenter(SignUpViewInterface signUpViewInterface) {
        this.signUpViewInterface = signUpViewInterface;
    }

    @Override
    public void signUp(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            signUpViewInterface.showToast();
        }
        else {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                signUpViewInterface.signUpSuccess();

                            } else {
                                signUpViewInterface.signUpFailed(task.getException().getMessage());
                            }
                        }
                    });
        }
    }
}
