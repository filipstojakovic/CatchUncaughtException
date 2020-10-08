package com.example.catchuncaughtexception;

import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportCrash
{
    public static final String EMAIL_SUBJECT = "Crash Report ";
    public static final String EMAIL_TO = "name.surname@email.com";
    public static final String INTENT_TYPE = "message/rfc822";
    public static final String CRASH_REPORT_TITLE = "Title when choosing emailing app";

    private Context context;
    private String emailTo;

    public ReportCrash(Context context)
    {
        this.context = context;
        emailTo = EMAIL_TO;
    }

    public ReportCrash(String email, Context context)
    {
        this(context);
        this.emailTo = email;
    }

    //Send crash via email if crashfile exists
    public void emailCrash()
    {
        try
        {
            String email_body = getStackTraceFile();

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
            sendIntent.putExtra(Intent.EXTRA_TEXT, email_body);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT + Util.getCurrentDateAndTime());
            sendIntent.setType(INTENT_TYPE);

            context.startActivity(Intent.createChooser(sendIntent, CRASH_REPORT_TITLE));


        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }



    // if file crash.stacktrace exists ask user to send email of the crash
    // if file scrash.stacktrace does NOT exist, then FileNotFoundException will be thrown and rest of the method's code wont be executed
    private String getStackTraceFile() throws IOException
    {
        //or use File.exists to check if file exists
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(CustomUncaughtExceptionHandler.STACKTRACE_FILE_NAME)));
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null)
        {
            stringBuilder.append(line).append("\n");
        }
        reader.close();

        // u can delete file after reading
        // context.deleteFile(CustomUncaughtExceptionHandler.STACKTRACE_FILE_NAME);
        return stringBuilder.toString();
    }
}
