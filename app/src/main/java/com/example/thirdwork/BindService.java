package com.example.thirdwork;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class BindService extends Service {
    private int count;
    private boolean quitFlag;

    //定义onBinder方法所返回的对象
    private MyBinder myBinder = new MyBinder();

    //通过继承Binder来实现IBinder类
    public class MyBinder extends Binder {
        public int getCount(){
            //获取Service的运行状态
            return count;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("~~~~~Service is binded~~~~~");
        //返回IBinder对象
        return myBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("~~~~~Service is created~~~~~");
        //启动一条线程，动态地修改count状态值
        new Thread(){
            @Override
            public void run(){
                while(!quitFlag){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e)
                    {

                    }
                    count++;
                }
            }
        }.start();
    }

    //Service被断开连接时回调该方法
    @Override
    public boolean onUnbind(Intent intent){
        System.out.println("~~~~~Service is unbinded~~~~~");
        return true;
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy(){
        super.onDestroy();
        this.quitFlag = true;
        System.out.println("~~~~~Service is destroyed~~~~~");
    }
}
