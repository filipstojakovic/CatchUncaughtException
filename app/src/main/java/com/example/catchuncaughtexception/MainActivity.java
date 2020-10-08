package com.example.catchuncaughtexception;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomUncaughtExceptionHandler handler = new CustomUncaughtExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        new ReportCrash(this).emailCrash();
        handler.deleteFile();
        
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Object t=null;
                t.toString();   // make a crash
            }
        });
    }
}