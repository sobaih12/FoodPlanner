package com.example.comedo.OnBoardingScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.comedo.R;
import com.example.comedo.SignIn.View.SignInActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    TextView skipTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        skipTextView = findViewById(R.id.skipTextView);
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


        fragmentManager = getSupportFragmentManager();


        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView, paperOnboardingFragment);
        fragmentTransaction.commit();



    }
    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        PaperOnboardingPage source = new PaperOnboardingPage("Foodies", "Find your Favourite food here ... ",Color.parseColor("#FFFFFFFF"),R.drawable.chef_serving_food_at_hotel, R.drawable.face_savoring_food);
        PaperOnboardingPage source1 = new PaperOnboardingPage("Weclome To Foodies", "Find your Recepies and Ingredients also ...", Color.parseColor("#FFFFFFFF"),R.drawable.chef_serving_food_at_hotel, R.drawable.face_savoring_food);
        PaperOnboardingPage source2 = new PaperOnboardingPage("Let's Go", "Became one of our family ..",Color.parseColor("#FFFFFFFF"),R.drawable.chef_serving_food_at_hotel, R.drawable.face_savoring_food);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        elements.add(source);
        elements.add(source1);
        elements.add(source2);

        return elements;
    }

}