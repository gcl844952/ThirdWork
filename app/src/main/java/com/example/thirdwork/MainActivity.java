package com.example.thirdwork;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    //声明按钮
    Button bind;
    Button unbind;
    Button getServiceStatus;

    //保持所启动的Service的IBinder对象
    BindService.MyBinder myBinder;

    //定义一个ServiceConnection对象
    private ServiceConnection connection = new ServiceConnection() {
        //连接成功时
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("~~~~~Connected~~~~~");
            //获取Service的onBind方法返回的MyBinder对象
            myBinder = (BindService.MyBinder) iBinder;
        }

        //连接失败时
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("~~~~~Disconnected~~~~~");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取程序界面中的bind、unbind、getServiceStatus按钮
        bind = (Button)findViewById(R.id.bindButton);
        unbind = (Button)findViewById(R.id.unbindButton);
        getServiceStatus = (Button)findViewById(R.id.getServiceStatusButton);

        //创建启动Service的Intent
        final Intent intent = new Intent(MainActivity.this, BindService.class);

        //监听bind按钮点击
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定指定Service
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });

        //监听unbind按钮点击
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //解除绑定Service
                unbindService(connection);
            }
        });

        //监听getServiceStatus按钮点击
        getServiceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取并显示Service的count值
                Toast.makeText(MainActivity.this,
                        "Service的count值为：" + myBinder.getCount(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
