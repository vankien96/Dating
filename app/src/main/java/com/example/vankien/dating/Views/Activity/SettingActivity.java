package com.example.vankien.dating.Views.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vankien.dating.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgBtnBack, imgBtnSave;
    private Switch swPrivacy, swMen, swWomen;
    private SeekBar seekBarDistance;
    private TextView tvDistance,tvAgeFrom, tvAgeTo;
    private RelativeLayout rlAgeFrom, rlAgeTo;
    private boolean isPrivacy, isLookingMen, isLookingWomen;
    private int distance, ageFrom, ageTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getDataFromSharedPreference();
        init();
        settingView();
        addEvent();
    }

    public void init(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSave = (ImageButton) findViewById(R.id.imgBtnSave);
        swPrivacy = (Switch) findViewById(R.id.swPrivacy);
        swMen = (Switch) findViewById(R.id.swMen);
        swWomen = (Switch) findViewById(R.id.swWomen);
        seekBarDistance = (SeekBar) findViewById(R.id.seekBarDistance);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvAgeFrom = (TextView) findViewById(R.id.tvAgeFrom);
        tvAgeTo = (TextView) findViewById(R.id.tvAgeTo);
        rlAgeFrom = (RelativeLayout) findViewById(R.id.rlAgeFrom);
        rlAgeTo = (RelativeLayout) findViewById(R.id.rlAgeTo);
    }

    public void addEvent(){
        imgBtnSave.setOnClickListener(this);
        imgBtnBack.setOnClickListener(this);
        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvDistance.setText(distance+" km");
            }
        });
        rlAgeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
        rlAgeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
    }

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose age");
        final String[] ages = {"15","20","25","30"};
        builder.setItems(ages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String selectedItem = ages[item].toString();
                tvAgeFrom.setText(selectedItem);
                ageFrom = Integer.parseInt(selectedItem);
                Toast.makeText(getApplicationContext(),selectedItem,Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void putDataToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySetting",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isPrivacy",swPrivacy.isChecked());
        editor.putBoolean("isLookingMen",swMen.isChecked());
        editor.putBoolean("isLookingWomen",swWomen.isChecked());
        editor.putInt("distance",distance);
        editor.putInt("age_from",ageFrom);
        editor.commit();
    }
    public void getDataFromSharedPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySetting",MODE_PRIVATE);
        isPrivacy = sharedPreferences.getBoolean("isPrivacy",true);
        isLookingMen = sharedPreferences.getBoolean("isLookingMen",true);
        isLookingWomen = sharedPreferences.getBoolean("isLookingWomen",false);
        distance = sharedPreferences.getInt("distance",0);
        ageFrom =sharedPreferences.getInt("age_from",0);
    }

    public void settingView(){
        seekBarDistance.setMax(10);
        swPrivacy.setChecked(isPrivacy);
        swMen.setChecked(isLookingMen);
        swWomen.setChecked(isLookingWomen);
        seekBarDistance.setProgress(distance);
        tvDistance.setText(distance+" km");
        tvAgeFrom.setText(ageFrom+"");
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgBtnBack:
                finish();
                break;
            case R.id.imgBtnSave:
                putDataToSharedPreferences();
                Toast.makeText(this,"Save to preference success!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
