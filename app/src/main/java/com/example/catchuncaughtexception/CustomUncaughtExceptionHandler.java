package com.example.catchuncaughtexception;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

//Saves StackTrace of the crash if it occurs during RunTime
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
{
    public static final String STACKTRACE_FILE_NAME = "crash.stacktrace";
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Context context;

    public CustomUncaughtExceptionHandler(Context context)
    {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        StackTraceElement[] arr = ex.getStackTrace();
        String report = ex.toString() + "\n\n";
        report += "----------- Stack trace -----------\n\n";
        for (int i = 0; i < arr.length; i++)
        {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-----------------------------------\n\n";

        // Returns the cause of this throwable or null if the cause is nonexistent or unknow
        Throwable cause = ex.getCause();
        if (cause != null)
        {
            report += "----------- Cause -----------\n\n";
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++)
            {
                report += "    " + arr[i].toString() + "\n";
            }
            report += "-----------------------------------\n\n";
        }

        //try with resources
        try(FileOutputStream trace = context.openFileOutput(STACKTRACE_FILE_NAME, Context.MODE_PRIVATE))
        {
            trace.write(report.getBytes());

        }catch (IOException ioe)
        {
            // ...
        }

        defaultUEH.uncaughtException(thread, ex);
    }
}