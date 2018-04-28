package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        //Toast.makeText(this, json, Toast.LENGTH_LONG).show();
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // display main name
        TextView mainNameTV = findViewById(R.id.mainname_tv);
        mainNameTV.setText(sandwich.getMainName());

        // display also known as
        TextView akaTV = findViewById(R.id.also_known_tv);
        List<String> akaList = sandwich.getAlsoKnownAs();
        String akaString = "";
        for (int i = 0; i < akaList.size(); i++) {
            akaString += akaList.get(i);
            if (i < akaList.size()-1) {
                akaString += ", ";
            }
        }
        akaTV.setText(akaString);

        // display description
        TextView descriptionTV = findViewById(R.id.description_tv);
        descriptionTV.setText(sandwich.getDescription());

        // display origin
        TextView originTV = findViewById(R.id.origin_tv);
        originTV.setText(sandwich.getPlaceOfOrigin());

        // display ingredients
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        List<String> ingList = sandwich.getIngredients();
        String ingStr = "";
        for (int i = 0; i < ingList.size(); i++) {
            ingStr += ingList.get(i);
            if (i < ingList.size()-1) {
                ingStr += ", ";
            }
        }
        ingredientsTV.setText(ingStr);


    }
}
