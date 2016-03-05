package com.example.development.frostapp3;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.media.AudioManager;
//import android.hardware.usb.UsbDevice;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import java.util.List;
import java.util.logging.Handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MainFrostApp3 extends AppCompatActivity implements SensorEventListener {
    //Used throughout app
    TextView txtViewTime;
    TextView txtAllClickTimes;
    TextView txtAccerValsScreen;
    TextView txtGyroscopeScreen;
    TextView txtStepCounterScreen;
    TextView txtRightDiffTime;
    ScrollView scrPulseView1;
    ScrollView scrMovementTimes1;
    Calendar CalLeftClick;// = Calendar.getInstance();
    Calendar CalRightClick;// = Calendar.getInstance();
    Calendar CalBeepTick;// = Calendar.getInstance();
    Calendar CalAccel;// = Calendar.getInstance();
    Calendar CalGyro;// = Calendar.getInstance();


    PulseExerciseCounter2 timerRSG = new PulseExerciseCounter2 (3000, 1000);//retsettgo
    PulseExerciseCounter timer = new PulseExerciseCounter (30000, 1000);

    String   sAlterStep;
    int      stepCount;

    int iEachPulse = 1;
    int iPulseTime = 0;
    int iRightTIme = 0;
    int iLeftTime = 0;
    int iTotalDiffToDisplay = 0;
    int i = 0;
    private long actualTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_frost_app3);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        sAlterStep = "1";
        int stepCount = 0;

        Button btnStart         = (Button) findViewById(R.id.btnStart);
        Button btnStop          = (Button) findViewById(R.id.btnStopp);
        //Button btnExitThis1 = (Button) findViewById(R.id.btnExitThis);
        txtViewTime             = (TextView) findViewById(R.id.txtTimer);
        txtAllClickTimes        = (TextView) findViewById(R.id.txtAllClickTimes);
        txtRightDiffTime        = (TextView) findViewById(R.id.txtRightDiff);

        scrPulseView1           = (ScrollView) findViewById(R.id.scrPulseView);
        scrMovementTimes1       = (ScrollView) findViewById(R.id.scrMovementTimes);

        txtAllClickTimes.setMovementMethod((new ScrollingMovementMethod()));
        txtViewTime.setText(" 00 ");

        //ResetPulseTimer();

        btnStart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetPulseTimer();
                timerRSG.start();
                //timer.start();
            }
        });

        btnStop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //
                //
                ResetPulseTimer();
                timer.cancel();
            }
        });

        //mixxer 2 6 2016 start
        txtAccerValsScreen = (TextView) findViewById(R.id.txtAccelerVals);
        txtGyroscopeScreen = (TextView) findViewById(R.id.txtGyroscopeVals);
        txtStepCounterScreen = (TextView) findViewById(R.id.txtStepCounter);
