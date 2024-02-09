package com.example.comedo.HomePage.RandomMealFragment.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.comedo.HomePage.RandomMealFragment.Presenter.RandomMealFragmentPresenter;
import com.example.comedo.HomePage.RandomMealFragment.Presenter.RandomMealFragmentPresenterInterface;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenter;
import com.example.comedo.HomePage.SearchFragment.SearchByNameView.Presenter.SearchPresenterInterface;
import com.example.comedo.Models.DateFormatter;
import com.example.comedo.Models.IngredientWithMeasuresModel;
import com.example.comedo.Models.MealModel;
import com.example.comedo.Models.PlanDetailsConverter;
import com.example.comedo.Models.PlanDetailsModel;
import com.example.comedo.R;
import com.example.comedo.RoomDB.MealDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RandomMealFragmentView extends Fragment implements RandomMealFragmentViewInterface{

    RandomMealFragmentPresenterInterface randomMealFragmentPresenterInterface;
    SearchPresenterInterface searchPresenterInterface;
    TextView mealName,countryName,descriptionName,mealNameMain,ingredientText;
    ImageView imageView,favoriteImage,calenderImage,addedToFavorite;
    YouTubePlayerView youTubePlayerView;
    List<IngredientWithMeasuresModel> ingredientList = new ArrayList<>();
    IngredientsAdapter ingredientsAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MealModel mealModel;
    FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReferenceFavorite;
    static DatabaseReference databaseReferenceCalendar;
    Boolean favFalg;

    @Override
    public void onPause() {
        super.onPause();
        youTubePlayerView.release();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_random_meal, container, false);

        mealNameMain = view.findViewById(R.id.meal_name_text_view_2);
        mealName = view.findViewById(R.id.meal_name_text_view);
        countryName = view.findViewById(R.id.country_text_view);
        descriptionName = view.findViewById(R.id.description_text_view);
        imageView = view.findViewById(R.id.meal_image_view);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        recyclerView = view.findViewById(R.id.ingredients_recycler_view);
        favoriteImage = view.findViewById(R.id.favorite_meal_image_view);
        calenderImage = view.findViewById(R.id.calendar_meal_image_view);
        addedToFavorite = view.findViewById(R.id.added_to_favorite);


        ingredientText = view.findViewById(R.id.ingredients_text_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(FirebaseAuth.getInstance().getUid() == null){
            randomMealFragmentPresenterInterface = new RandomMealFragmentPresenter(this);
            mealModel = RandomMealFragmentViewArgs.fromBundle(getArguments()).getMealData();
            searchPresenterInterface = new SearchPresenter(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            mealNameMain.setText(mealModel.getStrMeal());
            mealName.setText(mealModel.getStrMeal());
            countryName.setText("  "+mealModel.getStrArea()+"  ");
            descriptionName.setText(mealModel.getStrInstructions());
            Glide.with(getContext()).load(mealModel.getStrMealThumb())
                    .apply(new RequestOptions().override(379, 235).centerCrop())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = getId(mealModel.getStrYoutube());
                    if (videoId == ""){
                        youTubePlayerView.release();
                    }
                    else{
                        youTubePlayer.cueVideo(videoId,0);
                    }
                }
            });
            favoriteImage.setVisibility(View.GONE);
            addedToFavorite.setVisibility(View.GONE);
            calenderImage.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            IngredientsAdapter ingredientsAdapter1 = new IngredientsAdapter(processMealModel(mealModel), getContext());
            recyclerView.setAdapter(ingredientsAdapter1);

        }else{
            randomMealFragmentPresenterInterface = new RandomMealFragmentPresenter(this);
            mealModel = RandomMealFragmentViewArgs.fromBundle(getArguments()).getMealData();
            searchPresenterInterface = new SearchPresenter(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceFavorite = firebaseDatabase.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("FavoriteMeals").child(mealModel.getIdMeal());
            databaseReferenceCalendar = firebaseDatabase.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("CalendarMeals").child(mealModel.getIdMeal());

            getMealFromId(mealModel.idMeal);
            mealNameMain.setText(mealModel.getStrMeal());
            mealName.setText(mealModel.getStrMeal());
            countryName.setText("  "+mealModel.getStrArea()+"  ");
            descriptionName.setText(mealModel.getStrInstructions());
            Glide.with(getContext()).load(mealModel.getStrMealThumb())
                    .apply(new RequestOptions().override(379, 235).centerCrop())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = getId(mealModel.getStrYoutube());
                    if (videoId == ""){
                        youTubePlayerView.release();
                    }
                    else{
                        youTubePlayer.cueVideo(videoId,0);
                    }
                }
            });
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            IngredientsAdapter categoriesAdapter = new IngredientsAdapter(processMealModel(mealModel), getContext());
            recyclerView.setAdapter(categoriesAdapter);
            favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Room
                    new Thread(()->{
                        MealDataBase.getInstance(getContext()).getMealDao().insertMeal(mealModel);
                        Log.i("TAG", "onClick: Data Successfully Added To Room");
                    }).start();
                    //RealTime
                    databaseReferenceFavorite.setValue(mealModel);

                }
            });
            calenderImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new RandomMealFragmentView.DatePickerFragment(RandomMealFragmentView.this,mealModel);
                    newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");


                }
            });
            addedToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReferenceCalendar = firebaseDatabase.getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("FavoriteMeals");
                    databaseReferenceCalendar.child(mealModel.idMeal).removeValue();
                    new Thread(()->{
                        MealDataBase.getInstance(v.getContext()).getMealDao().deleteMeal(mealModel);
                        Log.i("TAG", "onClick: Data Successfully Deleted To Room");
                    }).start();
                }
            });
        }



    }
    Context getContextInFragment(){
        Context context;
        context = getContext();
        return context;
    }
    public static void onGetDataFromRealTime() {
        RandomMealFragmentView randomMealFragmentView = new RandomMealFragmentView();
        DatabaseReference databaseReferenceFavorite = FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid()).child("FavoriteMeals");
        databaseReferenceFavorite.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MealModel> mealModelList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Assuming each child under "favorite" node is a MealModel object
                    MealModel mealModel = dataSnapshot.getValue(MealModel.class);
                    mealModelList.add(mealModel);
                }
                new Thread(()->{
                    for(int i=0;i<mealModelList.size();i++)
                    MealDataBase.getInstance(randomMealFragmentView.getContextInFragment()).getMealDao().insertMeal(mealModelList.get(i));
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
                    // Assuming each child under "calendar" node is a PlanDetailsModel object
                    PlanDetailsModel planDetailsModel = dataSnapshot.getValue(PlanDetailsModel.class);
                    planDetailsModelList.add(planDetailsModel);
                }
                new Thread(()->{
                    for(int i=0;i<planDetailsModelList.size();i++)
                        MealDataBase.getInstance(randomMealFragmentView.getContextInFragment()).getMealDao().insertMealPlan(planDetailsModelList.get(i));
                }).start();
                // Do something with the retrieved PlanDetailsModel list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

    public static List<IngredientWithMeasuresModel> processMealModel(MealModel mealModel) {
        List<IngredientWithMeasuresModel> ingredientList = new ArrayList<>();

        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient1(), mealModel.getStrMeasure1());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient2(), mealModel.getStrMeasure2());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient3(), mealModel.getStrMeasure3());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient4(), mealModel.getStrMeasure4());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient5(), mealModel.getStrMeasure5());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient6(), mealModel.getStrMeasure6());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient7(), mealModel.getStrMeasure7());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient8(), mealModel.getStrMeasure8());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient9(), mealModel.getStrMeasure9());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient10(), mealModel.getStrMeasure10());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient11(), mealModel.getStrMeasure11());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient12(), mealModel.getStrMeasure12());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient13(), mealModel.getStrMeasure13());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient14(), mealModel.getStrMeasure14());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient15(), mealModel.getStrMeasure15());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient16(), mealModel.getStrMeasure16());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient17(), mealModel.getStrMeasure17());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient18(), mealModel.getStrMeasure18());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient19(), mealModel.getStrMeasure19());
        addIngredientIfNotNull(ingredientList, mealModel.getStrIngredient20(), mealModel.getStrMeasure20());


        return ingredientList;
    }
    private static void addIngredientIfNotNull(List<IngredientWithMeasuresModel> list, String ingredient, String measure) {
        if (!TextUtils.isEmpty(ingredient) && !TextUtils.isEmpty(measure)) {
            list.add(new IngredientWithMeasuresModel(ingredient, measure));
        }
    }

    public String getId(String link) {
        if (link != null && link.split("\\?v=").length > 1)
            return link.split("\\?v=")[1];
        else return "";
    }
    @Override
    public void onSuccessRandomMeal(MealModel mealModel) {
        mealNameMain.setText(mealModel.getStrMeal());
        mealName.setText(mealModel.getStrMeal());
        countryName.setText("  "+mealModel.getStrArea()+"  ");
        descriptionName.setText(mealModel.getStrInstructions());
        Glide.with(getContext()).load(mealModel.getStrMealThumb())
                .apply(new RequestOptions().override(379, 235).centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);



        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getId(mealModel.getStrYoutube());
                if (videoId == ""){
                    youTubePlayerView.release();
                }
                else{
                    youTubePlayer.cueVideo(videoId,0);
                }
            }
        });
    }

    @Override
    public void onFailureRandomMeal() {

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        RandomMealFragmentView mealFragmentView;
        MealModel mealModel;
        public DatePickerFragment(RandomMealFragmentView planFragment,MealModel mealModel)
        {
            this.mealFragmentView =planFragment;
            this.mealModel = mealModel;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            c.add(Calendar.DAY_OF_MONTH, 7);
            long maxDate = c.getTimeInMillis();
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            PlanDetailsModel planDetailsModel = PlanDetailsConverter.getMealPlannerFromMealAndDate(mealModel, DateFormatter.getString(year, month, day), 0);
            //room
            new Thread(()->{
                MealDataBase.getInstance(getActivity()).getMealDao().insertMealPlan(planDetailsModel);
                Log.i("TAG", "onClick: Data Successfully Added To Room");
            }).start();

            //real time database
            RandomMealFragmentView.databaseReferenceCalendar.setValue(planDetailsModel);
        }
    }
    public void getMealFromId(String id){
        MealDataBase.getInstance(getContext()).getMealDao().getMealsById(id)
        .observe(getViewLifecycleOwner(), new Observer<List<MealModel>>() {
            @Override
            public void onChanged(List<MealModel> mealsItems) {
                if(mealsItems != null && !mealsItems.isEmpty()){
                    favFalg = true;
                    favoriteImage.setVisibility(View.GONE);
                    addedToFavorite.setVisibility(View.VISIBLE);
                }else{
                    favFalg = false;
                    addedToFavorite.setVisibility(View.GONE);
                    favoriteImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
