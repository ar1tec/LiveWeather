package com.maxvi.max.liveweather.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maxvi.max.liveweather.R;

public class SettingsActivity extends AppCompatActivity {
    private ImageButton mBackButton;
    private TextView mUnitsTextView;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mBackButton = (ImageButton) findViewById(R.id.home_btn_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.settings_constraint);
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                final View view = getLayoutInflater().inflate(R.layout.dialog_units, null);
                dialogBuilder.setTitle("Preferred units");
                dialogBuilder.setView(view);
                dialogBuilder.show();
            }
        });


    }


}
