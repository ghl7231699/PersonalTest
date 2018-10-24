package com.example.liangge.rxjavatest.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.liangge.rxjavatest.IMyAidlInterface;

/**
 * Created by guhongliang on 2018/8/9.
 */

public class TestService extends Service {
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void add(int type, String s) throws RemoteException {
            if (type == 1) {
                Log.e(TestService.class.getSimpleName(), s);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }
}
