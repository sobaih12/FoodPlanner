package com.example.comedo.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.comedo.R;
import com.example.comedo.SignIn.View.SignInActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;


public class HomePageActivity extends AppCompatActivity  {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView,navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.randomMealFragment){
                    navigationView.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.searchByAreaView) {
                    navigationView.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.searchByCategoryView) {
                    navigationView.setVisibility(View.GONE);
                } else if (navDestination.getId() == R.id.searchByIngredientsView) {
                    navigationView.setVisibility(View.GONE);
                } else{
                    navigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if (SignInActivity.isGuestMode == true) {
                int itemId = item.getItemId();
                if (itemId == R.id.profileFragment || itemId == R.id.calenderFragment || itemId == R.id.favouriteFragment) {
                    showGuestModeMessage();
                    return false;
                }
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

    }
    public void showGuestModeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign In For More Features");
        builder.setMessage("Add your food preferences, shop your recipes, plan your meals and more!");

        builder.setPositiveButton("SIGN IN", (dialog, which) -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}