/*      FindSensorsNPlay1();
        FindSensorsNPlay2();
        FindSensorsNPlay3();
        FindSensorsNPlay4();
        FindSensorsNPlay5();*/
        RequestedToClearEverything();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

    @Override
    public void onSensorChanged(SensorEvent event) {

/*        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            txtAccerValsScreen.setText("X>" + event.values[0] +
                    "\nY>" + event.values[1] +
                    "\nz>" + event.values[2]);

            float accelationSquareRoot = (event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2])
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            if (accelationSquareRoot >= 2) {
                //this.actualTime = event.timestamp;

                CalAccel = Calendar.getInstance();
                int seconds = CalAccel.get(Calendar.SECOND);
                int milliscnd = CalAccel.get(Calendar.MILLISECOND);
                txtAllClickTimes.append(seconds + ":" + milliscnd + "<-Acc ");

                //txtAllClickTimes.setText("Acc: " + (int)this.actualTime);

                txtAccerValsScreen.setBackgroundColor(Color.RED);
            } else {
                txtAccerValsScreen.setBackgroundColor(Color.BLUE);
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER)// ||
                //event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
                {
                stepCount++;
                if (event.values[0] == 1.0f) {
                    stepCount++;

                    if (sAlterStep.equals("1"))// == "1")
                    {
                        sAlterStep = "2";
                        txtStepCounterScreen.setBackgroundColor(Color.GRAY);
                    }
                    else {
                        sAlterStep = "1";
                        txtStepCounterScreen.setBackgroundColor(Color.YELLOW);
                    }

                    txtStepCounterScreen.setText("# Steps: " + stepCount);
                }
            }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            txtGyroscopeScreen.setText("X:" + (int) event.values[0] + " rad/s" +
                    "\nY:" + (int) event.values[1] + " rad/s" +
                    "\nZ:" + (int) event.values[2] + " rad/s");

            if ((int)event.values[1] > 2 ){
                CalGyro = Calendar.getInstance();
                int seconds = CalGyro.get(Calendar.SECOND);
                int milliscnd = CalGyro.get(Calendar.MILLISECOND);
                txtAllClickTimes.append(seconds + ":" + milliscnd + "gyro: ");
                txtGyroscopeScreen.setTextColor(Color.GREEN);
                txtGyroscopeScreen.setBackgroundColor(Color.RED);

            }
            else {
                txtGyroscopeScreen.setTextColor(Color.MAGENTA);
                txtGyroscopeScreen.setBackgroundColor(Color.LTGRAY);
            }
        }
*/
    }

    public void ResetPulseTimer()
    {
        timerRSG.cancel();
        timerRSG = new PulseExerciseCounter2 (3000, 1000);
        timer.cancel();
        timer = new PulseExerciseCounter (30000, 1000);
    }


    public void btnClickRight(View vwRight)
    {
        //Button btnRight=(Button) vwRight;
        //((Button) vwRight).setText("red ded");
        //long millis = millisUntilFinished;
        //String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
         //       TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
          //      TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //System.out.println(hms);
        //txtViewTime.setText(hms);

        //txtAllClickTimes.setText( hms );

        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_DTMF_3, 130);
        toneG.release();
        int iTimeDiff;
        CalRightClick = Calendar.getInstance();
        int seconds = CalRightClick.get(Calendar.SECOND);
        int milliscnd = CalRightClick.get(Calendar.MILLISECOND);
        iRightTIme = seconds + milliscnd;
        if (iPulseTime != iRightTIme)  {
            iTimeDiff = iPulseTime - iRightTIme;
            AddUpDiffTimes(iTimeDiff);

            if(iTimeDiff > 400 || iTimeDiff < -400) {
                txtRightDiffTime.setTextColor(Color.RED);
            }
            else if (iTimeDiff > 200 || iTimeDiff < -200){
                txtRightDiffTime.setTextColor(Color.YELLOW);
            }
            else{
                txtRightDiffTime.setTextColor(Color.GREEN);
            }

            txtRightDiffTime.append("T"+iTimeDiff+"\n");
        }
        else {
            txtRightDiffTime.setTextColor(Color.MAGENTA);
            txtRightDiffTime.append("Perfect\n");
        }


