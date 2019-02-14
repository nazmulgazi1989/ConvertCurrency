package com.nazmul.currency;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ScrollingActivity extends AppCompatActivity {

    private LinearLayout lnlParent;
    private RadioGroup rgGroup;
    private Spinner spnCountry;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private JSONObject jsonObjectCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        lnlParent = findViewById(R.id.lnlParent);
        rgGroup = findViewById(R.id.rgGroup);
        spnCountry = findViewById(R.id.spnCountry);

        String errorMessageJson = readJSONData("sample.json");
        try {
            jsonObject = new JSONObject(errorMessageJson);
            jsonArray = jsonObject.getJSONArray("rates");
            CountryAdapter countryAdapter = new CountryAdapter(this, R.layout.spinner_prompt_profile, jsonArray);
            spnCountry.setAdapter(countryAdapter);

            spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        setCountryInterface( position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setCountryInterface( int position) throws JSONException {
        if (jsonArray != null) {
            try {
                jsonObjectCountry = jsonArray.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonType = jsonObjectCountry.getJSONArray("periods").getJSONObject(0).getJSONObject("rates");
        for (int i = 0; i < jsonType.names().length(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins((int) getResources().getDimension(R.dimen._10sdp), (int) getResources().getDimension(R.dimen._5sdp), 0, 0);
            radioButton.setLayoutParams(params);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setTag(jsonType.names().getString(i));
            radioButton.setText(jsonType.names().getString(i));
            radioButton.setTextSize(R.dimen._13ssp);

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("tttt", "Check change" + buttonView.getTag());
                    Toast.makeText(ScrollingActivity.this, "Check change" + buttonView.getTag(), Toast.LENGTH_SHORT).show();
                }
            });

            rgGroup.addView(radioButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    protected String readJSONData(String fileName) {
        try {
            InputStream inputStream = getApplicationContext().getAssets().open(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
