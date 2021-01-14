package com.example.fidelitycontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ratingBar=findViewById(R.id.ratingBar);

        SharedPreferences sharedPreferences=getSharedPreferences("AppPrefs",0);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                editor.putFloat("rating", rating);
                editor.apply();

            }
        });
        float rating = sharedPreferences.getFloat("rating", 0f);
        ratingBar.setRating(rating);
    }

}