package com.example.smsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mbtnSend;
    private EditText medtPhoneNum,medtMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnSend=findViewById(R.id.btnSend);
        medtMessage=findViewById(R.id.edtMsg);
        medtPhoneNum=findViewById(R.id.edtPhoneNum);

        mbtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED)
                {
                    //When permission is granted
                    sendsms();
                }
                //when permision is not granted ,request for permission
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
                }


            }
        });
    }

    private void sendsms()
    {
        String phoneno=medtPhoneNum.getText().toString().trim();
        String SMS=medtMessage.getText().toString().trim();

       if(! phoneno.equals("") && !SMS.equals(""))
        {
            SmsManager smsmanager=SmsManager.getDefault();
            smsmanager.sendTextMessage(phoneno,null,SMS,null,null);
            Toast.makeText(getApplicationContext(),"SMS sent",Toast.LENGTH_LONG).show();

        }
        else
       {
           Toast.makeText(getApplicationContext(),"Fillthe values first",Toast.LENGTH_LONG).show();
       }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendsms();
        }
        else {
            Toast.makeText(getApplicationContext(),"Failed to send",Toast.LENGTH_LONG).show();

        }
    }
}