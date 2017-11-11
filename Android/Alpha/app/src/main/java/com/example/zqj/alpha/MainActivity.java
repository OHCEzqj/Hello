package com.example.zqj.alpha;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private int alpha=255;
    boolean isshow=false;
    private Thread thread;
    private Handler mHandler=new Handler();
    private ImageView imageview;
    private Button jingru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jingru=(Button)findViewById(R.id.button);
        jingru.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                thread.start();
                jingru.setVisibility(View.INVISIBLE);
            }

        });


        imageview=(ImageView)findViewById(R.id.imageView);
        imageview.setAlpha(alpha);
        isshow=true;


        mHandler=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                imageview.setAlpha(alpha);
            }
        };

        thread=new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(isshow){
                    try{
                        thread.sleep(100);
                        // update();
                        if(alpha>=25){alpha=alpha-25;}
                        else{alpha=0;
                            isshow=false;
                        }
                        //  mHandler.sendMessage(mHandler.obtainMessage());
                        mHandler.sendEmptyMessage(1);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    protected void update() {
        // TODO Auto-generated method stub
        if(alpha>=25){alpha=alpha-25;}
        else{alpha=0;
            isshow=false;
        }
        mHandler.sendMessage(mHandler.obtainMessage());
    }
}
