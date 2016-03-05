package com.example.development.frostapp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class ConfigureTest extends AppCompatActivity {

    SeekBar seekChooseSpeed;
    TextView edtNumberofbeatsper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_test);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);(TextView) findViewById(R.id.txtAccelerVals);

        edtNumberofbeatsper = (TextView) findViewById(R.id.txtSpeedOfPulse);
        seekChooseSpeed = (SeekBar) findViewById(R.id.seekSpeedOfPulse);
        seekChooseSpeed.setProgress(seekChooseSpeed.getMax()/2);
        edtNumberofbeatsper.setText("BPM: " + seekChooseSpeed.getProgress() + "/" + seekChooseSpeed.getMax()) ;
        //edtNumberofbeatsper.setText("Covered: " + seekChooseSpeed.getProgress() + "/" + seekChooseSpeed.getMax());
        seekChooseSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                edtNumberofbeatsper.setText("BPM: " + seekChooseSpeed.getProgress() + "/" + seekChooseSpeed.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                edtNumberofbeatsper.setText("BPM: " + seekChooseSpeed.getProgress() + "/" + seekChooseSpeed.getMax());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                edtNumberofbeatsper.setText("BPM: " + seekChooseSpeed.getProgress() + "/" + seekChooseSpeed.getMax());
            }
        });
    }

    public void btnPerformTestClick(View vwLeft) {
        Intent intConfigureSettings = new Intent(ConfigureTest.this, MainFrostApp3.class);
       //startActivity(intConfigureSettings);
        startActivity(new Intent(ConfigureTest.this, MainFrostApp3.class));
    }


}
