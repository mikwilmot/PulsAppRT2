package com.example.development.frostapp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {

//    Button btnGoToExercise;
//    Button btnConfigTest;
//    Button btnHelp;
//    Button btnExitHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // btnGoToExercise = (Button) findViewById(R.id.btnDoTest);
        // btnConfigTest = (Button) findViewById(R.id.btnConfigTest);
        // btnHelp = (Button) findViewById(R.id.btnWhatIsPulsApp);
        // btnExitHere = (Button) findViewById(R.id.btnExitHere);

    }

    public void btnConfigTestClick(View vwLeft) {
        startActivity(new Intent(SplashScreen.this, ConfigureTest.class));
    }

    public void btnPerformTestClick(View vwLeft) {
        startActivity(new Intent(SplashScreen.this, MainFrostApp3.class));
    }

    public void btnHelpScreen(View vwLeft) {
        startActivity(new Intent(SplashScreen.this, helpPulsApp.class));
    }

    public void btnExitClick(View vwLeft) {
        finish();
        System.exit(0);
    }
}

//        btnConfigTest.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent( SplashScreen.this, ConfigureTest.class));
//            }
//        });

//        btnGoToExercise.setOnClickListener(new View.OnClickListener() {
//           public void onClick(View v) {
//                startActivity(new Intent( SplashScreen.this, MainFrostApp3.class));
//            }
//        });

//        btnHelp.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent( SplashScreen.this, helpPulsApp.class));
//            }
//        });
//        btnExitHere.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                finish();
//                System.exit(0);
//           }
//        });