package com.example.bob_book.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    TextView textView, round, text2, raznica, textIterationInSecond;
    SimpleDateFormat sdf;
    Sensor sensor;
    long pre = System.currentTimeMillis();
    long last = System.currentTimeMillis();
    long now = System.currentTimeMillis();
    DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
    DateFormat dfMs = new SimpleDateFormat("ss:SSS");
    DateFormat dfS = new SimpleDateFormat("ss");
    int second = Integer.parseInt(dfS.format(System.currentTimeMillis()));
    int sedondCurrent = Integer.parseInt(dfS.format(System.currentTimeMillis()));
    int iterationInSecond = 0;
    int iterations = 0;


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_FASTEST);
        System.out.println("sensor_register");
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
        round = (TextView) findViewById(R.id.round);
        raznica = (TextView) findViewById(R.id.raznica);
        textIterationInSecond = (TextView) findViewById(R.id.iterationInSecond);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            sedondCurrent = Integer.parseInt(dfS.format(System.currentTimeMillis()));
            if (sedondCurrent != second) {
                second = sedondCurrent;
                iterationInSecond = iterations;
                iterations = 0;
            }
            iterations++;
            textIterationInSecond.setText("Iterations Over The Last Second: " + iterationInSecond);
            pre = System.currentTimeMillis();
            now = pre - last;
            textView.setText("LIGHT: " + sensorEvent.values[0]);
            if (sensorEvent.values.length > 1) {
                text2.setText("LIGHT2: " + sensorEvent.values[1]);
            }
            round.setText("Current Time: " + df.format(Calendar.getInstance().getTimeInMillis()));
            raznica.setText("Time Since Last Request : " + dfMs.format(now));
            last = System.currentTimeMillis();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
