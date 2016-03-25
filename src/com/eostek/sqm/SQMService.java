
package com.eostek.sqm;

import com.eostek.sqm.net.SQMSession;
import com.eostek.sqm.utils.SQMConstantUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SQMService extends Service {

    private static String TAG = "SQMService";
    private SQMSession mSQMSession;
    
    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        String serverIP = intent.getStringExtra(SQMConstantUtil.KEY_MQMCIP);
        if(null == serverIP || serverIP.length() == 0){
            serverIP = "125.88.86.55";
        }
        mSQMSession = new SQMSession();
        mSQMSession.open(serverIP);
        SQMNotifyImpl.getInstance().setSQMSession(mSQMSession);
        return SQMNotifyImpl.getInstance();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mSQMSession.close();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit( 0 );
    }
    
}
