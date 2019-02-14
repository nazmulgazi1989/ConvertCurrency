package com.nazmul.currency;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryAdapter extends ArrayAdapter<JSONObject> {

    // Your sent context
    private Context context;
    // Your custom jsonArray for the spinner (User)
    private JSONArray jsonArray;

    public CountryAdapter(Context context, int textViewResourceId,
                       JSONArray values) {
        super(context, textViewResourceId);
        this.context = context;
        this.jsonArray = values;
    }

    @Override
    public int getCount(){
        return jsonArray.length();
    }

    @Override
    public JSONObject getItem(int position){
        try {
            return jsonArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the jsonArray array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        try {
            label.setText(jsonArray.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        try {
            label.setText(jsonArray.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return label;
    }
}
