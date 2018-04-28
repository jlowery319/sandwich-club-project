package com.udacity.sandwichclub.utils;

import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        JSONObject sandwichObject = null;

        try {
            sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject("name");
            sandwich.setMainName(nameObject.getString("mainName"));
            // Also Known As: put all strings from JSONArray into a List<String> so we can set value in sandwich object
            List<String> akaList = new ArrayList<String>();
            JSONArray akaJSONArray = nameObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < akaJSONArray.length(); i++) {
                String akaStr = akaJSONArray.getString(i);
                akaList.add(akaStr);
            }
            sandwich.setAlsoKnownAs(akaList);

            sandwich.setDescription(sandwichObject.getString("description"));

            sandwich.setPlaceOfOrigin(sandwichObject.getString("placeOfOrigin"));

            // ingredients - array
            List<String> ingredientsList = new ArrayList<String>();
            JSONArray ingredientArray = sandwichObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientArray.length(); i++) {
                String ingStr = ingredientArray.getString(i);
                ingredientsList.add(ingStr);
            }
            sandwich.setIngredients(ingredientsList);

            // image
            sandwich.setImage(sandwichObject.getString("image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sandwich;
    }
}
