package com.example.comedo.SignIn.Presenter;



import android.util.Log;


import androidx.annotation.NonNull;
import com.example.comedo.SignIn.View.SignInViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInPresenter implements SignInPresenterInterface{
    private FirebaseAuth mAuth;
    SignInViewInterface signInViewInterface;


    public SignInPresenter(SignInViewInterface signInViewInterface) {
        this.signInViewInterface = signInViewInterface;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email ,String password) {

        if (email.isEmpty() || password.isEmpty()) {
            signInViewInterface.showToast();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                signInViewInterface.successSignUp();
                                Log.i("TAG", "onComplete: Mostafa");

                            } else {
                                signInViewInterface.failedSignUp(task.getException().getMessage());
                            }
                        }
                    });
        }
    }

    @Override
    public void checkIfSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            signInViewInterface.successSignUp();
        }
    }


}