/*        if(((Button) vwRight).getText() == "Right1") {
            txtAllClickTimes.append( seconds + ":" + milliscnd + "Right");
            ((Button) vwRight).setText("Right2");
            (vwRight).setBackgroundColor(0xFFF12C2C);
        }
        else
        {
            txtAllClickTimes.append(seconds + ":" + milliscnd + "Right");
            ((Button) vwRight).setText("Right1");
            ( vwRight ).setBackgroundColor(0xFFFCFC0A);
        }*/
    }

    public void btnExitThisNow(View vwLeft)
    {
        finish();
        System.exit(0);
    }

    public void btnClearThis(View vwLeft){
        RequestedToClearEverything();

    }

    public void btnClickLeft(View vwLeft)
    {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        toneG.startTone(ToneGenerator.TONE_DTMF_9, 100);
        toneG.release();
        Button btnLeft=(Button) vwLeft;
        ((Button) vwLeft).setText("LEFT");
        CalLeftClick = Calendar.getInstance();
        int seconds = CalLeftClick.get(Calendar.SECOND);
        int milliscnd = CalLeftClick.get(Calendar.MILLISECOND);
        //txtAllClickTimes.append( seconds + ":" + milliscnd + " Left" );
        int  iTimeDiff = 0;
        iLeftTime = seconds + milliscnd;
        if (iPulseTime != iLeftTime)  {
            iTimeDiff = iPulseTime - iLeftTime;
            AddUpDiffTimes(iTimeDiff);

            if(iTimeDiff > 400 || iTimeDiff < -400) {
                txtRightDiffTime.setTextColor(Color.RED);
            }
            else if (iTimeDiff > 200 || iTimeDiff < -200){
                txtRightDiffTime.setTextColor(Color.YELLOW);
            }
            else{
                txtRightDiffTime.setTextColor(Color.GREEN);
            }

            txtRightDiffTime.append("B"+iTimeDiff+"\n");
        }
        else {
            txtRightDiffTime.setTextColor(Color.MAGENTA);
            txtRightDiffTime.append("Perfect\n");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_frost_app3, menu);
        return true;
    }

    public void AddUpDiffTimes(int iTimeOfClick){
        iTotalDiffToDisplay = Math.abs( iTotalDiffToDisplay) + Math.abs(iTimeOfClick);
        txtStepCounterScreen.setText(""+iTotalDiffToDisplay);

    }
    public void RequestedToClearEverything() {
        txtAllClickTimes.setText("");
        txtAllClickTimes.clearFocus();
        txtAllClickTimes.setTop(1);

        txtRightDiffTime.setText("");
        txtRightDiffTime.clearFocus();
        txtRightDiffTime.setTop(1);

        scrPulseView1.smoothScrollTo(0, 0);
        scrMovementTimes1.smoothScrollTo(0, 0);
        ResetPulseTimer();
        txtViewTime.setText("");
        iTotalDiffToDisplay = 0;
        txtStepCounterScreen.setText("");
        txtRightDiffTime.setTextColor(Color.BLACK);
        iEachPulse=1;
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
    public void FindSensorsNPlay1 ()
    {
        //Sensor stuff

        SensorManager mSensorManager1;
        mSensorManager1 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager1.getSensorList(Sensor.TYPE_ALL);

        List<Sensor> gravitySensors = mSensorManager1.getSensorList(Sensor.TYPE_GRAVITY);
        //SensorManager mSensorManger = getSystemService(Context.SENSOR_SERVICE);


    }

    public void FindSensorsNPlay2 ()
    {
        //Sensor stuff 2
        Sensor senAccelerometer;
        SensorManager mSensorManager2;
        SensorEventListener senListener;
        mSensorManager2 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = mSensorManager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager2.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void FindSensorsNPlay3 ()
    {
        //Sensor stuff 3
        Sensor senGyro;
        SensorManager mSensorManager3;
        SensorEventListener senListener;
        mSensorManager3 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senGyro = mSensorManager3.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager3.registerListener(this, senGyro, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void FindSensorsNPlay4 ()
    {
        //Sensor stuff 4
        Sensor senStepper;
        SensorManager mSensorManager4;
        SensorEventListener senListener;
        mSensorManager4 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senStepper = mSensorManager4.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager4.registerListener(this, senStepper, SensorManager.SENSOR_DELAY_FASTEST);// SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void FindSensorsNPlay5 ()
    {
        //Sensor stuff 5
        Sensor senStepper;
        SensorManager mSensorManager5;
        SensorEventListener senListener;
        mSensorManager5 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senStepper = mSensorManager5.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager5.registerListener(this, senStepper, SensorManager.SENSOR_DELAY_NORMAL);   //SENSOR_STATUS_ACCURACY_LOW);// SensorManager.SENSOR_DELAY_NORMAL);



    }
    public void FindSensorsNPlay6 ()
    {
        //Sensor stuff 6
        Sensor senStepper;
        SensorManager mSensorManager6;
        SensorEventListener senListener;
        mSensorManager6 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senStepper = mSensorManager6.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager6.registerListener(this, senStepper, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);// SensorManager.SENSOR_DELAY_NORMAL);


    }

    public class PulseExerciseCounter extends CountDownTimer {
        public PulseExerciseCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;


            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
            toneG.startTone(ToneGenerator.TONE_DTMF_6, 100);
            toneG.release();

            //String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            //       TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            //      TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            //System.out.println(hms);
            //txtViewTime.setText(hms.substring(6, 8));
            //txtViewTime.


            //txtAllClickTimes.setText( hms );
            //txtAllClickTimes.append(hms + "\n");*/
            CalBeepTick = Calendar.getInstance();
            int seconds = CalBeepTick.get(Calendar.SECOND);
            int milliscnd = CalBeepTick.get(Calendar.MILLISECOND);

            iPulseTime = seconds + milliscnd;

            //txtAllClickTimes.append("\nPULSE: " + seconds + ":" + milliscnd + " " );

            txtAllClickTimes.append("P: " + iEachPulse + "\n");// + iEachPulse + iPulseTime );
            iEachPulse++;
            Button btnStart = (Button) findViewById(R.id.btnRight);
            //RelativeLayout rl = (RelativeLayout)findViewById(R.id.btnRight);
            //rl.setBackgroundColor(Color.MAGENTA);


        }

        @Override
        public void onFinish() {
            txtViewTime.setText("Diff " + iTotalDiffToDisplay);
            ResetPulseTimer();
        }
    }

    public class PulseExerciseCounter2 extends CountDownTimer {
        public PulseExerciseCounter2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;


            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
            toneG.startTone(ToneGenerator.TONE_DTMF_6  , 200);
            toneG.release();

            //String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            //       TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            //      TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            //System.out.println(hms);
            //txtViewTime.setText(hms.substring(6, 8));
            //txtViewTime.
            if(iEachPulse == 1) {
                txtStepCounterScreen.setBackgroundColor(Color.RED);
                txtStepCounterScreen.setText("READY");
            }
            if(iEachPulse == 2) {
                txtStepCounterScreen.setBackgroundColor(Color.YELLOW);
                txtStepCounterScreen.setText("SET");
            }
            iEachPulse++;
        }

        @Override
        public void onFinish() {
            //txtViewTime.setText("Diff " + iTotalDiffToDisplay);
           // ResetPulseTimer();
            iEachPulse = 1;
            txtStepCounterScreen.setBackgroundColor(Color.GREEN);
            txtStepCounterScreen.setText("GO");
            timer.start();
        }


    }
}
