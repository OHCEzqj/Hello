package com.example.zqj.myservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent=new Intent(getBaseContext(),MyService.class);
        Button btstart=(Button)findViewById(R.id.button1);
        Button btstop=(Button)findViewById(R.id.button2);
        btstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动服务
                startService(intent);
            }
        });
        btstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止服务
                stopService(intent);
            }
        });


    }
}
