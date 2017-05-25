package com.maxvi.max.liveweather.activities;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maxvi.max.liveweather.R;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton mBackButton;
    private TextView mUnitsHintTextView;
    private ConstraintLayout mUnitsConstraintLayout;
    private LinearLayout mLocationLinearLayout;
    private TextView mLocationHintTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        setOnClickListeners();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String unitsHint = sharedPreferences.getString(getString(R.string.pref_units), getString(R.string.unit_celsius));
        mUnitsHintTextView.setText(unitsHint);
    }

    private void findViews() {
        mBackButton = (ImageButton) findViewById(R.id.home_btn_back);
        mUnitsConstraintLayout = (ConstraintLayout) findViewById(R.id.settings_clickable_units);
        mLocationLinearLayout = (LinearLayout) findViewById(R.id.settings_clickable_location);
        mUnitsHintTextView = (TextView) findViewById(R.id.settings_tv_units_hint);
        mLocationHintTextView = (TextView) findViewById(R.id.settings_tv_location_hint);
    }

    private void setOnClickListeners() {
        if (mBackButton != null) {
            mBackButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (mUnitsConstraintLayout != null) {
            mUnitsConstraintLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.dialog_units, null);

                    final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.dialog_radio_group);
                    final Checkable celsuisRadioButton = (Checkable) view.findViewById(
                            R.id.dialog_rb_btn_celsius);
                    final Checkable fahrenheitRadioButton = (Checkable) view.findViewById(
                            R.id.dialog_rb_btn_fahrenheit);

                    final SharedPreferences preferences = PreferenceManager
                            .getDefaultSharedPreferences(SettingsActivity.this);

                    final String checkedUnit = preferences.getString(getString(R.string.pref_units),
                            getString(R.string.unit_celsius));
                    if (checkedUnit.equals(getString(R.string.unit_celsius))) {
                        celsuisRadioButton.setChecked(true);
                    } else if (checkedUnit.equals(getString(R.string.unit_fahrenheit))) {
                        fahrenheitRadioButton.setChecked(true);
                    }

                    final SharedPreferences.Editor editor = preferences.edit();

                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(final RadioGroup group,
                                                     @IdRes final int checkedId) {
                            switch (checkedId) {
                                case R.id.dialog_rb_btn_celsius:
                                    mUnitsHintTextView.setText(getString(R.string.unit_celsius));
                                    celsuisRadioButton.setChecked(true);
                                    editor.putString(getString(R.string.pref_units),
                                            getString(R.string.unit_celsius));
                                    editor.apply();
                                    break;
                                case R.id.dialog_rb_btn_fahrenheit:
                                    mUnitsHintTextView.setText(getString(R.string.unit_fahrenheit));
                                    fahrenheitRadioButton.setChecked(true);
                                    editor.putString(getString(R.string.pref_units),
                                            getString(R.string.unit_fahrenheit));
                                    editor.apply();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    dialogBuilder.setTitle("Preferred units");
                    dialogBuilder.setView(view);
                    dialogBuilder.show();
                }
            });
        }

        if (mLocationLinearLayout != null) {
            mLocationLinearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.dialog_location, null);
                    dialogBuilder.setTitle("Select location to display");
                    dialogBuilder.setView(view);
                    dialogBuilder.show();
                }
            });
        }

    }

}
