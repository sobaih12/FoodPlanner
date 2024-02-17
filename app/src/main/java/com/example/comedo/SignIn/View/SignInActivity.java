package com.example.comedo.SignIn.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.example.comedo.HomePage.HomePageActivity;
import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.PlanDetailsModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;
import com.example.comedo.SignIn.Presenter.SignInPresenter;
import com.example.comedo.SignIn.Presenter.SignInPresenterInterface;
import com.example.comedo.SignUp.View.SignUpActivity;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements SignInViewInterface{

    EditText emailText;
    EditText passwordText;
    Button signInButton,googleSignIN,guestMode,signUpLink;
    SignInPresenterInterface signInPresenterInterface;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    public static Boolean isGuestMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("86288114919-vp6d9tqi4ujbkd0oj5beunb8u4kmsboa.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(SignInActivity.this,signInOptions);

        googleSignIN = findViewById(R.id.google_botton);
        guestMode = findViewById(R.id.guest_button);
        googleSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,100);
                isGuestMode = false;

            }
        });
        guestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGuestMode = true;
                Intent intent = new Intent(SignInActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        emailText = findViewById(R.id.user_name_text);
        passwordText = findViewById(R.id.password_text);
        signInButton = findViewById(R.id.signin_in_button);
        signUpLink = findViewById(R.id.signup_link_button);
        signInPresenterInterface = new SignInPresenter(this);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGuestMode = false;
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isGuestMode = false;
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
        DatabaseReference databaseReferenceFavorite = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("FavoriteMeals");
        databaseReferenceFavorite.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MealModel> mealModelList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MealModel mealModel = dataSnapshot.getValue(MealModel.class);
                    mealModelList.add(mealModel);
                }
                new Thread(()->{
                    for(int i=0;i<mealModelList.size();i++)
                        MealDataBase.getInstance(getApplicationContext()).getMealDao().insertMeal(mealModelList.get(i));
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());
            }
        });

        DatabaseReference databaseReferenceCalendar = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("CalendarMeals");
        databaseReferenceCalendar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PlanDetailsModel> planDetailsModelList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PlanDetailsModel planDetailsModel = dataSnapshot.getValue(PlanDetailsModel.class);
                    planDetailsModelList.add(planDetailsModel);
                }
                new Thread(()->{
                    for(int i=0;i<planDetailsModelList.size();i++)
                        MealDataBase.getInstance(getApplicationContext()).getMealDao().insertMealPlan(planDetailsModelList.get(i));
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());
            }
        });
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
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
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
                }
            }
        }
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
    }
}
